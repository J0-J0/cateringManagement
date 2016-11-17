package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.MerchantDao;
import com.jojo.model.Merchant;

public class MerchantLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String merchantName = request.getParameter("merchantName");
		String password = request.getParameter("password");

		// �п�
		if (merchantName == null || "".equals(merchantName) || password == null || "".equals(password)) {
			request.setAttribute("error", "�û��������벻��Ϊ�գ�");
			request.getRequestDispatcher("merchantLogin.jsp").forward(request, response);
			return;
		}

		DaoFactory daoFactory = new DaoFactory();
		daoFactory.beginConnectionScope();
		try {
			// ��������������
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();

			MerchantDao merchantDao = daoFactory.createMerchantDao();
			Merchant merchant = merchantDao.selectMerchant(merchantName, password);
			if (merchant == null) {
				request.setAttribute("error", "�û������������");
				request.getRequestDispatcher("merchantLogin.jsp").forward(request, response);
				return;
			} else {
				// ��ѯ�ɹ����ٻ�session
				HttpSession session = request.getSession();
				session.setAttribute("currentMerchant", merchant);
				response.sendRedirect("merchantMain.jsp");
			}

			// ������ѯ���ύûʲô��ϵ��
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		} finally {
			daoFactory.endConnectionScope();
		}
	}

}
