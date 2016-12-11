package com.jojo.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.FoodDao;
import com.jojo.dao.MerchantDao;
import com.jojo.model.Food;
import com.jojo.model.FoodComment;
import com.jojo.model.Page;


public class FoodDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		String merchantName = request.getParameter("merchantName");
		
		Cookie history = null;    // 侧边栏历史记录
		String[] historyId = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies){
			if("history".equals(c.getName())){
				history = c;
				break ;
			}
		}
		if(history != null){
			String tmp = history.getValue();
			historyId = tmp.split(":"); 
			if(historyId.length > 8){				// 历史记录大于8个就从下往上顶，即删除第一个
				String regex = ":"+historyId[1];
				tmp.replace(regex, "");
			}
			history.setValue(tmp+":"+foodId);
			history.setMaxAge(24*60*60);    // 设置一天
		}else{
			history = new Cookie("history", ":"+foodId);
			history.setMaxAge(24*60*60);
		}
		response.addCookie(history);
		
		
		DaoFactory daoFactory = new DaoFactory();
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FoodDao foodDao = daoFactory.createFoodDao();
			
			// 根据foodId查食物以及评论，其实主要还在评论
			Food food = foodDao.selectFood(foodId);
			if(merchantName == null || "".equals(merchantName)){
				MerchantDao merchantDao = daoFactory.createMerchantDao();
				merchantName = merchantDao.selectMerchantName(food.getMerchantId());
			}
			
			Page page = new Page(1, 10);
			List<FoodComment> positiveList = foodDao.selectFoodComment(foodId, 1, false, page);
			List<FoodComment> negativeList = foodDao.selectFoodComment(foodId, 0, false, page);
			// 查历史记录
			List<Food> historyList = new ArrayList<Food>();
			if (historyId != null) {
				for (int i = 1; i < historyId.length; i++) {
					historyList.add(foodDao.selectFood(Integer.parseInt(historyId[i])));
				}
			}
			request.setAttribute("food", food);
			request.setAttribute("merchantName", merchantName);
			request.setAttribute("positiveList", positiveList);
			request.setAttribute("negativeList", negativeList);
			request.setAttribute("historyList", historyList);
			
			request.getRequestDispatcher("foodDetail.jsp").forward(request, response);
			
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}

}
