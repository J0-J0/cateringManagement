package com.jojo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jojo.model.Merchant;

/**
 * 没什么好写的
 * 
 * @author flash.J
 *
 */
public class MerchantDao {

	private Connection conn = null;

	/**
	 * 构造函数
	 * 
	 * @param conn
	 */
	public MerchantDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 小店老板注册，成功返回1
	 * 
	 * @param merchant
	 * @return
	 * @throws SQLException
	 */
	public int addMerchant(Merchant merchant) throws SQLException {
		String sql = "insert into t_merchant values(null,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		// 嘿嘿，可以复制粘贴
		pstmt.setString(1, merchant.getMerchantName());
		pstmt.setString(2, merchant.getPassword());
		pstmt.setInt(3, merchant.getMerchantIdCard());
		pstmt.setString(4, merchant.getMerchantRealName());

		// 如果sex为空，设置默认性别
		if (merchant.getSex() == null || merchant.getSex().length() == 0) {
			merchant.setSex("女");
		}
		pstmt.setString(5, merchant.getSex());
		pstmt.setInt(6, merchant.getAge());
		pstmt.setString(7, merchant.getMerchantTel());

		// 插入带时间的日期
		merchant.setAddTime(new java.util.Date(System.currentTimeMillis()));
		Timestamp ts = new Timestamp(merchant.getAddTime().getTime());
		pstmt.setTimestamp(8, ts);

		pstmt.setDouble(9, merchant.getSum());
		pstmt.setString(10, merchant.getPic());
		pstmt.setString(11, merchant.getDescription());

		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * 小店老板注销，成功返回1
	 * 
	 * @param merchantId
	 * @return
	 * @throws SQLException
	 */
	public int deleteMerchant(int merchantId) throws SQLException {
		// 只删一个就直接加在后头，省的烦
		String sql = "delete from t_merchant where merchantId = " + merchantId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * 小店老板登录，根据给定的用户民与密码进行查询 成功返回完整信息的Merchant，不成功返回null
	 * 
	 * @param merchantName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Merchant selectMerchant(String merchantName, String password) throws SQLException {
		Merchant resultMerchant = null;

		String sql = "select * from t_merchant where merchantName = ? and password = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);   // 这意思是我就不需要显示关结果集了嚯

		pstmt.setString(1, merchantName);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			resultMerchant = new Merchant();

			// 复制粘贴虽好，眼睛得擦亮点
			resultMerchant.setMerchantId(rs.getInt("merchantId"));
			resultMerchant.setMerchantName(rs.getString("merchantName"));
			resultMerchant.setPassword(rs.getString("password"));
			resultMerchant.setMerchantIdCard(rs.getInt("merchantIdCard"));
			resultMerchant.setMerchantRealName(rs.getString("merchantRealName"));
			resultMerchant.setSex(rs.getString("sex"));
			resultMerchant.setAge(rs.getInt("age"));
			resultMerchant.setMerchantTel(rs.getString("merchantTel"));

			// 设置日期
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				resultMerchant.setAddTime(date);
				resultMerchant.setAddTime_();
			}

			resultMerchant.setSum(rs.getDouble("sum"));
			
			// 设置描述
			String description = rs.getString("description");
			if(description == null || "".equals(description))
				description = "这家伙很懒，什么东西都没留下。";
			resultMerchant.setDescription(description);
		}

		return resultMerchant;
	}
	
