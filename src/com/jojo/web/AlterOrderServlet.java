package com.jojo.web;

import java.io.IOException;
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
		if(request.getParameter("action").equals("add")){
			addOrder(request, response);
			response.sendRedirect(request.getContextPath()+"/userMain.jsp");
			return ;
		}
	}

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
