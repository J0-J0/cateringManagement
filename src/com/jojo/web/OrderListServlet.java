package com.jojo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
		String isAJAX = request.getParameter("isAJAX");
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			OrderDao orderDao = daoFactory.createOrderDao();
			List<Order> orderList =  orderDao.selectOrderByStatus(id, status, flag, page);
			
			// 是否AJAX请求
			if (isAJAX == null || "".equals(isAJAX)) {
				
				// 查询并插入总页数
				int totalPages = orderDao.selectOrderCounts(id, status, flag);
				if(totalPages % 10 == 0){
					totalPages /= 10;
				}else{
					totalPages = totalPages / 10 + 1;
				}
				request.setAttribute("totalPages", totalPages);
				request.setAttribute("orderList", orderList);
				
				// 根据这个flag发往指定的页面
				if (flag == true) {
					request.setAttribute("xxxjsp", "background/userStatus.jsp");
					request.getRequestDispatcher("userMain.jsp").forward(request, response);
					daoFactory.endTransaction();
					return;
				} else {
					request.setAttribute("xxxjsp", "background/merchantStatus.jsp");
					request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
					daoFactory.endTransaction();
					return;
				}
				
			}else{
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.setPrettyPrinting().create();
				// 不是很懂这个内部类
				Type listType = new TypeToken<List<Order>>(){}.getType();
				String json = gson.toJson(orderList, listType);
				
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = new PrintWriter(response.getWriter(), true);
				out.println(json);
				out.close();
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
