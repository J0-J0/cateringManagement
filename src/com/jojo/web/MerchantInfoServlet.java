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
import com.jojo.model.Food;

public class MerchantInfoServlet extends HttpServlet {

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
		String merchantName = request.getParameter("merchantName");
		int merchantId = Integer.parseInt(request.getParameter("merchantId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FoodDao foodDao = daoFactory.createFoodDao();
			
			List<List<Food>> foodTypeList = foodDao.selectFoodList(merchantId);
			
			// 一个servlet就这么几行，心里落差挺大的
			request.setAttribute("foodTypeList", foodTypeList);
			request.setAttribute("merchantName", merchantName);
			request.getRequestDispatcher("merchantInfo.jsp").forward(request, response);
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
