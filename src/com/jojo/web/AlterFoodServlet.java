package com.jojo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.FoodDao;
import com.jojo.model.Food;
import com.jojo.util.StringUtil;

/**
 * ����������request.setAttribute()����֮Ԥ�в��ã�����Ҫ�������
 * �����ܡ�
 * @author flash.J
 *
 */
public class AlterFoodServlet extends HttpServlet{

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
		String action = request.getParameter("action");

		// ����ʳ��
		if (action.equals("add")) {
			addFood(request, response);
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;

		// Ϊ�˰�addFood.jsp��ʾ���������������̵��Ժ�һ��������ô��˿�ķ�ʽ
		} else if (action.equals("showAdd")) {
			request.setAttribute("xxxjsp", "background/addFood.jsp");
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;
		
		// ����ʳ���б���ȻҪ����merchantID��
		} else if (action.equals("selectList")) {
			selectFoodList(request, response);
			request.setAttribute("xxxjsp", "background/foodList.jsp");
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;
		
		}else if(action.equals("selectFoodNum")){
			selectFoodNum(request, response);
			return ;  // AJAX�������ԾͲ�����ת������
			
		// ��ʾupdateFood.jsp��������add��ͬ��ҪԤ����ʾһ��������
		}else if(action.equals("showUpdate")){
			showUpdateFood(request, response);
			request.setAttribute("xxxjsp", "background/updateFood.jsp");
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;
		
		// ����ʳ��
		}else if(action.equals("update")){
			updateFood(request, response);
			response.sendRedirect("merchantMain.jsp");
			return;
		}
	}

	
	
	
	
	
	
	
	/**
	 * ��merchantId����ʳƷ���������˾�������������ܲ���Ҫ
	 * ����ôд�ţ�Ȼ�����Ż���
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void selectFoodNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int merchantId = Integer.parseInt(request.getParameter("merchantId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();
			int num = foodDao.selectFoodNum(merchantId);
			
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = new PrintWriter(response.getWriter(), true);
			out.println(num);
			out.close();
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * ���ұߣ�������outline
	 * @param request
	 * @param response
	 */
	private void showUpdateFood(HttpServletRequest request, HttpServletResponse response) {
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();
			Food food = foodDao.selectFood(foodId);
			request.setAttribute("food", food);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}
	/**
	 * ���ұ�
	 * @param request
	 * @param response
	 */
	private void updateFood(HttpServletRequest request, HttpServletResponse response) {
		String foodName = request.getParameter("foodName");
		double foodPrice = Double.parseDouble(request.getParameter("foodPrice"));
		String foodType = request.getParameter("foodType");
		String description = request.getParameter("description");
		int num = Integer.parseInt(request.getParameter("num"));
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		
		Food food = new Food();
		food.setFoodName(foodName);
		food.setFoodPrice(foodPrice);
		food.setFoodType(foodType);
		food.setDescription(description);
		food.setNum(num);
		food.setFoodId(foodId);
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			// �����Ż��ռ䰡
			FoodDao foodDao = daoFactory.createFoodDao();
			foodDao.updateFood(food);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * ���Ҷ�Ӧ�̼ҵ�ʳ��
	 * @param request
	 * @param response
	 */
	private void selectFoodList(HttpServletRequest request, HttpServletResponse response) {
		int merchantId = Integer.parseInt(request.getParameter("merchantId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FoodDao foodDao = daoFactory.createFoodDao();
			// ȡ�����̼���ص�ʳ��
			List<List<Food>> foodTypeList = foodDao.selectFoodList(merchantId);
			request.setAttribute("foodTypeList", foodTypeList);
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * ����ʳ��
	 * @param request
	 * @param response
	 */
	private void addFood(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		// �Ȼ�ȡ����
		String foodName = request.getParameter("foodName");
		double foodPrice = Double.parseDouble(request.getParameter("foodPrice"));
		String foodType = request.getParameter("foodType");
		String description = request.getParameter("description");
		if(StringUtil.isEmpty(description)){
			description = "���̼�ë��ûд....";
		}
		int num = Integer.parseInt(request.getParameter("num"));
		int merchantId = Integer.parseInt(request.getParameter("merchantId"));
		
		// ��װfood
		Food food = new Food();
		food.setFoodName(foodName);
		food.setFoodPrice(foodPrice);
		food.setFoodType(foodType);
		food.setDescription(description);
		food.setNum(num);
		
		// ��������ùر�����
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();
			foodDao.addFood(food, merchantId);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
