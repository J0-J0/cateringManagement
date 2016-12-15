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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.FavouriteDao;
import com.jojo.dao.FoodDao;
import com.jojo.dao.MerchantDao;
import com.jojo.model.Favourite;
import com.jojo.model.Food;
import com.jojo.model.Merchant;
import com.jojo.model.User;
import com.jojo.util.StringUtil;

public class AlterFavouriteServlet extends HttpServlet {

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
		
		if("add".equals(action)){						// 此处为ajax请求
			addFavourite(request, response);
			return ;
			
		}else if("delete".equals(action)){
			deleteFavourite(request, response);
			return ;
			
		}else if("select".equals(action))				// 此处为ajax请求
			selectFavouriteList(request, response);
	}

	
	
	
	
	
	
	/**
	 * 查找收藏夹，返回一串列表。
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void selectFavouriteList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = new PrintWriter(response.getWriter(), true);
		
		// 如果是从userMain.jsp传过来的话，说明用户已登录，可以直接提取userId
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute("currentUser");
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FavouriteDao favouriteDao = daoFactory.createFavouriteDao();
			
			List<Favourite> favouriteList = favouriteDao.selectFavouriteList(currentUser.getUserId()); 
			
			// 转成json
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.setPrettyPrinting().create();
			Type listType = new TypeToken<List<Favourite>>(){}.getType();
			String json = gson.toJson(favouriteList, listType);
			out.println(json);
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
	 * 删除模块，这个简单
	 * @param request
	 * @param response
	 */
	private void deleteFavourite(HttpServletRequest request, HttpServletResponse response) {
		int favouriteId = Integer.parseInt(request.getParameter("favouriteId"));
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FavouriteDao favouriteDao = daoFactory.createFavouriteDao();
			favouriteDao.deleteFavourite(favouriteId);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
		
	}

	/**
	 * 增加模块
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void addFavourite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = new PrintWriter(response.getWriter(), true);
		
		int userId = 0;
		if(StringUtil.isEmpty(request.getParameter("userId"))){
			out.println("添加失败，请检查是否登录！");
			out.close();
			return ;
		}else{
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FavouriteDao favouriteDao = daoFactory.createFavouriteDao();
			
			boolean flag = favouriteDao.selectFavourite(userId, foodId);			// 进入数据库判重
			if(!flag == true){
				FoodDao foodDao = daoFactory.createFoodDao();				// DAO 预备
				MerchantDao merchantDao = daoFactory.createMerchantDao();
				Food food = foodDao.selectFood(foodId);							// 数据预备
				Merchant merchant = merchantDao.selectMerchant(food.getMerchantId());
				Favourite favourite = favouriteDao.createFavourite(food, merchant.getMerchantId(), merchant.getMerchantName(), userId);
				favouriteDao.addFavourite(favourite);							// 添加至数据库
				
				out.println("添加成功！");
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
