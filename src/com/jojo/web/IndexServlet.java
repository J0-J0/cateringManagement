package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.MerchantDao;
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
		DaoFactory daoFactory = new DaoFactory();
		// 开启连接与事务
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			MerchantDao merchantDao = daoFactory.createMerchantDao();
			List<Merchant> merchantList = merchantDao.selectAllMerchants();

			// 取出来的时候也是Object，嘿，还必须得去看JSTL
			request.setAttribute("merchantList", merchantList);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}
	
}
