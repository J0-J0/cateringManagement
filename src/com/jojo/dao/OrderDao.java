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
	 * ���캯������ȡ����
	 * 
	 * @param conn
	 */
	public OrderDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * ���Ӷ������ɹ����� 1
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int addOrder(Order order) throws SQLException {
		String sql = "insert into t_order values(null,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, order.getUserId());
		pstmt.setInt(2, order.getUserIdCard());
		pstmt.setInt(3, order.getMerchantId());
		pstmt.setString(4, order.getMerchantName());
		pstmt.setInt(5, order.getStatus());
		pstmt.setDouble(6, order.getSum());
		pstmt.setString(7, order.getAddress());

		// ʱ��ת��
		Timestamp tsd = new Timestamp(order.getAddTime().getTime());
		pstmt.setTimestamp(8, tsd);
		if (order.getAckTime() != null) {
			Timestamp tsk = new Timestamp(order.getAckTime().getTime());
			pstmt.setTimestamp(9, tsk);
		}else{
			pstmt.setTimestamp(9, null);
		}

		int row = pstmt.executeUpdate();
		return row;
	}
	/**
	 * ��t_orderFood��������
	 * @param order
	 * @param food
	 * @param num
	 * @throws SQLException 
	 */
	public int addOrderFood(Order order, Food food, int num) throws SQLException {
		int row = 0;
		String sql = "insert into t_orderFood values(null,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, order.getOrderId());
		pstmt.setInt(2, food.getFoodId());
		pstmt.setString(3, food.getFoodName());
		pstmt.setDouble(4, food.getFoodPrice());
		pstmt.setInt(5, num);
		pstmt.setDouble(6, food.getFoodPrice() * (double)num);
		
		row = pstmt.executeUpdate();
		return row;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ����orderId,ʧ�ܷ���0
	 * @param order
	 * @return
	 * @throws SQLException 
	 */
	public int selectOrderId(Order order) throws SQLException{
		String sql = "select orderId from t_order "
						  + "where userId = ? and merchantId = ? and status = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, order.getUserId());
		pstmt.setInt(2, order.getMerchantId());
		pstmt.setInt(3, order.getStatus());
		ResultSet rs = pstmt.executeQuery();
		
		int orderId = 0;
		if(rs.next()){
			orderId = rs.getInt(1);
		}
		return orderId;
	}
	
	/**
	 * ��ѯ����������list���������������������ղ��ջ��������ĺ�û��ȫ�С�
	 *
	 * ���������̼Һ��û��Ĵ�����һ���ģ������ء�����ֱ����id�������𡣡����� trueΪ�û���falseΪ�̼ң����찡��������
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public List<Order> selectOrderByStatus(int id, int status, boolean flag, int page) throws SQLException {
		List<Order> list = new ArrayList<Order>();
		String sql = null;
		
		// ��֪����������ȥ����geek�����������ķ��㰡��ctrl c + ctrl v
		if (flag == true) {
			sql = "SELECT"
								+ " `o`.`orderId` AS `orderId`,"
								+ " `o`.`userId` AS `userId`,"
								+ " `o`.`userIdCard` AS `userIdCard`,"
								+ " `o`.`merchantId` AS `merchantId`,"
								+ " `o`.`merchantName` AS `merchantName`,"
								+ " `o`.`status` AS `status`,"
								+ " `o`.`sum` AS `sum`,"
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
								+ "  WHERE   (`o`.`status` = ? and userId = ?) limit ?,?";
		}else{
			sql = "SELECT"
					+ " `o`.`orderId` AS `orderId`,"
					+ " `o`.`userId` AS `userId`,"
					+ " `o`.`userIdCard` AS `userIdCard`,"
					+ " `o`.`merchantId` AS `merchantId`,"
					+ " `o`.`merchantName` AS `merchantName`,"
					+ " `o`.`status` AS `status`,"
					+ " `o`.`sum` AS `sum`,"
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
					+ "  WHERE   (`o`.`status` = ? and merchantId = ?) limit ?,?";
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, status);
		pstmt.setInt(2, id);
		pstmt.setInt(3, (page - 1) * 10);
		pstmt.setInt(4, (page - 1) * 10 + 9);

		ResultSet rs = pstmt.executeQuery();
		Order o = new Order();
		while (rs.next()) {

			OrderFood f = new OrderFood();
			f.setFoodId(rs.getInt("foodId"));
			f.setFoodName(rs.getString("foodName"));
			f.setFoodPrice(rs.getDouble("foodPrice"));
			f.setNum(rs.getInt("num"));
			f.setFoodSum(rs.getDouble("foodSum"));

			// �ж��Ƿ�ͬһ�ʶ���
			if (o.getOrderId() != rs.getInt(1)) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setUserId(rs.getInt("userId"));
				o.setUserIdCard(rs.getInt("userIdCard"));
				o.setMerchantId(rs.getInt("merchantId"));
				o.setMerchantName(rs.getString("merchantName"));
				o.setStatus(rs.getInt("status"));
				o.setSum(rs.getDouble("sum"));
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
				// ����
				list.add(o);
			} else {
				o.getFoodList().add(f);
			}
		}
		return list;
	}

	/**
	 * ������ȷ���ջ����ɹ����� 1
	 * 
	 * @param order
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public int updateOrder(Order order, int status) throws SQLException {
		String sql = "update t_order set status = ?, ackTime = ? where orderId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, status);

		// �Ƿ�����ʱ��
		if (status == 2) {
			pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		} else {
			pstmt.setTimestamp(2, null);
		}
		pstmt.setInt(3, order.getOrderId());
		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * ����Order���������addOrder(), ��ʼ״̬�µ�order��û��������û��״̬... ������룬�����Ӿ����顣
	 * 
	 * @param food
	 * @param user
	 * @return
	 */
	public Order createOrder(Food food, User user, Merchant merchant, int num) {
		Order resultOrder = new Order();
		
		resultOrder.setUserId(user.getUserId());
		resultOrder.setUserIdCard(user.getUserIdCard());
		resultOrder.setMerchantId(merchant.getMerchantId());
		resultOrder.setMerchantName(merchant.getMerchantName());
		// δ������Ĭ��Ϊ0
		resultOrder.setStatus(0);
		resultOrder.setSum(food.getFoodPrice()*(double)num);
		resultOrder.setAddress(user.getAddress());
		// ����ʱ��
		java.util.Date addTime = new java.util.Date(System.currentTimeMillis());
		resultOrder.setAddTime(addTime);

		return resultOrder;
	}

	
}