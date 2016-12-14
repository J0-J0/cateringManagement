package com.jojo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.jojo.model.User;

/**
 * ��User�����ݽ��в���
 * 
 * @author flash.J
 *
 */
public class UserDao {

	private Connection conn = null;

	/**
	 * ���캯����ȡ���ӣ����಻����ر�
	 * 
	 * @param conn
	 */
	public UserDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * �û�ע��
	 * 
	 * ��user�������һ�����ݡ��ɹ�����1
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int addUser(User user) throws SQLException {
		String sql = "insert into t_user values(null,?,?,?,?,?,?,?,?,?,?,?,null)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		// �����ھ���дjdbc������
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setInt(3, user.getUserIdCard());
		pstmt.setString(4, user.getUserRealName());
		
		//���sexΪ��������Ĭ���Ա�ΪŮ
		if(user.getSex() == null || user.getSex().length() == 0){
			user.setSex("Ů");
		}
		pstmt.setString(5, user.getSex());
		
		pstmt.setInt(6, user.getAge());
		pstmt.setString(7, user.getUserTel());
		pstmt.setString(8, user.getAddress());
		pstmt.setInt(9, user.getGroup());

		// �����ʱ�������
		user.setAddTime(new java.util.Date(System.currentTimeMillis()));
		Timestamp ts = new Timestamp(user.getAddTime().getTime());
		pstmt.setTimestamp(10, ts);

		pstmt.setDouble(11, user.getSum());

		return pstmt.executeUpdate();
	}

	/**
	 * ����userIdɾ���û����ɹ�����1
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public int deleteUser(int userId) throws SQLException {
		// ֻɾһ����ֱ�Ӽ��ں�ͷ��ʡ�ķ�
		String sql = "delete from t_user where userId = " + userId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * �����û����������ѯ�û����ɹ�����User���󣬲��ɹ�����null
	 * 
	 * @return
	 * @throws SQLException
	 */
	public User selectUser(String userName, String password) throws SQLException {
		User resultUser = null;

		// Ҫ��Ҫ��userName��password�������� ?
		String sql = "select * from t_user where userName = ? and password = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			resultUser = new User();

			// ������������д��
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

			// ��������
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
	 * ����userId��ѯuser
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
			// ��������
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
	 * ������
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
	 * ע��󷵻�userId,ʧ�ܷ���0
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
	 * ��ѯѧ��
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
	 * �����û����ɹ�����1
	 * 
	 * �����и����⣬�÷�������˭���õġ���Ϊ��Щ�������û����ɼ��ġ� ��ô������������û����ã��޸��û��ɼ����ԡ�
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
	 * �����û��飬�ɹ�����1
	 * 
	 * �÷������̼ҵ��á�
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
	 * �����û������ѣ������ջ�ʱ�Զ����ã��ɹ�����1
	 * 
	 * @param user
	 * @param sum
	 *            �ò����Ƕ������ܼ۸�
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
