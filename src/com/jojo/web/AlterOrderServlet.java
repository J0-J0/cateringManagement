package com.jojo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.FoodDao;
import com.jojo.dao.MerchantDao;
import com.jojo.dao.OrderDao;
import com.jojo.dao.UserDao;
import com.jojo.model.Food;
import com.jojo.model.Merchant;
import com.jojo.model.Order;
import com.jojo.model.User;

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
		String action = request.getParameter("action");
		
		if("add".equals(action)){
			addOrder(request, response);
			response.sendRedirect(request.getContextPath()+"/userMain.jsp");
			return ;
		
		}else if("updateStatus".equals(action)){
			updateOrderStatus(request, response);
			return ;  // ajax���󣬷�������תҳ��
		
		}else if("selectOrderNum".equals(action)){
			selectOrderNum(request, response);
			return ;
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
	protected void addOrder(HttpServletRequest request, HttpServletResponse response) {
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
			
			/*
			 * ���ɶ����������ݿ����,����ҳ�OrderId���ٹ�һ������
			 * �˴���Ҫ����ʳƷ����
			 */
			foodDao.updateFoodNum(food, num);
			Order order = orderDao.createOrder(food, user, merchant, num);
			orderDao.addOrder(order);
			order.setOrderId(orderDao.selectOrderId(order));
			daoFactory.endTransaction(); 
			
			// ��t_orderFood���ʳ��
			orderDao.addOrderFood(order, food, num);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	
	}

}
