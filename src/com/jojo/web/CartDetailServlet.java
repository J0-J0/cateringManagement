package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.CartDao;
import com.jojo.dao.DaoFactory;
import com.jojo.model.Cart;


public class CartDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			CartDao cartDao = daoFactory.createCartDao();
			List<List<Cart>> cartList = cartDao.selectCart(userId);
			
			request.setAttribute("cartList", cartList);
			request.getRequestDispatcher("userCart.jsp").forward(request, response);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
