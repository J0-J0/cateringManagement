package com.jojo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ���ݿ⹤���࣬������ֻ��д����ȡ����
 * @author flash.J
 *
 */
public class DbUtil {

	/**
	 * ��ȡ���ݿ����ӣ���ȡ��������null
	 * db_cateringManagerment
	 * @return
	 */
	public static Connection getConn(){
		
		String user = "root";
		String password = "123456";
		String url = "jdbc:mysql://127.0.0.1:3306/db_cateringManagement";
		String driverName = "com.mysql.jdbc.Driver";
		
		Connection conn = null;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/*public static void main(String[] args) {
		Connection conn = DbUtil.getConn();
		if(conn != null){
			System.out.println("success !");
		}else{
			System.out.println("fail !");
		}
	}*/
}
