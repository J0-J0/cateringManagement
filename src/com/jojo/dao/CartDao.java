package com.jojo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jojo.model.Cart;

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
	 * 增删查改之增，成功返回 1
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int addCart(Cart cart) throws SQLException {
		String sql = "insert into t_cart values(null, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, cart.getUserId());
		pstmt.setInt(2, cart.getFoodId());
		pstmt.setInt(3, cart.getMerchantId());		
		pstmt.setString(4, cart.getFoodName());
		pstmt.setDouble(5, cart.getFoodPrice());
		pstmt.setInt(6, cart.getNum());
		pstmt.setDouble(7, cart.getSum());

		int row = pstmt.executeUpdate();
		return row;
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
		int row = pstmt.executeUpdate();
		return row;
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
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, foodId);
		ResultSet rs = pstmt.executeQuery();
		try {
			if (rs.next()) {
				int cartId = rs.getInt("userId");
				if (cartId != 0)
					result = true;
				else
					result = false;
			}
		} finally {
			if (rs != null) {// 关掉！
				rs.close();
			}
		}
		return result;
	}

	/**
	 * 增删查改之查，查一堆
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<Cart> selectCart(int userId) throws SQLException {
		List<Cart> list = null;
		String sql = "select * from t_cart where userId = " + userId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try {
			list = new ArrayList<Cart>();
			while (rs.next()) {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cartId"));
				cart.setUserId(rs.getInt("userId"));
				cart.setFoodId(rs.getInt("foodId"));
				cart.setMerchantId(rs.getInt("merchantId"));
				cart.setFoodName(rs.getString("foodName"));
				cart.setFoodPrice(rs.getDouble("foodPrice"));
				cart.setNum(rs.getInt("num"));
				cart.setSum(rs.getDouble("sum"));

				list.add(cart);
			}
		} finally {
			// 关掉！
			if (rs != null)
				rs.close();
		}
		return list;
	}

	/**
	 * 增删查改之改，成功返回1
	 * 老实说我只觉得会修改数量, 顺带着变一下总价
	 * @return
	 * @throws SQLException 
	 */
	public int updateCart(Cart cart, int num) throws SQLException {
		String sql = "update t_cart set num = ? sum = ? where cartId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		
		//修改总价
		double sum = cart.getFoodPrice() * num;
		pstmt.setDouble(2, sum);
		pstmt.setInt(3, cart.getCartId());
		
		int row = pstmt.executeUpdate();
		return row;
	}
}
