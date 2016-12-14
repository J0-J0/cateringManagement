package com.jojo.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jojo.model.Food;
import com.jojo.model.FoodComment;
import com.jojo.model.Page;

public class FoodDao {

	private Connection conn = null;

	/**
	 * ���캯��
	 * 
	 * @param conn
	 */
	public FoodDao(Connection conn) {
		this.conn = conn;
	}

	
	
	
	
	
	
	
	/**
	 * �̼����ʳ��
	 * 
	 * ��ǰ̨��װFood����,����Ҫ��Ӧ��merchantId
	 * 
	 * @param food
	 * @param merchant
	 * @return
	 * @throws SQLException
	 */
	public int addFood(Food food, int merchantId) throws SQLException {
		String sql = "insert into t_food values(null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, food.getFoodName());
		pstmt.setDouble(2, food.getFoodPrice());
		pstmt.setString(3, food.getFoodType());
		pstmt.setString(4, food.getDescription());
		pstmt.setInt(5, food.getNum());
		// ��������ʳƷʱ��
		food.setAddTime(new java.util.Date(System.currentTimeMillis()));
		Timestamp ts = new Timestamp(food.getAddTime().getTime());
		pstmt.setTimestamp(6, ts);
		pstmt.setInt(7, merchantId);

		return pstmt.executeUpdate();
	}

