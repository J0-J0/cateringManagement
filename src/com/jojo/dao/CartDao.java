package com.jojo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jojo.model.Cart;
import com.jojo.model.Food;

public class CartDao {
	private Connection conn = null;

	/**
	 * 构造函数，获取连接
	 * 
	 * @param conn
	 */
	public CartDao(Connection conn) {
		super();
		this.conn = conn;
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建购物车，为往数据库增加购物车做准备
	 * @param merchantId
	 * @param merchantName
	 * @param userId
	 * @param userName
	 * @param foodId
	 * @return
	 */
	public Cart createCart(int merchantId, String merchantName, int userId, Food food, int num) {
		Cart cart = new Cart();
		
		cart.setFoodId(food.getFoodId());
		cart.setFoodName(food.getFoodName());
		cart.setFoodPrice(food.getFoodPrice());
		cart.setMerchantId(merchantId);
		cart.setMerchantName(merchantName);
		cart.setUserId(userId);
		cart.setNum(num);   // 第一次加入购物车，只会是1，也不对，日后有空慢慢更新吧
		cart.setSum(food.getFoodPrice());
		
		return cart;
	}
	/**
	 * 增删查改之增，成功返回 1
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int addCart(Cart cart) throws SQLException {
		String sql = "insert into t_cart values(null, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, cart.getUserId());
		pstmt.setInt(2, cart.getFoodId());
		pstmt.setInt(3, cart.getMerchantId());		
		pstmt.setString(4, cart.getMerchantName());		
		pstmt.setString(5, cart.getFoodName());
		pstmt.setDouble(6, cart.getFoodPrice());
		pstmt.setInt(7, cart.getNum());
		pstmt.setDouble(8, cart.getSum());

		return pstmt.executeUpdate();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 增删查改之删，成功返回1
	 * 
	 * @param cartId
	 * @return
	 * @throws SQLException
	 */
	public int deleteCart(int cartId) throws SQLException {
		String sql = "delete from t_cart where cartId = " + cartId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 批量形成订单时用到，根据userId 与 foodId删除对应的cart
	 * @param userId
	 * @param foodId
	 * @throws SQLException 
	 */
	public int deleteCart(int userId, int foodId) throws SQLException {
		String sql = "delete from t_cart where userId = "+userId+" and foodId = "+foodId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 存在为true，不存在为false 用于判重，食物添加至购物车时，要先判重
	 * 
	 * @param userId
	 * @param foodId
	 * @return
	 * @throws SQLException
	 */
	public boolean selectCart(int userId, int foodId) throws SQLException {
		boolean result = false;
		String sql = "select cartId from t_cart where userId = ? and foodId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, foodId);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int cartId = rs.getInt(1);
			if (cartId != 0)
				result = true;
			else
				result = false;
		}
	
		return result;
	}

	/**
	 * 增删查改之查，查一堆
	 * 此处要将查出的购物车商品按照merchantId进行分类，
	 * 这样方便填充userCart.jsp
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<List<Cart>> selectCart(int userId) throws SQLException {
		List<List<Cart>> cartList = new ArrayList<List<Cart>>();
		List<Cart> list = null;
		
		String sql = "select * from t_cart where userId = " + userId +" order by merchantId";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Cart cart = new Cart();
			cart.setCartId(rs.getInt("cartId"));
			cart.setUserId(rs.getInt("userId"));
			cart.setFoodId(rs.getInt("foodId"));
			cart.setMerchantId(rs.getInt("merchantId"));
			cart.setMerchantName(rs.getString("merchantName"));
			cart.setFoodName(rs.getString("foodName"));
			cart.setFoodPrice(rs.getDouble("foodPrice"));
			cart.setNum(rs.getInt("num"));
			cart.setSum(rs.getDouble("sum"));

			if(list == null){
				list = new ArrayList<Cart>();
				list.add(cart);
				cartList.add(list);
			}else if(list.get(0).getMerchantId() == cart.getMerchantId()){
				list.add(cart);
			}else{
				list = new ArrayList<Cart>();
				list.add(cart);
				cartList.add(list);
			}
		}
		return cartList;
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * 增删查改之改，成功返回1
	 * 老实说我只觉得会修改数量, 顺带着变一下总价
	 * @return
	 * @throws SQLException 
	 */
	public int updateCart(int cartId, int num, float sum) throws SQLException {
		String sql = "update t_cart set num = ?, sum = ? where cartId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		
		pstmt.setDouble(2, sum);
		pstmt.setInt(3, cartId);
		
		int row = pstmt.executeUpdate();
		return row;
	}
}
