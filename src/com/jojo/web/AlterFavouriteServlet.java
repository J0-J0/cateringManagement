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
		
		if("add".equals(action)){						// �˴�Ϊajax����
			addFavourite(request, response);
			return ;
			
		}else if("delete".equals(action)){
			deleteFavourite(request, response);
			return ;
			
		}else if("select".equals(action))				// �˴�Ϊajax����
			selectFavouriteList(request, response);
	}

	
	
	
	
	
	
	/**
	 * �����ղؼУ�����һ���б�
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void selectFavouriteList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = new PrintWriter(response.getWriter(), true);
		
		// ����Ǵ�userMain.jsp�������Ļ���˵���û��ѵ�¼������ֱ����ȡuserId
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute("currentUser");
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FavouriteDao favouriteDao = daoFactory.createFavouriteDao();
			
			List<Favourite> favouriteList = favouriteDao.selectFavouriteList(currentUser.getUserId()); 
			
			// ת��json
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
	 * ɾ��ģ�飬�����
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
	 * ����ģ��
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void addFavourite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = new PrintWriter(response.getWriter(), true);
		
		int userId = 0;
		if(StringUtil.isEmpty(request.getParameter("userId"))){
			out.println("���ʧ�ܣ������Ƿ��¼��");
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
			
			boolean flag = favouriteDao.selectFavourite(userId, foodId);			// �������ݿ�����
			if(!flag == true){
				FoodDao foodDao = daoFactory.createFoodDao();				// DAO Ԥ��
				MerchantDao merchantDao = daoFactory.createMerchantDao();
				Food food = foodDao.selectFood(foodId);							// ����Ԥ��
				Merchant merchant = merchantDao.selectMerchant(food.getMerchantId());
				Favourite favourite = favouriteDao.createFavourite(food, merchant.getMerchantId(), merchant.getMerchantName(), userId);
				favouriteDao.addFavourite(favourite);							// ��������ݿ�
				
				out.println("��ӳɹ���");
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
