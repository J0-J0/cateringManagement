package com.jojo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jojo.model.Favourite;
import com.jojo.model.Food;

public class FavouriteDao {

	private Connection conn = null;
	/**
	 * 构造函数，获取连接
	 * @param conn
	 */
	public FavouriteDao(Connection conn) {
		this.conn = conn;
	}
	
	
	public Favourite createFavourite(Food food, int merchantId, String merchantName, int userId) {
		Favourite favourite = new Favourite();
		favourite.setFoodId(food.getFoodId());
		favourite.setFoodName(food.getFoodName());
		favourite.setFoodPrice(food.getFoodPrice());
		favourite.setMerchantId(merchantId);
		favourite.setMerchantName(merchantName);
		favourite.setUserId(userId);
		
		return favourite;
	}

	
	
	
	
	
	/**
	 * 增删查改之增，往t_favourite增加数据
	 * @param favourite
	 * @return
	 * @throws SQLException 
	 */
	public int addFavourite(Favourite favourite) throws SQLException {
		String sql = "insert into t_favourite values(null, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, favourite.getUserId());
		pstmt.setInt(2, favourite.getFoodId());
		pstmt.setInt(3, favourite.getMerchantId());		
		pstmt.setString(4, favourite.getMerchantName());		
		pstmt.setString(5, favourite.getFoodName());
		pstmt.setDouble(6, favourite.getFoodPrice());
		
		return pstmt.executeUpdate();
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * 增删查改至删，最简单的模块
	 * @param favouriteId
	 * @throws SQLException 
	 */
	public int deleteFavourite(int favouriteId) throws SQLException {
		String sql = "delete from t_favourite where favouriteId="+favouriteId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 增删查改之查，根据foodId与userId查找，是添加前的判重
	 * @param userId
	 * @param foodId
	 * @return
	 * @throws SQLException 
	 */
	public boolean selectFavourite(int userId, int foodId) throws SQLException {
		boolean result = false;
		String sql = "select favouriteId from t_favourite where userId = ? and foodId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, foodId);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int favouriteId = rs.getInt(1);
			if (favouriteId != 0)
				result = true;
			else
				result = false;
		}
		return result;
	}

	/**
	 * 查找favorite列表
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public List<Favourite> selectFavouriteList(int userId) throws SQLException {
		List<Favourite> list = new ArrayList<Favourite>();
		String sql = "select * from t_favourite where userId = "+userId;
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();		
		while (rs.next()) {
			Favourite favourite = new Favourite();
			favourite.setFavouriteId(rs.getInt("favouriteId"));
			favourite.setUserId(rs.getInt("userId"));
			favourite.setFoodId(rs.getInt("foodId"));
			favourite.setMerchantId(rs.getInt("merchantId"));
			favourite.setMerchantName(rs.getString("merchantName"));
			favourite.setFoodName(rs.getString("foodName"));
			favourite.setFoodPrice(rs.getDouble("foodPrice"));
			list.add(favourite);
		}
		return list;
	}
}
