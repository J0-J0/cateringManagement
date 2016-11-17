package com.jojo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jojo.util.DbUtil;

/**
 * 工厂类，本次使用线程连接范围
 * @author flash.J
 *
 */
public class DaoFactory {

	private Connection conn = null;
	
	/**
	 * 开启连接，千万千万要关闭！
	 */
	public void beginConnectionScope(){
		conn = DbUtil.getConn();
	}
	/**
	 * 关闭连接，千万千万不能忘！
	 */
	public void endConnectionScope(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 开启事务，唉，事务，这东西有点烦啊。
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException{
		conn.setAutoCommit(false);
	}
	/**
	 * 提交
	 * @throws SQLException
	 */
	public void endTransaction() throws SQLException{
		conn.commit();
		
		//这边继续开事务，为下一次回滚做准备。用不到没关系，连接一关全没了。
		conn.setAutoCommit(false); 
	}
	/**
	 * 回滚，这个事务机制我真的用得到吗？
	 * 自己处理异常，省的到时候嵌套来嵌套去分不清楚。
	 */
	public void abortTransaction(){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * UserDao工厂方法
	 * @return
	 */
	public UserDao createUserDao(){
		return new UserDao(conn);
	}
	/**
	 * MerchantDao工厂方法
	 * @return
	 */
	public MerchantDao createMerchantDao(){
		return new MerchantDao(conn);
	}
	/**
	 * FoodDao工厂方法
	 * @return
	 */
	public FoodDao createFoodDao(){
		return new FoodDao(conn);
	}
	/**
	 * CartDao工厂方法
	 * @return
	 */
	public CartDao createCartDao(){
		return new CartDao(conn);
	}
	/**
	 * OrderDao工厂方法
	 * @return
	 */
	public OrderDao createOrderDao(){
		return new OrderDao(conn);
	}
}
