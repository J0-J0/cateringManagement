package com.jojo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jojo.dao.CartDao;
import com.jojo.dao.DaoFactory;
import com.jojo.dao.FoodDao;
import com.jojo.dao.MerchantDao;
import com.jojo.dao.OrderDao;
import com.jojo.dao.UserDao;
import com.jojo.model.Food;
import com.jojo.model.Merchant;
import com.jojo.model.Order;
import com.jojo.model.OrderFood;
import com.jojo.model.User;
import com.jojo.util.DateUtil;

public class AlterOrderServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("add".equals(action)){
			addOrder(request, response);
			response.sendRedirect(request.getContextPath()+"/userMain.jsp");
			return ;
		
		}else if("generate".equals(action)){	
			String source = request.getParameter("source");
			if ("fromCart".equals(source)) {
				generateOrderFromCart(request, response);
			} else {
				generateOrderFromFoodDetail(request, response);
			}
			request.getRequestDispatcher("orderDetail.jsp").forward(request, response);
			return;

		}else if("updateStatus".equals(action)){
			updateOrderStatus(request, response);
			return ;  // ajax请求，犯不着跳转页面
		
		}else if("selectOrderNum".equals(action)){
			selectOrderNum(request, response);
			return ;
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 单订单，多食物
	 * @param request
	 * @param response
	 */
	private void generateOrderFromCart(HttpServletRequest request, HttpServletResponse response) {
		String foodIdList = request.getParameter("foodIdList");
		String numList = request.getParameter("numList");
		int userId = Integer.parseInt(request.getParameter("userId"));
		String[] foodIdArr = foodIdList.split(":");
		String[] numArr = numList.split(":");
		
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();				// DAO准备
			UserDao userDao = daoFactory.createUserDao();
			MerchantDao merchantDao = daoFactory.createMerchantDao();
			OrderDao orderDao = daoFactory.createOrderDao();
			
			Food tmp = foodDao.selectFood(Integer.parseInt(foodIdArr[1]));
			User user = userDao.selectUser(userId);
			Merchant merchant = merchantDao.selectMerchant(tmp.getMerchantId());
			daoFactory.endTransaction(); 
			
			List<OrderFood> orderFoodList = new ArrayList<OrderFood>();
			for(int i = 1; i < foodIdArr.length; i++){												// 循坏预备
				Food food = foodDao.selectFood(Integer.parseInt(foodIdArr[i]));
				OrderFood orderFood = orderDao.createOrderFood(food, Integer.parseInt(numArr[i]));
				orderFoodList.add(orderFood);
			}
			
			Order order = orderDao.createOrder(user, merchant, orderFoodList);
			HttpSession session = request.getSession();
			session.setAttribute("order", order);
			session.setAttribute("orderFoodList", orderFoodList);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * 单订单，单食物
	 * @param request
	 * @param response
	 */
	private void generateOrderFromFoodDetail(HttpServletRequest request, HttpServletResponse response) {
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();
			UserDao userDao = daoFactory.createUserDao();
			MerchantDao merchantDao = daoFactory.createMerchantDao();
			OrderDao orderDao = daoFactory.createOrderDao();
			
			// 查出必要信息，然后先关一波事务
			Food food = foodDao.selectFood(foodId);
			User user = userDao.selectUser(userId);
			Merchant merchant = merchantDao.selectMerchant(food.getMerchantId());
			daoFactory.endTransaction(); 
			
			// 生成Order对象与，OrderFood对象并插入session
			OrderFood orderFood = orderDao.createOrderFood(food, num);
			List<OrderFood> orderFoodList = new ArrayList<OrderFood>();
			orderFoodList.add(orderFood);
			
			Order order = orderDao.createOrder(user, merchant, orderFoodList);
			HttpSession session = request.getSession();
			session.setAttribute("order", order);
			session.setAttribute("orderFoodList", orderFoodList);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * 按状态与商家id查询订单的数目
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void selectOrderNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int status = Integer.parseInt(request.getParameter("status"));
		int merchantId = Integer.parseInt(request.getParameter("merchantId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			OrderDao orderDao = daoFactory.createOrderDao();
			int num = orderDao.selectOrderCounts(merchantId, status, false);

			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = new PrintWriter(response.getWriter(), true);
			out.println(num);
			out.close();

			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * 更新订单
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int status = Integer.parseInt(request.getParameter("status"));
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			OrderDao orderDao = daoFactory.createOrderDao();
			int row = orderDao.updateOrder(orderId, status);
			if(row == 1){
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				
				PrintWriter out = new PrintWriter(response.getWriter(), true);
				out.println("修改成功!");
				out.close();
			}
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * 添加订单
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	private void addOrder(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute("order");
		List<OrderFood> orderFoodList = (List<OrderFood>) session.getAttribute("orderFoodList");
		
		// 提取预先准备好的order数据，并完善
		String address = request.getParameter("address");
		String way = request.getParameter("way");
		if("自取".equals(way)){
			address="该用户选择自取";
		}
		
		String str = request.getParameter("addTime");
		Date addTime = DateUtil.formatString(str, "dd/MM/yyyy");
		order.setAddress(address);
		order.setWay(way);
		order.setAddTime(addTime);
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FoodDao foodDao = daoFactory.createFoodDao();  	
			OrderDao orderDao = daoFactory.createOrderDao();
			CartDao cartDao = daoFactory.createCartDao();
			
			// 这里是往数据库添加order信息，并取出orderId
			orderDao.addOrder(order);
			order.setOrderId(orderDao.selectOrderId(order));
			
			/*
			 * 这里是往t_orderFood添加数据，
			 * 并更新t_food中的数量，并删除t_cart中对应的食品 
			 */
			for (OrderFood of : orderFoodList) {
				foodDao.updateFoodNum(of.getFoodId(), of.getNum());
				cartDao.deleteCart(order.getUserId(), of.getFoodId());
				orderDao.addOrderFood(of, order.getOrderId());
			}
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
