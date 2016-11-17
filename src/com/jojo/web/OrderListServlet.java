package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.OrderDao;
import com.jojo.model.Order;

public class OrderListServlet extends HttpServlet {

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
		int id = Integer.parseInt(request.getParameter("id"));
		int status = Integer.parseInt(request.getParameter("status"));
		boolean flag = Boolean.parseBoolean(request.getParameter("flag"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			OrderDao orderDao = daoFactory.createOrderDao();
			List<Order> orderList =  orderDao.selectOrderByStatus(id, status, flag, page);
			request.setAttribute("orderList", orderList);
			
			// 根据这个flag发往指定的页面
			if(flag == true){
				request.setAttribute("xxxjsp", "background/userStatus.jsp");
				request.getRequestDispatcher("userMain.jsp").forward(request, response);
				return ;
			}else{
				request.setAttribute("xxxjsp", "background/merchantStatus.jsp");
				request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
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
