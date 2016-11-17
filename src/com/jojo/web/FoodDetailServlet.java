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
import com.jojo.model.FoodComment;


public class FoodDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		String merchantName = request.getParameter("merchantName");
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			// 根据foodId查食物以及评论，其实主要还在评论
			FoodDao foodDao = daoFactory.createFoodDao();
			Food food = foodDao.selectFood(foodId);
			List<FoodComment> commentList = foodDao.selectFoodComment(foodId);
			
			request.setAttribute("food", food);
			request.setAttribute("commentList", commentList);
			request.setAttribute("merchantName", merchantName);
			request.getRequestDispatcher("foodDetail.jsp").forward(request, response);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
