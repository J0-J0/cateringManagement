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
 * ûʲô��д��
 * 
 * @author flash.J
 *
 */
public class MerchantDao {

	private Connection conn = null;

	/**
	 * ���캯��
	 * 
	 * @param conn
	 */
	public MerchantDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * С���ϰ�ע�ᣬ�ɹ�����1
	 * 
	 * @param merchant
	 * @return
	 * @throws SQLException
	 */
	public int addMerchant(Merchant merchant) throws SQLException {
		String sql = "insert into t_merchant values(null,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		// �ٺ٣����Ը���ճ��
		pstmt.setString(1, merchant.getMerchantName());
		pstmt.setString(2, merchant.getPassword());
		pstmt.setInt(3, merchant.getMerchantIdCard());
		pstmt.setString(4, merchant.getMerchantRealName());

		// ���sexΪ�գ�����Ĭ���Ա�
		if (merchant.getSex() == null || merchant.getSex().length() == 0) {
			merchant.setSex("Ů");
		}
		pstmt.setString(5, merchant.getSex());
		pstmt.setInt(6, merchant.getAge());
		pstmt.setString(7, merchant.getMerchantTel());

		// �����ʱ�������
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
	 * С���ϰ�ע�����ɹ�����1
	 * 
	 * @param merchantId
	 * @return
	 * @throws SQLException
	 */
	public int deleteMerchant(int merchantId) throws SQLException {
		// ֻɾһ����ֱ�Ӽ��ں�ͷ��ʡ�ķ�
		String sql = "delete from t_merchant where merchantId = " + merchantId;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int row = pstmt.executeUpdate();
		return row;
	}

	/**
	 * С���ϰ��¼�����ݸ������û�����������в�ѯ �ɹ�����������Ϣ��Merchant�����ɹ�����null
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
				ResultSet.CLOSE_CURSORS_AT_COMMIT);   // ����˼���ҾͲ���Ҫ��ʾ�ؽ��������

		pstmt.setString(1, merchantName);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			resultMerchant = new Merchant();

			// ����ճ����ã��۾��ò�����
			resultMerchant.setMerchantId(rs.getInt("merchantId"));
			resultMerchant.setMerchantName(rs.getString("merchantName"));
			resultMerchant.setPassword(rs.getString("password"));
			resultMerchant.setMerchantIdCard(rs.getInt("merchantIdCard"));
			resultMerchant.setMerchantRealName(rs.getString("merchantRealName"));
			resultMerchant.setSex(rs.getString("sex"));
			resultMerchant.setAge(rs.getInt("age"));
			resultMerchant.setMerchantTel(rs.getString("merchantTel"));

			// ��������
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				resultMerchant.setAddTime(date);
				resultMerchant.setAddTime_();
			}

			resultMerchant.setSum(rs.getDouble("sum"));
			
			// ��������
			String description = rs.getString("description");
			if(description == null || "".equals(description))
				description = "��һ������ʲô������û���¡�";
			resultMerchant.setDescription(description);
		}

		return resultMerchant;
	}
	
	/**
	 * ����merchantId��ѯmerchant
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
			// ��������
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				resultMerchant.setAddTime(date);
				resultMerchant.setAddTime_();
			}
			resultMerchant.setSum(rs.getDouble("sum"));
			// ��������
			String description = rs.getString("description");
			if(description == null || "".equals(description))
				description = "��һ������ʲô������û���¡�";
			resultMerchant.setDescription(description);
		}
		return resultMerchant;
	}
	
	/**
	 * ������ѯ�����̼ң�����list���϶���Ϊnull����Ҫ�жϳ���
	 * @return
	 * @throws SQLException
	 */
	public List<Merchant> selectAllMerchants() throws SQLException{
		List<Merchant> list = new ArrayList<Merchant>();
		
		// �Һܵ����������
		String sql = "select * from t_merchant";
		
		// д����������Ҳ��װ�Ƶĺ���������
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

			// ��������
			Timestamp ts = rs.getTimestamp("addTime");
			if (ts != null) {
				java.util.Date date = new java.util.Date(ts.getTime());
				m.setAddTime(date);
				m.setAddTime_();
			}

			m.setSum(rs.getDouble("sum"));
			
			// ������������һûд�͸�Ĭ��
			String description = rs.getString("description");
			if(description == null || "".equals(description))
				description = "��һ������ʲô������û���¡�";
			m.setDescription(description);
			
			list.add(m);
		}
		return list;
	}
	/**
	 * ��ѯt_merchant���еļ�¼��
	 * @return
	 * @throws SQLException 
	 */
	public int selectMerchantNums() throws SQLException{
		String sql = "select count(*) from t_merchant";
		// ����������д�˸о����ǲ�һ��
		PreparedStatement pstmt = conn.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, 
				ResultSet.CLOSE_CURSORS_AT_COMMIT);
		ResultSet rs = pstmt.executeQuery();
		
		// ���صı���
		int count = 0;
		if(rs.next()){
			count = rs.getInt(1);
		}
		return count;
	}
	/**
	 * ��ѯ�̼�����
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
	 * С���ϰ������Ϣ���ɹ�����1��ͬ����ֻ�п��ļ�����Ϣ���Խ��и���
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
	 * ����������
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
	 * ����ͷ�񣬳ɹ�����1
	 * 
	 * ������д�ţ������ò��ã��㲻�û���ģ�黯. ��Merchant��������Ϊ������׼�������Լ���ͷ��ʱ����϶��Ѿ���¼�ˣ�����ֱ�Ӵ�
	 * ������Ե÷��㡣����¼�����û���ʲô�ģ��˶��������ݿ��������Ļ���Ҫ���� �ڷ�װ��������
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
