package com.jojo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jojo.util.DbUtil;

/**
 * �����࣬����ʹ���߳����ӷ�Χ
 * @author flash.J
 *
 */
public class DaoFactory {

	private Connection conn = null;
	
	/**
	 * �������ӣ�ǧ��ǧ��Ҫ�رգ�
	 */
	public void beginConnectionScope(){
		conn = DbUtil.getConn();
	}
	/**
	 * �ر����ӣ�ǧ��ǧ��������
	 */
	public void endConnectionScope(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * �������񣬰��������ⶫ���е㷳����
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException{
		conn.setAutoCommit(false);
	}
	/**
	 * �ύ
	 * @throws SQLException
	 */
	public void endTransaction() throws SQLException{
		conn.commit();
		
		//��߼���������Ϊ��һ�λع���׼�����ò���û��ϵ������һ��ȫû�ˡ�
		conn.setAutoCommit(false); 
	}
	/**
	 * �ع�������������������õõ���
	 * �Լ������쳣��ʡ�ĵ�ʱ��Ƕ����Ƕ��ȥ�ֲ������
	 */
	public void abortTransaction(){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * UserDao��������
	 * @return
	 */
	public UserDao createUserDao(){
		return new UserDao(conn);
	}
	/**
	 * MerchantDao��������
	 * @return
	 */
	public MerchantDao createMerchantDao(){
		return new MerchantDao(conn);
	}
	/**
	 * FoodDao��������
	 * @return
	 */
	public FoodDao createFoodDao(){
		return new FoodDao(conn);
	}
	/**
	 * CartDao��������
	 * @return
	 */
	public CartDao createCartDao(){
		return new CartDao(conn);
	}
	/**
	 * OrderDao��������
	 * @return
	 */
	public OrderDao createOrderDao(){
		return new OrderDao(conn);
	}
}
