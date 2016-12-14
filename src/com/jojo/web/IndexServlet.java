package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.FoodDao;
import com.jojo.dao.MerchantDao;
import com.jojo.model.Food;
import com.jojo.model.Merchant;

/**
 * 给index.jsp提供数据，主要是商家以及一些菜品
 * @author flash.J
 *
 */
public class IndexServlet extends HttpServlet{

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
		
		if("selectFood".equals(request.getParameter("action"))){
			selectFood(request, response);
			request.getRequestDispatcher("selectFood.jsp").forward(request, response);
			return ;
		}else{
			goIndex(request, response);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return ;
		}
	}
	
	
	/**
	 * 导航条搜索
	 * @param request
	 * @param response
	 */
	private void selectFood(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
																				// 为模糊搜索准备sql语句，注意要加引号
		char[] arr = keyword.toCharArray();
		StringBuilder sql = new StringBuilder("select * from t_food where foodName like \"%");
		for(char c : arr){
			sql.append(c + "%");
		}
		sql.append("\"");
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();
			List<Food> foodList = foodDao.selectFood(sql.toString());
			request.setAttribute("foodList", foodList);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
		
		
	}

	/**
	 * 访问首页
	 * @param request
	 * @param response
	 */
	private void goIndex(HttpServletRequest request, HttpServletResponse response) {
		DaoFactory daoFactory = new DaoFactory();
		// 开启连接与事务
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			MerchantDao merchantDao = daoFactory.createMerchantDao();
			List<Merchant> merchantList = merchantDao.selectAllMerchants();
			request.setAttribute("merchantList", merchantList);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}
}
