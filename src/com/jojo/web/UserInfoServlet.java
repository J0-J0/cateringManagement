package com.jojo.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.UserDao;
import com.jojo.model.User;


/**
 * 该类即负责注册也负责信息修改，这是不是个好主意呢？
 * 是！而且从现在起，它还负责登录。
 * @author flash.J
 *
 */
public class UserInfoServlet extends HttpServlet {

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
		
		if("update".equals(action)){
			updateUser(request, response);
			request.getRequestDispatcher("userMain.jsp").forward(request, response);
			return ;  
			
		}else if("login".equals(action)){
			userLogin(request, response);
			return ;
			
		}else if("register".equals(action)){
			registerUser(request, response);
			response.sendRedirect("userMain.jsp");
			return ;
		}
	}
	
	
	
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
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

	
	
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		User currentUser = new User();
		getUserInfo(currentUser, request);
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			UserDao userDao = daoFactory.createUserDao();
			
			int row = userDao.addUser(currentUser);
			daoFactory.endTransaction();  // 提交事务
			if(row == 1){
				int userId = userDao.selectUserId(currentUser); // 插入成功，这里要不要也判断一下？
				currentUser.setUserId(userId);
				
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", currentUser);
				daoFactory.endTransaction();
			}else{
				
				// 数据库添加失败，预留位置，还没想好怎么写
			}
			daoFactory.endTransaction();  // 提交事务
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	
	
	
	/**
	 * 用户更新自己的信息，修改数据库的同时，修改currentUser
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute("currentUser");
		// 更新currentUser中的信息
		getUserInfo(currentUser, request);
	
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			UserDao userDao = daoFactory.createUserDao();

			// 用户更新，依旧是没有异常处理。。。
			userDao.updateUser(currentUser);
			
			daoFactory.endTransaction();  // 提交事务
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	
	
	/**
	 * 获取用户信息并赋给传递过来的user引用，是void，是个工具
	 * @param user
	 * @param request
	 * @throws UnsupportedEncodingException 
	 */
	private void getUserInfo(User user, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String userIdCard = request.getParameter("userIdCard");
		String userRealName = request.getParameter("userRealName");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String userTel = request.getParameter("userTel");
		String address = request.getParameter("address");
		
		user.setUserName(userName);
		user.setPassword(password);
		user.setUserIdCard(Integer.parseInt(userIdCard));
		user.setUserRealName(userRealName);
		user.setSex(sex);
		user.setAge(Integer.parseInt(age));
		user.setUserTel(userTel);
		user.setAddress(address);
	}
	
}
