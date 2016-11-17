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


/**
 * ���༴����ע��Ҳ������Ϣ�޸ģ����ǲ��Ǹ��������أ�
 * @author flash.J
 *
 */
public class UserInfoUpdateServlet extends HttpServlet {

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
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute("currentUser");
		DaoFactory daoFactory = new DaoFactory();
		
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			UserDao userDao = daoFactory.createUserDao();
		
			if(currentUser != null){
				
				// �û�����
				getUserInfo(currentUser, request);
				userDao.updateUser(currentUser);
			}else{
				
				// �û�ע��
				// ��������õ��������Һ�û��ȫ��
				currentUser = new User();
				getUserInfo(currentUser, request);
				int row = userDao.addUser(currentUser);
				if(row == 1){
					
					// ����ɹ�������Ҫ��ҪҲ�ж�һ�£�����if�ǲ���̫���ˣ�
					int userId = userDao.selectUserId(currentUser);
					currentUser.setUserId(userId);
					session.setAttribute("currentUser", currentUser);
					
					// Ϊ�˹رս����
					daoFactory.endTransaction();
					response.sendRedirect("userMain.jsp");
					return ;
				}
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
	 * ��ȡ�û���Ϣ���������ݹ�����user����
	 * @param user
	 * @param request
	 */
	private void getUserInfo(User user, HttpServletRequest request) {
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
