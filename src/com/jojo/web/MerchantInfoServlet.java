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

public class MerchantInfoServlet extends HttpServlet {

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
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("login".equals(action)){
			merchantLogin(request, response);
			return ;
		}else if("update".equals(action)){
			merchantUpdate(request, response);
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return ;
		}
	}

	/**
	 * �̼��޸ĸ�����Ϣ
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void merchantUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Merchant currentMerchant = (Merchant)session.getAttribute("currentMerchant");

		String merchantName = request.getParameter("merchantName");
		String password = request.getParameter("password");
		String merchantIdCard = request.getParameter("merchantIdCard");
		String merchantRealName = request.getParameter("merchantRealName");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String merchantTel = request.getParameter("merchantTel");
		String description = request.getParameter("description");
		
		currentMerchant.setMerchantName(merchantName);
		currentMerchant.setPassword(password);
		currentMerchant.setMerchantIdCard(Integer.parseInt(merchantIdCard));
		currentMerchant.setMerchantRealName(merchantRealName);
		currentMerchant.setSex(sex);
		currentMerchant.setAge(Integer.parseInt(age));
		currentMerchant.setMerchantTel(merchantTel);
		currentMerchant.setDescription(description);
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			MerchantDao merchantDao = daoFactory.createMerchantDao();

			merchantDao.updateMerchant(currentMerchant);
			
			daoFactory.endTransaction();  // �ύ����
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * �̼ҵ�¼
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void merchantLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		try {
			// ��������������
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();

			MerchantDao merchantDao = daoFactory.createMerchantDao();
			Merchant merchant = merchantDao.selectMerchant(merchantName, password);
			if (merchant == null) {
				request.setAttribute("error", "�û������������");
				request.getRequestDispatcher("merchantLogin.jsp").forward(request, response);
				daoFactory.endTransaction();  // �ر�����
				return ;
			} else {
				// ��ѯ�ɹ����ٻ�session
				HttpSession session = request.getSession();
				session.setAttribute("currentMerchant", merchant);
				session.setMaxInactiveInterval(60*60);
				response.sendRedirect("merchantMain.jsp");
			}
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		} finally {
			daoFactory.endConnectionScope();
		}
	}

}
