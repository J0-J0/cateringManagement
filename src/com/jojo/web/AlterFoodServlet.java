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
 * 大量依赖了request.setAttribute()，总之预感不好，还是要快点上手
 * 三大框架。
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

		// 增加食物
		if (action.equals("add")) {
			addFood(request, response);
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;

		// 为了把addFood.jsp显示出来。。。。奶奶的以后一定不用这么屌丝的方式
		} else if (action.equals("showAdd")) {
			request.setAttribute("xxxjsp", "background/addFood.jsp");
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;
		
		// 查找食物列表，当然要根据merchantID来
		} else if (action.equals("selectList")) {
			selectFoodList(request, response);
			request.setAttribute("xxxjsp", "background/foodList.jsp");
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;
		
		}else if(action.equals("selectFoodNum")){
			selectFoodNum(request, response);
			return ;  // AJAX请求，所以就不做跳转处理了
			
		// 显示updateFood.jsp，另外与add不同，要预先显示一部分数据
		}else if(action.equals("showUpdate")){
			showUpdateFood(request, response);
			request.setAttribute("xxxjsp", "background/updateFood.jsp");
			request.getRequestDispatcher("merchantMain.jsp").forward(request, response);
			return;
		
		// 更新食物
		}else if(action.equals("update")){
			updateFood(request, response);
			response.sendRedirect("merchantMain.jsp");
			return;
		}
	}

	
	
	
	
	
	
	
	/**
	 * 按merchantId查找食品数量，个人觉得这个方法可能不需要
	 * 先这么写着，然后在优化吧
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
	 * 看右边，别忘了outline
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
	 * 看右边
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
			
			// 存在优化空间啊
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
	 * 查找对应商家的食物
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
			// 取出与商家相关的食物
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
	 * 新增食物
	 * @param request
	 * @param response
	 */
	private void addFood(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		// 先获取参数
		String foodName = request.getParameter("foodName");
		double foodPrice = Double.parseDouble(request.getParameter("foodPrice"));
		String foodType = request.getParameter("foodType");
		String description = request.getParameter("description");
		if(StringUtil.isEmpty(description)){
			description = "该商家毛都没写....";
		}
		int num = Integer.parseInt(request.getParameter("num"));
		int merchantId = Integer.parseInt(request.getParameter("merchantId"));
		
		// 封装food
		Food food = new Food();
		food.setFoodName(foodName);
		food.setFoodPrice(foodPrice);
		food.setFoodType(foodType);
		food.setDescription(description);
		food.setNum(num);
		
		// 放在外面好关闭连接
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
