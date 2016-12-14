package com.jojo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.jojo.model.User;

/**
 * 对User类数据进行操作
 * 
 * @author flash.J
 *
 */
public class UserDao {

	private Connection conn = null;

	/**
	 * 构造函数获取连接，该类不负责关闭
	 * 
	 * @param conn
	 */
	public UserDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 用户注册
	 * 
	 * 往user表中添加一行数据。成功返回1
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int addUser(User user) throws SQLException {
		String sql = "insert into t_user values(null,?,?,?,?,?,?,?,?,?,?,?,null)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		// 我现在觉得写jdbc恶心了
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setInt(3, user.getUserIdCard());
		pstmt.setString(4, user.getUserRealName());
		
		//如果sex为空则设置默认性别为女
		if(user.getSex() == null || user.getSex().length() == 0){
			user.setSex("女");
		}
		pstmt.setString(5, user.getSex());
		
		pstmt.setInt(6, user.getAge());
		pstmt.setString(7, user.getUserTel());
		pstmt.setString(8, user.getAddress());
		pstmt.setInt(9, user.getGroup());

		// 插入带时间的日期
		user.setAddTime(new java.util.Date(System.currentTimeMillis()));
		Timestamp ts = new Timestamp(user.getAddTime().getTime());
		pstmt.setTimestamp(10, ts);

		pstmt.setDouble(11, user.getSum());

		return pstmt.executeUpdate();
	}

	/**
	 * 根据userId删除用户，成功返回1
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public int deleteUser(int userId) throws SQLException {
		// 只删一个就直接加在后头，省的烦
		String sql = "delete from t_user where userId = " + userId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * 根据用户名与密码查询用户，成功返回User对象，不成功返回null
	 * 
	 * @return
	 * @throws SQLException
	 */
	public User selectUser(String userName, String password) throws SQLException {
		User resultUser = null;

		// 要不要给userName和password建个索引 ?
		String sql = "select * from t_user where userName = ? and password = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			resultUser = new User();

			// 。。。不是人写的
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setUserIdCard(rs.getInt("userIdCard"));
			resultUser.setUserRealName(rs.getString("userRealName"));
			resultUser.setSex(rs.getString("sex"));
			resultUser.setAge(rs.getInt("age"));
			resultUser.setUserTel(rs.getString("userTel"));
			resultUser.setAddress(rs.getString("address"));
			resultUser.setGroup(rs.getInt("group"));

			// 设置日期
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				resultUser.setAddTime(date);
				resultUser.setAddTime_();
			}

			resultUser.setSum(rs.getDouble("sum"));
		}

		return resultUser;
	}
	
	/**
	 * 根据userId查询user
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public User selectUser(int userId) throws SQLException{
		User resultUser = null;
		
		String sql = "select * from t_user where userId = "+ userId;
		PreparedStatement pstmt = conn.prepareStatement(sql,
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()){
			resultUser = new User();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setUserIdCard(rs.getInt("userIdCard"));
			resultUser.setUserRealName(rs.getString("userRealName"));
			resultUser.setSex(rs.getString("sex"));
			resultUser.setAge(rs.getInt("age"));
			resultUser.setUserTel(rs.getString("userTel"));
			resultUser.setAddress(rs.getString("address"));
			resultUser.setGroup(rs.getInt("group"));
			// 设置日期
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				resultUser.setAddTime(date);
				resultUser.setAddTime_();
			}
			resultUser.setSum(rs.getDouble("sum"));
		}
		
		return resultUser;
	}
	/**
	 * 查名字
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public String selectUserName(int userId) throws SQLException {
		String sql = "select userName from t_user where userId="+userId;
		PreparedStatement pstmt = conn.prepareStatement(sql,
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		String userName = null;
		if(rs.next()){
			userName = rs.getString(1);
		}
		return userName;
	}
	
	/**
	 * 注册后返回userId,失败返回0
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public int selectUserId(User user) throws SQLException{
		String sql = "select userId from t_user where userName = ? and password = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql,
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		
		ResultSet rs = pstmt.executeQuery();
		
		int userId = 0;
		if(rs.next()){
			userId = rs.getInt("userId");
		}
		return userId;
	}
	/**
	 * 查询学号
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public int selectUserIdCard(int userId) throws SQLException{
		String sql = "select userIdCard from t_user where userId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, 
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, userId);
		
		ResultSet rs = pstmt.executeQuery();
		
		int userIdCard = 0;
		if(rs.next()){
			userIdCard = rs.getInt(1);
		}
		return userIdCard;
	}
	
	
	
	
	
	
	
	
	/**
	 * 更新用户，成功返回1
	 * 
	 * 这里有个问题，该方法是由谁调用的。因为有些参数是用户不可见的。 那么，这个方法由用户调用，修改用户可见属性。
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int updateUser(User user) throws SQLException {
		String sql = "update t_user set userName = ?, password = ?, userIdCard = ?, "
				+ "userRealName = ?, sex = ?, age = ?, userTel = ?, address = ? " + "where userId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setInt(3, user.getUserIdCard());
		pstmt.setString(4, user.getUserRealName());
		pstmt.setString(5, user.getSex());
		pstmt.setInt(6, user.getAge());
		pstmt.setString(7, user.getUserTel());
		pstmt.setString(8, user.getAddress());
		pstmt.setInt(9, user.getUserId());

		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * 更新用户组，成功返回1
	 * 
	 * 该方法由商家调用。
	 * 
	 * @param group
	 * @return
	 * @throws SQLException
	 */
	public int updateUser(User user, int group) throws SQLException {
		String sql = "update t_user set group = ? where userId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, group);
		pstmt.setInt(2, user.getUserId());

		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * 更新用户总消费，订单收货时自动调用，成功返回1
	 * 
	 * @param user
	 * @param sum
	 *            该参数是订单的总价格
	 * @return
	 * @throws SQLException
	 */
	public int updateUser(int userId, double sum) throws SQLException {
		String sql = "update t_user set sum=sum+?  where userId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setDouble(1, sum);
		pstmt.setInt(2, userId);

		return pstmt.executeUpdate();
	}
}
