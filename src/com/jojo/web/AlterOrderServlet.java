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
			return ;  // ajax���󣬷�������תҳ��
		
		}else if("selectOrderNum".equals(action)){
			selectOrderNum(request, response);
			return ;
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * ����������ʳ��
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
			
			FoodDao foodDao = daoFactory.createFoodDao();				// DAO׼��
			UserDao userDao = daoFactory.createUserDao();
			MerchantDao merchantDao = daoFactory.createMerchantDao();
			OrderDao orderDao = daoFactory.createOrderDao();
			
			Food tmp = foodDao.selectFood(Integer.parseInt(foodIdArr[1]));
			User user = userDao.selectUser(userId);
			Merchant merchant = merchantDao.selectMerchant(tmp.getMerchantId());
			daoFactory.endTransaction(); 
			
			List<OrderFood> orderFoodList = new ArrayList<OrderFood>();
			for(int i = 1; i < foodIdArr.length; i++){												// ѭ��Ԥ��
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
	 * ����������ʳ��
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
			
			// �����Ҫ��Ϣ��Ȼ���ȹ�һ������
			Food food = foodDao.selectFood(foodId);
			User user = userDao.selectUser(userId);
			Merchant merchant = merchantDao.selectMerchant(food.getMerchantId());
			daoFactory.endTransaction(); 
			
			// ����Order�����룬OrderFood���󲢲���session
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
	 * ��״̬���̼�id��ѯ��������Ŀ
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
	 * ���¶���
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
				out.println("�޸ĳɹ�!");
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
	 * ��Ӷ���
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	private void addOrder(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute("order");
		List<OrderFood> orderFoodList = (List<OrderFood>) session.getAttribute("orderFoodList");
		
		// ��ȡԤ��׼���õ�order���ݣ�������
		String address = request.getParameter("address");
		String way = request.getParameter("way");
		if("��ȡ".equals(way)){
			address="���û�ѡ����ȡ";
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
			
			// �����������ݿ����order��Ϣ����ȡ��orderId
			orderDao.addOrder(order);
			order.setOrderId(orderDao.selectOrderId(order));
			
			/*
			 * ��������t_orderFood������ݣ�
			 * ������t_food�е���������ɾ��t_cart�ж�Ӧ��ʳƷ 
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
