package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.UserDao;
import com.jojo.model.User;

public class UserLoginServlet extends HttpServlet {

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
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		
		// 判空
		if(userName == null || "".equals(userName)|| password == null || "".equals(password)){
			session.setAttribute("status", "null");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		DaoFactory daoFactory = new DaoFactory();
		try{
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			UserDao userDao = daoFactory.createUserDao();
			User user = userDao.selectUser(userName, password);
			if(user == null){
				
				// 用户名或密码错误
				session.setAttribute("status", "failed");
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}else{
				
				session.setAttribute("currentUser", user);
				session.setAttribute("status", "success");
				response.sendRedirect("index");
			}
			
			daoFactory.endTransaction();
		}catch(SQLException e){
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}
	
}
