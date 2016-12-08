package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.CartDao;
import com.jojo.dao.DaoFactory;
import com.jojo.dao.FoodDao;
import com.jojo.model.Cart;
import com.jojo.model.Food;

public class AlterCartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		// ajax请求还挺好用的
		if("add".equals(action)){
			addCart(request, response);
			return ;
		
		}else if("delete".equals(action)){
			deleteCart(request, response);
			return ;
		
		}else if("update".equals(action)){
			updateCart(request, response);
			return ;
		}
	}

	
	
	/**
	 * 更新购物车，主要是数量
	 * @param request
	 * @param response
	 */
	private void updateCart(HttpServletRequest request, HttpServletResponse response) {
		int cartId =Integer.parseInt(request.getParameter("cartId"));
		int num = Integer.parseInt(request.getParameter("num"));
		float sum = Float.parseFloat(request.getParameter("sum"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			CartDao cartDao = daoFactory.createCartDao();
			cartDao.updateCart(cartId, num, sum);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * 删除购物车内的商品
	 * @param request
	 * @param response
	 */
	private void deleteCart(HttpServletRequest request, HttpServletResponse response) {
		int cartId = Integer.parseInt(request.getParameter("cartId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			CartDao cartDao = daoFactory.createCartDao();
			cartDao.deleteCart(cartId);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * 添加购物车
	 * @param request
	 * @param response
	 */
	private void addCart(HttpServletRequest request, HttpServletResponse response) {
		int merchantId = Integer.parseInt(request.getParameter("merchantId"));
		String merchantName = request.getParameter("merchantName");
		int userId = Integer.parseInt(request.getParameter("userId"));
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			CartDao cartDao = daoFactory.createCartDao();
			
			// 往数据库添加时，先判重
			boolean flag = cartDao.selectCart(userId, foodId);
			if (!flag) {
				FoodDao foodDao = daoFactory.createFoodDao();
				Food food = foodDao.selectFood(foodId);
				Cart cart = cartDao.createCart(merchantId, merchantName, userId, food);
				cartDao.addCart(cart);
				daoFactory.endTransaction();
			}else{
				// 预留，暂时不写，已存在商品数量加 1 功能
				//	cartDao.updateCart(cartId, num, sum)
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
