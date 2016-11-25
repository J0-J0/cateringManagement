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
import com.jojo.model.User;

public class FoodDao {

	private Connection conn = null;

	/**
	 * 构造函数
	 * 
	 * @param conn
	 */
	public FoodDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 商家添加食物
	 * 
	 * 从前台封装Food对象,并需要相应的merchantId
	 * 
	 * @param food
	 * @param merchant
	 * @return
	 * @throws SQLException
	 */
	public int addFood(Food food, int merchantId) throws SQLException {
		String sql = "insert into t_food values(null,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, food.getFoodName());
		pstmt.setDouble(2, food.getFoodPrice());
		pstmt.setString(3, food.getFoodType());
		pstmt.setString(4, food.getDescription());
		pstmt.setInt(5, food.getNum());
		pstmt.setInt(6, food.getPositive());
		pstmt.setInt(7, food.getNegative());

		// 插入新增食品时间
		food.setAddTime(new java.util.Date(System.currentTimeMillis()));
		Timestamp ts = new Timestamp(food.getAddTime().getTime());
		pstmt.setTimestamp(8, ts);
		pstmt.setInt(9, merchantId);

		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * 给食物添加图片,成功返回1， 不成功返回0， 没图片返回-1
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

			// 这个IO类型就自己处理一下吧，咦，用户上传时没选中图片是怎么弄得？
			// 要不然我catch里return一个 -1？
			e.printStackTrace();
			return -1;
		}
		return row;
	}

	/**
	 * 给食物添加评论
	 * 
	 * @param comment
	 * @param foodId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public int addFoodComment(String comment, int foodId, int userId) throws SQLException {
		// 我总有一种这个方法要重写的预感

		int row = 0;
		String sql = "insert into t_foodcomment values(null, ?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, comment);
		pstmt.setInt(2, userId);
		pstmt.setInt(3, foodId);

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		pstmt.setTimestamp(4, ts);

		row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * 删除单个食品
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
	 * 商家删除图片，成功返回1
	 * 
	 * 参数看着有点奇怪，主键可以唯一标识一行数据，另一个好像没用
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
	 * 删除食物评论，成功返回1
	 * 
	 * 应该还是只需要一个主键吧
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
	 * 这里，奶奶的，这里还不清楚要用什么参数来查，需求不明确啊。
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
			resultFood.setPositive(rs.getInt("positive"));
			resultFood.setNegative(rs.getInt("negative"));
			// 获取时间
			Timestamp ts = rs.getTimestamp("addTime");
			resultFood.setAddTime(new java.util.Date(ts.getTime()));
			resultFood.setAddTime_();
			resultFood.setMerchantId(rs.getInt("merchantId"));
		}
		
		return resultFood;
	}
	
	/**
	 * 根据merchantId查出所有食品，按类别排序，是嵌套的list
	 * 相应的，还会有一个按类别查询的方法
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
			// 又来啦，又来啦，开动小脑筋，写有效代码！
			f = new Food();
			f.setFoodId(rs.getInt("foodId"));
			f.setFoodName(rs.getString("foodName"));
			f.setFoodPrice(rs.getDouble("foodPrice"));
			f.setFoodType(rs.getString("foodType"));
			f.setDescription(rs.getString("description"));
			f.setNum(rs.getInt("num"));
			f.setPositive(rs.getInt("positive"));
			f.setNegative(rs.getInt("negative"));
			// 获取时间
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
	 * 根据类别查询，返回list
	 * 复制粘贴的感觉真他妈好！
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
			f.setPositive(rs.getInt("positive"));
			f.setNegative(rs.getInt("negative"));
			// 获取时间
			Timestamp ts = rs.getTimestamp("addTime");
			f.setAddTime(new java.util.Date(ts.getTime()));
			f.setAddTime_();
			f.setFoodId(rs.getInt("merchantId"));
			
			foodList.add(f);
		}
		
		return foodList;
	}
	
	/**
	 * 用户查询自己的历史评论，返回一个list
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public List<FoodComment> selectFoodComment(User user) throws SQLException {
		List<FoodComment> list = null;
		String sql = "select * from t_foodcomment where userId = " + user.getUserId();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try {
			list = new ArrayList<FoodComment>();
			while (rs.next()) {
				FoodComment foodComment = new FoodComment();
				foodComment.setFoodCommentId(rs.getInt("foodCommentId"));
				foodComment.setComment(rs.getString("comment"));
				foodComment.setUserId(rs.getInt("userId"));
				foodComment.setFoodId(rs.getInt("foodId"));

				// 时间转换
				java.util.Date addTime = new java.util.Date(rs.getTimestamp("addTime").getTime());
				foodComment.setAddTime(addTime);
				foodComment.setAddTime_();

				list.add(foodComment);
			}
		} finally {
			// 把他关掉！
			if (rs != null)
				rs.next();
		}
		return list;
	}

	/**
	 * 根据商品ID查询所有评论，返回list
	 * 
	 * @param food
	 * @return
	 * @throws SQLException
	 */
	public List<FoodComment> selectFoodComment(int  foodId) throws SQLException {
		List<FoodComment> list = null;
		String sql = "select * from t_foodcomment where foodId = " + foodId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try {
			list = new ArrayList<FoodComment>();
			while (rs.next()) {
				FoodComment foodComment = new FoodComment();
				foodComment.setFoodCommentId(rs.getInt("foodCommentId"));
				foodComment.setComment(rs.getString("comment"));
				foodComment.setUserId(rs.getInt("userId"));
				foodComment.setFoodId(rs.getInt("foodId"));

				// 时间转换
				java.util.Date addTime = new java.util.Date(rs.getTimestamp("addTime").getTime());
				foodComment.setAddTime(addTime);
				foodComment.setAddTime_();

				list.add(foodComment);
			}
		} finally {
			// 关掉！
			if (rs != null)
				rs.close();
		}
		return list;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 减去数据库中食品数量，参数由订单食品数量决定
	 * @param food
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public int updateFoodNum(Food food, int num) throws SQLException{
		String sql = "update t_food set num = ? where foodId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, food.getNum() - num);
		pstmt.setInt(2, food.getFoodId());
		
		int row = pstmt.executeUpdate();
		return row;
	}
	
	/**
	 * 更新表，重新上传图片什么的，用处应该还蛮大的 该方法由商家调用。因此，商家不可见的信息，与商家无能为力的信息就不用加了
	 * 
	 * 这是怎么把foodId获取出来的呢？
	 * 
	 * @param food
	 * @return
	 * @throws SQLException
	 */
	public int updateFood(Food food) throws SQLException {
		String sql = "update t_food set foodName = ?, foodPrice = ?, description = ?, num = ? where foodId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, food.getFoodName());
		pstmt.setDouble(2, food.getFoodPrice());
		pstmt.setString(3, food.getDescription());
		pstmt.setInt(4, food.getNum());
		pstmt.setInt(5, food.getFoodId());

		int row = pstmt.executeUpdate();
		return row;
	}
}
