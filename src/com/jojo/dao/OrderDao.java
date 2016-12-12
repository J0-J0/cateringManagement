package com.jojo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jojo.model.Food;
import com.jojo.model.Merchant;
import com.jojo.model.Order;
import com.jojo.model.OrderFood;
import com.jojo.model.User;

public class OrderDao {
	private Connection conn;

	/**
	 * 构造函数，获取连接
	 * 
	 * @param conn
	 */
	public OrderDao(Connection conn) {
		this.conn = conn;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 生成Order，预先准备一些基础信息 
	 * 方便编码，改善视觉体验。
	 * 
	 * @param food
	 * @param user
	 * @param orderFoodList 
	 * @return
	 */
	public Order createOrder(User user, Merchant merchant, List<OrderFood> orderFoodList) {
		Order resultOrder = new Order();
		
		resultOrder.setUserId(user.getUserId());
		resultOrder.setUserIdCard(user.getUserIdCard());
		resultOrder.setMerchantId(merchant.getMerchantId());
		resultOrder.setMerchantName(merchant.getMerchantName());
		// 未发货，默认为0
		resultOrder.setStatus(0);
		double sum = 0;
		for(OrderFood of : orderFoodList){
			sum += of.getFoodSum();
		}
		resultOrder.setSum(sum);
		return resultOrder;
	}
	
	/**
	 * 生成订OrderFood
	 * @param food
	 * @param num
	 * @return
	 */
	public OrderFood createOrderFood(Food food, int num) {
		OrderFood orderFood = new OrderFood();
		orderFood.setFoodId(food.getFoodId());
		orderFood.setFoodName(food.getFoodName());
		orderFood.setFoodPrice(food.getFoodPrice());
		orderFood.setNum(num);
		orderFood.setFoodSum(food.getFoodPrice() * num);
		
		return orderFood;
	}
	
	
	
	
	
	
	/**
	 * 增加订单，成功返回 1
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int addOrder(Order order) throws SQLException {
		String sql = "insert into t_order values(null,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, order.getUserId());
		pstmt.setInt(2, order.getUserIdCard());
		pstmt.setInt(3, order.getMerchantId());
		pstmt.setString(4, order.getMerchantName());
		pstmt.setInt(5, order.getStatus());
		pstmt.setDouble(6, order.getSum());
		pstmt.setString(7, order.getWay());
		pstmt.setString(8, order.getAddress());

		// 时间转换
		Timestamp tsd = new Timestamp(order.getAddTime().getTime());
		pstmt.setTimestamp(9, tsd);
		if (order.getAckTime() != null) {
			Timestamp tsk = new Timestamp(order.getAckTime().getTime());
			pstmt.setTimestamp(10, tsk);
		}else{
			pstmt.setTimestamp(10, null);
		}

		int row = pstmt.executeUpdate();
		return row;
	}
	/**
	 * 给t_orderFood增加数据
	 * @param order
	 * @param food
	 * @param num
	 * @throws SQLException 
	 */
	public int addOrderFood(OrderFood orderFood, int orderId) throws SQLException {
		String sql = "insert into t_orderFood values(null,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, orderId);
		pstmt.setInt(2, orderFood.getFoodId());
		pstmt.setString(3, orderFood.getFoodName());
		pstmt.setDouble(4, orderFood.getFoodPrice());
		pstmt.setInt(5, orderFood.getNum());
		pstmt.setDouble(6, orderFood.getFoodSum());
		
		return pstmt.executeUpdate();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 带出orderId,失败返回0
	 * @param order
	 * @return
	 * @throws SQLException 
	 */
	public int selectOrderId(Order order) throws SQLException{
		String sql = "select orderId from t_order "
						  + "where userId = ? and merchantId = ? and status = ? and addTime = ? and sum = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, order.getUserId());
		pstmt.setInt(2, order.getMerchantId());
		pstmt.setInt(3, order.getStatus());
		pstmt.setTimestamp(4, new Timestamp(order.getAddTime().getTime()));
		pstmt.setDouble(5, order.getSum());
		ResultSet rs = pstmt.executeQuery();
		
		int orderId = 0;
		if(rs.next()){
			orderId = rs.getInt(1);
		}
		return orderId;
	}
	
	/**
	 * 查询订单，返回list。恩，管他发不发货，收不收货，我内心很没安全感。
	 *
	 * 但，好像商家和用户的代码是一样的，所以呢。。。直接用id做参数吗。。。。 true为用户，false为商家，日天啊。。。。
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public List<Order> selectOrderByStatus(int id, int status, boolean flag, int page) throws SQLException {
		List<Order> list = new ArrayList<Order>();
		String sql = null;
		
		// 我知道这样看上去不够geek，但你妈真心方便啊，ctrl c + ctrl v
		if (flag == true) {
			sql = "SELECT"
					+ " `o`.`orderId` AS `orderId`,"
					+ " `o`.`userId` AS `userId`,"
					+ " `o`.`userIdCard` AS `userIdCard`,"
					+ " `o`.`merchantId` AS `merchantId`,"
					+ " `o`.`merchantName` AS `merchantName`,"
					+ " `o`.`status` AS `status`,"
					+ " `o`.`sum` AS `sum`,"
					+ " `o`.`way` AS `way`,"
					+ " `o`.`address` AS `address`,"
					+ " `o`.`addTime` AS `addTime`,"
					+ " `o`.`ackTime` AS `ackTime`,"
					+ " `f`.`foodId` AS `foodId`,"
					+ " `f`.`foodName` AS `foodName`,"
					+ " `f`.`foodPrice` AS `foodPrice`,"
					+ " `f`.`num` AS `num`,"
					+ " `f`.`foodSum` AS `foodSum`"
					+ "  FROM"
					+ "  (`t_order` `o`   JOIN `t_orderfood` `f` ON ((`o`.`orderId` = `f`.`orderId`)))"
					+ "  WHERE   (`o`.`status` = ? and userId = ?) order by `o`.`addTime` desc limit ?,?";
		}else{
			sql = "SELECT"
					+ " `o`.`orderId` AS `orderId`,"
					+ " `o`.`userId` AS `userId`,"
					+ " `o`.`userIdCard` AS `userIdCard`,"
					+ " `o`.`merchantId` AS `merchantId`,"
					+ " `o`.`merchantName` AS `merchantName`,"
					+ " `o`.`status` AS `status`,"
					+ " `o`.`sum` AS `sum`,"
					+ " `o`.`way` AS `way`,"
					+ " `o`.`address` AS `address`,"
					+ " `o`.`addTime` AS `addTime`,"
					+ " `o`.`ackTime` AS `ackTime`,"
					+ " `f`.`foodId` AS `foodId`,"
					+ " `f`.`foodName` AS `foodName`,"
					+ " `f`.`foodPrice` AS `foodPrice`,"
					+ " `f`.`num` AS `num`,"
					+ " `f`.`foodSum` AS `foodSum`"
					+ "  FROM"
					+ "  (`t_order` `o`   JOIN `t_orderfood` `f` ON ((`o`.`orderId` = `f`.`orderId`)))"
					+ "  WHERE   (`o`.`status` = ? and merchantId = ?) order by `o`.`addTime` desc limit ?,?";
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, status);
		pstmt.setInt(2, id);
		pstmt.setInt(3, (page - 1) * 10);
		pstmt.setInt(4, (page - 1) * 10 + 10);

		ResultSet rs = pstmt.executeQuery();
		Order o = new Order();
		while (rs.next()) {

			OrderFood f = new OrderFood();
			f.setFoodId(rs.getInt("foodId"));
			f.setFoodName(rs.getString("foodName"));
			f.setFoodPrice(rs.getDouble("foodPrice"));
			f.setNum(rs.getInt("num"));
			f.setFoodSum(rs.getDouble("foodSum"));

			// 判断是否同一笔订单
			if (o.getOrderId() != rs.getInt(1)) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setUserId(rs.getInt("userId"));
				o.setUserIdCard(rs.getInt("userIdCard"));
				o.setMerchantId(rs.getInt("merchantId"));
				o.setMerchantName(rs.getString("merchantName"));
				o.setStatus(rs.getInt("status"));
				o.setSum(rs.getDouble("sum"));
				o.setWay(rs.getString("way"));
				o.setAddress(rs.getString("address"));
				if (rs.getTimestamp("addTime") != null) {
					java.util.Date addTime = new java.util.Date(rs.getTimestamp("addTime").getTime());
					o.setAddTime(addTime);
					o.setAddTime_();
				}
				if (rs.getTimestamp("ackTime") != null) {
					java.util.Date ackTime = new java.util.Date(rs.getTimestamp("ackTime").getTime());
					o.setAckTime(ackTime);
					o.setAckTime_();
				}

				o.getFoodList().add(f);
				// 添加
				list.add(o);
			} else {
				o.getFoodList().add(f);
			}
		}
		return list;
	}
	/**
	 * 根据id与状态查询总订单数
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException 
	 */
	public int selectOrderCounts(int id, int status, boolean flag) throws SQLException {
		String sql = "";
		if(flag == true){
			sql = "select count(*) from (`t_order` `o`   JOIN `t_orderfood` `f` ON ((`o`.`orderId` = `f`.`orderId`))) where `o`.`userId` = ? and `o`.`status` = ?";
		}else{
			sql = "select count(*) from (`t_order` `o`   JOIN `t_orderfood` `f` ON ((`o`.`orderId` = `f`.`orderId`))) where `o`.`merchantId` = ? and `o`.`status` = ?";
		}
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, 
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, id);
		pstmt.setInt(2, status);
		
		ResultSet rs = pstmt.executeQuery();
		
		int result = 0;
		if(rs.next()){
			result = rs.getInt(1);
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 发货与确认收货，成功返回 1
	 * 
	 * @param order
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public int updateOrder(int orderId, int status) throws SQLException {
		String sql = "update t_order set status = ?, ackTime = ? where orderId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, status);

		// 是否设置时间
		if (status == 2) {
			pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		} else {
			pstmt.setTimestamp(2, null);
		}
		pstmt.setInt(3, orderId);
		int row = pstmt.executeUpdate();
		return row;
	}
}