	/**
	 * ��ʳ�����ͼƬ,�ɹ�����1�� ���ɹ�����0�� ûͼƬ����-1
	 * 
	 * @param pic
	 * @param foodId
	 * @return
	 * @throws SQLException
	 */
	public int addFoodPic(File pic, Food food) throws SQLException {
		int row = 0;
		String sql = "insert into t_foodpic values(null,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try {

			InputStream in = new FileInputStream(pic);
			pstmt.setBinaryStream(1, in, pic.length());
			pstmt.setInt(2, food.getFoodId());
			row = pstmt.executeUpdate();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			return -1;
		}
		return row;
	}

	/**
	 * ��ʳ���������
	 * 
	 * @param comment
	 * @param foodId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public int addFoodComment(FoodComment foodComment) throws SQLException {
		String sql = "insert into t_foodcomment values(null, ?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, foodComment.getComment());
		pstmt.setInt(2, foodComment.getUserId());
		pstmt.setString(3, foodComment.getUserName());
		pstmt.setInt(4, foodComment.getFoodId());
		pstmt.setString(5, foodComment.getFoodName());
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		pstmt.setTimestamp(6, ts);
		pstmt.setInt(7, foodComment.getIsPositive());

		return pstmt.executeUpdate();
	}

	
	
	
	
	
	
	
	
	
	/**
	 * ɾ������ʳƷ
	 * 
	 * @param foodId
	 * @return
	 * @throws SQLException
	 */
	public int deleteFood(int foodId) throws SQLException {
		String sql = "delete from t_food where foodId  =" + foodId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * �̼�ɾ��ͼƬ���ɹ�����1
	 * 
	 * ���������е���֣���������Ψһ��ʶһ�����ݣ���һ������û��
	 * 
	 * @param foodId
	 * @param foodPicId
	 * @return
	 * @throws SQLException
	 */
	public int deleteFoodPic(int foodId, int foodPicId) throws SQLException {
		String sql = "delete from t_foodpic where foodId = ? and foodPicId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, foodId);
		pstmt.setInt(2, foodPicId);
		int row = pstmt.executeUpdate();

		return row;
	}

	/**
	 * ɾ��ʳ�����ۣ��ɹ�����1
	 * 
	 * Ӧ�û���ֻ��Ҫһ��������
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int deleteFoodComment(int foodCommentId) throws SQLException {
		String sql = "delete from t_foodcomment where foodCommentId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, foodCommentId);
		int row = pstmt.executeUpdate();
		return row;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ������̵ģ����ﻹ�����Ҫ��ʲô�������飬������ȷ����
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public Food selectFood(int foodId) throws SQLException {
		Food resultFood = null;

		String sql = "select * from t_food where foodId = " + foodId;
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()){
			resultFood = new Food();
			resultFood.setFoodId(rs.getInt("foodId"));
			resultFood.setFoodName(rs.getString("foodName"));
			resultFood.setFoodPrice(rs.getDouble("foodPrice"));
			resultFood.setFoodType(rs.getString("foodType"));
			resultFood.setDescription(rs.getString("description"));
			resultFood.setNum(rs.getInt("num"));
			// ��ȡʱ��
			Timestamp ts = rs.getTimestamp("addTime");
			resultFood.setAddTime(new java.util.Date(ts.getTime()));
			resultFood.setAddTime_();
			resultFood.setMerchantId(rs.getInt("merchantId"));
		}
		
		return resultFood;
	}
	
	/**
	 * ��������������Ҫ����sql���
	 * @param sql
	 * @throws SQLException 
	 */
	public List<Food> selectFood(String sql) throws SQLException {
		List<Food> list = new ArrayList<Food>();
		
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){
			Food food = new Food();
			food.setFoodId(rs.getInt("foodId"));
			food.setFoodName(rs.getString("foodName"));
			food.setFoodPrice(rs.getDouble("foodPrice"));
			food.setFoodType(rs.getString("foodType"));
			food.setDescription(rs.getString("description"));
			food.setNum(rs.getInt("num"));
			// ��ȡʱ��
			Timestamp ts = rs.getTimestamp("addTime");
			food.setAddTime(new java.util.Date(ts.getTime()));
			food.setAddTime_();
			food.setMerchantId(rs.getInt("merchantId"));
			list.add(food);
		}
		return list;
	}
	
	
	/**
	 * ����merchantId����ѯʳƷ��Ŀ��ʧ�ܷ���0
	 * @param merchantId
	 * @return
	 * @throws SQLException 
	 */
	public int selectFoodNum(int merchantId) throws SQLException {
		String sql = "select count(foodId) from t_food where merchantId = " + merchantId;
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		int result = 0;
		if(rs.next()){
			result = rs.getInt(1);
		}
		return result;
	}
	
	/**
	 * ����foodId��ѯʳ������
	 * @param foodId
	 * @return
	 * @throws SQLException 
	 */
	public String selectFoodName(int foodId) throws SQLException {
		String sql = "select foodName from t_food where foodId = "+ foodId;
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		String result = null;
		if(rs.next()){
			result = rs.getString(1);
		}
		return result;
	}
	
	/**
	 * ����merchantId�������ʳƷ�������������Ƕ�׵�list
	 * ��Ӧ�ģ�������һ��������ѯ�ķ���
	 * @param merchantId
	 * @return
	 * @throws SQLException 
	 */
	public List<List<Food>> selectFoodList(int merchantId) throws SQLException{
		List<List<Food>> foodTypeList = new ArrayList<List<Food>>();
		List<Food> foodList = new ArrayList<Food>();
		foodTypeList.add(foodList);
		
		String sql = "select * from t_food where merchantId = "+merchantId;
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		Food f = null;
		while(rs.next()){
			// ��������������������С�Խд��Ч���룡
			f = new Food();
			f.setFoodId(rs.getInt("foodId"));
			f.setFoodName(rs.getString("foodName"));
			f.setFoodPrice(rs.getDouble("foodPrice"));
			f.setFoodType(rs.getString("foodType"));
			f.setDescription(rs.getString("description"));
			f.setNum(rs.getInt("num"));
			// ��ȡʱ��
			Timestamp ts = rs.getTimestamp("addTime");
			f.setAddTime(new java.util.Date(ts.getTime()));
			f.setAddTime_();
			f.setMerchantId(rs.getInt("merchantId"));
			
			if(foodList.isEmpty()){
				foodList.add(f);
			}else if(foodList.get(0).getFoodType().equals(f.getFoodType())){
				foodList.add(f);
			}else{
				foodList = new ArrayList<Food>();
				foodTypeList.add(foodList);
				foodList.add(f);
			}
		}
		return foodTypeList;
	}
	/**
	 * ��������ѯ������list
	 * ����ճ���ĸо�������ã�
	 * @param merchantId
	 * @param foodType
	 * @return
	 * @throws SQLException 
	 */
	public List<Food> selectFoodByType(int merchantId, String foodType) throws SQLException{
		List<Food> foodList = new ArrayList<Food>();
		String sql = "select * from t_food where merchantId = ? and foodType = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, merchantId);
		pstmt.setString(2, foodType);
		ResultSet rs = pstmt.executeQuery();
		
		Food f = null;
		while(rs.next()){
			f = new Food();
			f.setFoodId(rs.getInt("foodId"));
			f.setFoodName(rs.getString("foodName"));
			f.setFoodPrice(rs.getDouble("foodPrice"));
			f.setFoodType(rs.getString("foodType"));
			f.setDescription(rs.getString("description"));
			f.setNum(rs.getInt("num"));
			// ��ȡʱ��
			Timestamp ts = rs.getTimestamp("addTime");
			f.setAddTime(new java.util.Date(ts.getTime()));
			f.setAddTime_();
			f.setFoodId(rs.getInt("merchantId"));
			
			foodList.add(f);
		}
		
		return foodList;
	}
	
	/**
	 * �û����̼Ҳ�ѯ���ۣ�����һ��list
	 * 
	 * ����Ϊʳ��id���Ƿ�������û���Ļ����̼Ҳ�ġ�
	 * ���ȷ��ҳ�� 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public List<FoodComment> selectFoodComment(int id, int isPositive, boolean flag, Page page) throws SQLException {
		List<FoodComment> commentList = new ArrayList<FoodComment>();
		StringBuilder sql = null;			// ����flagȷ����userId����foodId
		if (flag == true) {
			sql = new StringBuilder("select * from t_foodcomment where userId = "+id);
		} else {
			sql = new StringBuilder("select * from t_foodcomment where foodId = "+id);
		}
		sql.append(" and isPositive = "+isPositive);		// ע�������涼�и��ո��
		sql.append(" order by addTime desc");
		sql.append(" limit "+page.getStart()+","+page.getRows());
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString(), 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();

		
		while (rs.next()) {
			FoodComment foodComment = new FoodComment();
			foodComment.setFoodCommentId(rs.getInt("foodCommentId"));
			foodComment.setUserId(rs.getInt("userId"));
			foodComment.setUserName(rs.getString("userName"));
			foodComment.setComment(rs.getString("comment"));
			foodComment.setFoodId(rs.getInt("foodId"));
			foodComment.setFoodName(rs.getString("foodName"));
			
			java.util.Date addTime = new java.util.Date(rs.getTimestamp("addTime").getTime());
			foodComment.setAddTime(addTime);
			foodComment.setIsPositive(isPositive);
			
			commentList.add(foodComment);
		}
		return commentList;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ��ȥ���ݿ���ʳƷ�����������ɶ���ʳƷ��������
	 * @param food
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public int updateFoodNum(int  foodId, int num) throws SQLException{
		String sql = "update t_food set num=num-? where foodId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		pstmt.setInt(2, foodId);
		
		int row = pstmt.executeUpdate();
		return row;
	}
	
	/**
	 * ���±������ϴ�ͼƬʲô�ģ��ô�Ӧ�û������ �÷������̼ҵ��á���ˣ��̼Ҳ��ɼ�����Ϣ�����̼�����Ϊ������Ϣ�Ͳ��ü���
	 * 
	 * ������ô��foodId��ȡ�������أ�
	 * 
	 * @param food
	 * @return
	 * @throws SQLException
	 */
	public int updateFood(Food food) throws SQLException {
		String sql = "update t_food set foodName = ?, foodPrice = ?, description = ?, num = ?, foodType=? where foodId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, food.getFoodName());
		pstmt.setDouble(2, food.getFoodPrice());
		pstmt.setString(3, food.getDescription());
		pstmt.setInt(4, food.getNum());
		pstmt.setString(5, food.getFoodType());
		pstmt.setInt(6, food.getFoodId());

		int row = pstmt.executeUpdate();
		return row;
	}
	
	
	/**
	 * �û��޸�����ʱ��Ͳ��ø���
	 * @param foodComment
	 * @return
	 * @throws SQLException
	 */
	public int updateFoodComment(FoodComment foodComment) throws SQLException{
		String sql = "update t_foodComment set comment = ?, isPositive = ? where foodCommentId = " + foodComment.getFoodCommentId();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, foodComment.getComment());
		pstmt.setInt(2, foodComment.getIsPositive());
		pstmt.setInt(3, foodComment.getFoodCommentId());

		return pstmt.executeUpdate();
	}
}