	/**
	 * 根据merchantId查询merchant
	 * @param merchantId
	 * @return
	 * @throws SQLException 
	 */
	public Merchant selectMerchant(int merchantId) throws SQLException{
		Merchant resultMerchant = null;
		
		String sql = "select * from t_merchant where merchantId = " + merchantId;
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			resultMerchant = new Merchant();
			resultMerchant.setMerchantId(rs.getInt("merchantId"));
			resultMerchant.setMerchantName(rs.getString("merchantName"));
			resultMerchant.setPassword(rs.getString("password"));
			resultMerchant.setMerchantIdCard(rs.getInt("merchantIdCard"));
			resultMerchant.setMerchantRealName(rs.getString("merchantRealName"));
			resultMerchant.setSex(rs.getString("sex"));
			resultMerchant.setAge(rs.getInt("age"));
			resultMerchant.setMerchantTel(rs.getString("merchantTel"));
			// 设置日期
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				resultMerchant.setAddTime(date);
				resultMerchant.setAddTime_();
			}
			resultMerchant.setSum(rs.getDouble("sum"));
			// 设置描述
			String description = rs.getString("description");
			if(description == null || "".equals(description))
				description = "这家伙很懒，什么东西都没留下。";
			resultMerchant.setDescription(description);
		}
		return resultMerchant;
	}
	
	/**
	 * 批量查询所有商家，返回list，肯定不为null所以要判断长度
	 * @return
	 * @throws SQLException
	 */
	public List<Merchant> selectAllMerchants() throws SQLException{
		List<Merchant> list = new ArrayList<Merchant>();
		
		// 我很担忧这个性能
		String sql = "select * from t_merchant";
		
		// 写着三个属性也有装逼的含义在里面
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, 
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		
		ResultSet rs = pstmt.executeQuery();
		Merchant m = null;
		while(rs.next()){
			m = new Merchant();
			
			m.setMerchantId(rs.getInt("merchantId"));
			m.setMerchantName(rs.getString("merchantName"));
			m.setPassword(rs.getString("password"));
			m.setMerchantIdCard(rs.getInt("merchantIdCard"));
			m.setMerchantRealName(rs.getString("merchantRealName"));
			m.setSex(rs.getString("sex"));
			m.setAge(rs.getInt("age"));
			m.setMerchantTel(rs.getString("merchantTel"));

			// 设置日期
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				m.setAddTime(date);
				m.setAddTime_();
			}

			m.setSum(rs.getDouble("sum"));
			
			// 设置描述，万一没写就搞默认
			String description = rs.getString("description");
			if(description == null || "".equals(description))
				description = "这家伙很懒，什么东西都没留下。";
			m.setDescription(description);
			
			list.add(m);
		}
		return list;
	}
	/**
	 * 查询t_merchant表中的记录数
	 * @return
	 * @throws SQLException 
	 */
	public int selectMerchantNums() throws SQLException{
		String sql = "select count(*) from t_merchant";
		// 这三个参数写了感觉就是不一样
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, 
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		// 返回的变量
		int count = 0;
		if(rs.next()){
			count = rs.getInt(1);
		}
		return count;
	}
	/**
	 * 查询商家名称
	 * @param merchantId
	 * @return
	 * @throws SQLException
	 */
	public String selectMerchantName(int merchantId) throws SQLException{
		String sql = "select merchantName from t_merchant where merchantId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, 
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		pstmt.setInt(1, merchantId);
		
		ResultSet rs = pstmt.executeQuery();
		
		String merchantName = null;
		if(rs.next()){
			merchantName = rs.getString(1);
		}
		return merchantName;
	}
	
	
	
	
	
	
	
	
	/**
	 * 小店老板更新信息，成功返回1，同样，只有看的见得信息可以进行更改
	 * 
	 * @param merchant
	 * @return
	 * @throws SQLException
	 */
	public int updateMerchant(Merchant merchant) throws SQLException {
		String sql = "update t_merchant set merchantName = ?, password = ?, merchantIdCard = ?, "
				+ "merchantRealName = ?, sex = ?, age = ?, merchantTel = ?" + "where merchantId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, merchant.getMerchantName());
		pstmt.setString(2, merchant.getPassword());
		pstmt.setInt(3, merchant.getMerchantIdCard());
		pstmt.setString(4, merchant.getMerchantRealName());
		pstmt.setString(5, merchant.getSex());
		pstmt.setInt(6, merchant.getAge());
		pstmt.setString(7, merchant.getMerchantTel());
		pstmt.setInt(8, merchant.getMerchantId());

		int row = pstmt.executeUpdate();
		return row;
	}
	
	
	/**
	 * 更新总收入
	 * @param merchant
	 * @return
	 * @throws SQLException
	 */
	public int updateMerchant(int merchantId, double sum) throws SQLException {
		String sql = "update t_merchant set sum=sum+?  where merchantId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setDouble(1, sum);
		pstmt.setInt(2, merchantId);

		return pstmt.executeUpdate();
	}
	
	
	
	
	/**
	 * 更新头像，成功返回1
	 * 
	 * 唉，先写着，管他用不用，搞不好还能模块化. 用Merchant对象是因为，当你准备更新自己的头像时，你肯定已经登录了，那样直接传
	 * 对象就显得方便。而登录查找用户名什么的，人都不在数据库里，传对象的话还要创建 在封装，扯淡！
	 * 
	 * @param merchant
	 * @return
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	public int updateMerchantPic(Merchant merchant) throws FileNotFoundException, SQLException {
		String sql = "update t_merchant set pic = ? where merchantId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		FileInputStream in = new FileInputStream(merchant.getPic());
		pstmt.setBinaryStream(1, in, merchant.getPic().length());
		pstmt.setInt(2, merchant.getMerchantId());

		int row = pstmt.executeUpdate();
		return row;
	}
}
