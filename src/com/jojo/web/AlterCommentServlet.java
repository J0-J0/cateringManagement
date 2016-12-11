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
import com.jojo.dao.FoodDao;
import com.jojo.model.FoodComment;
import com.jojo.model.Page;

public class AlterCommentServlet extends HttpServlet {

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
		String action=request.getParameter("action");
		if("add".equals(action)){
			addComment(request,response);
			return ;
			
		}else if("delete".equals(action)){
			deleteComment(request,response);
			return ;
			
		}else if("update".equals(action)){
			updateComment(request, response);
			return ;
			
		}else if("select".equals(action)){
			selectComment(request, response);
		}
	}
	
	
	
	
	
	
	/**
	 * 用户查询评论，该方法为ajax请求服务
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void selectComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int foodId = Integer.parseInt(request.getParameter("foodId"));		// 并不确定id是哪个，所以都取。
		int userId = Integer.parseInt(request.getParameter("userId"));
		int isPositive = Integer.parseInt(request.getParameter("isPositive"));
		boolean flag = Boolean.parseBoolean(request.getParameter("flag"));
		int p = Integer.parseInt(request.getParameter("page"));
		Page page = new Page(p, 10);
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();
			List<FoodComment> list = null;
			if(flag == true){
				list = foodDao.selectFoodComment(userId, isPositive, flag, page);
			}else{
				list = foodDao.selectFoodComment(foodId, isPositive, flag, page);
			}
			// 转为json
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.setPrettyPrinting().create();
			Type listType = new TypeToken<List<FoodComment>>(){}.getType();
			String json = gson.toJson(list, listType);
			
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = new PrintWriter(response.getWriter(), true);
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
	 * 用户更新评论
	 * @param request
	 * @param response
	 */
	private void updateComment(HttpServletRequest request, HttpServletResponse response) {
		String comment = request.getParameter("comment");
		int foodCommentId = Integer.parseInt(request.getParameter("foodCommentId"));
		int isPositive = Integer.parseInt(request.getParameter("isPositive"));
		FoodComment foodComment = new FoodComment();
		foodComment.setFoodCommentId(foodCommentId);
		foodComment.setIsPositive(isPositive);
		foodComment.setComment(comment);
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();			// 修改评论
			foodDao.updateFoodComment(foodComment);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

	/**
	 * 用户删除评论
	 * @param request
	 * @param response
	 */
	private void deleteComment(HttpServletRequest request, HttpServletResponse response) {
		int foodCommentId = Integer.parseInt(request.getParameter("foodCommentId"));
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();			// 修改评论
			foodDao.deleteFoodComment(foodCommentId);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}
	/**
	 * 用户新增评论
	 * @param request
	 * @param response
	 */
	private void addComment(HttpServletRequest request, HttpServletResponse response) {
		String comment = request.getParameter("comment");
		int userId = Integer.parseInt(request.getParameter("userId"));
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		int isPositive = Integer.parseInt(request.getParameter("isPositive"));
		FoodComment foodComment = new FoodComment();
		foodComment.setFoodId(foodId);
		foodComment.setUserId(userId);
		foodComment.setIsPositive(isPositive);
		foodComment.setComment(comment);
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			
			FoodDao foodDao = daoFactory.createFoodDao();			// 新增评论
			foodDao.addFoodComment(foodComment);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
