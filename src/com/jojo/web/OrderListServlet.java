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


/**
 * 这个类写的太乱了
 * @author flash.J
 *
 */
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
			daoFactory.endTransaction();
			
			// 查询并插入总页数
			int totalPages = orderDao.selectOrderCounts(id, status, flag);
			daoFactory.endTransaction();
			if(totalPages % 10 == 0){
				totalPages /= 10;
			}else{
				totalPages = totalPages / 10 + 1;
			}
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("orderList", orderList);
			
			// 发往用户或商家
			if (flag == true) {
				// 根据status选择相应的jsp
				if (status == 0) {
					request.setAttribute("xxxjsp", "background/userStatus0.jsp");
				} else if (status == 1) {
					request.setAttribute("xxxjsp", "background/userStatus1.jsp");
				} else if (status == 2) {
					request.setAttribute("xxxjsp", "background/userStatus2.jsp");
				}
				request.getRequestDispatcher("userMain.jsp").forward(request, response);
				return;
				
			} else {
				// 根据status选择相应的jsp
				if (status == 0) {
					request.setAttribute("xxxjsp", "background/merchantStatus0.jsp");
				} else if (status == 1) {
					request.setAttribute("xxxjsp", "background/merchantStatus1.jsp");
				} else if (status == 2) {
					request.setAttribute("xxxjsp", "background/merchantStatus2.jsp");
				}
				request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
