/**
 * @author gengw
 * Created on Jan 24, 2013
 */
package com.bln.framework.persist.session.builder;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接构造器
 */
public interface IConnectionBuilder {
	
	/**
	 * 获得数据库连接
	 * @return Connection
	 * @throws SQLException 
	 */
	public Connection buildConnection() throws SQLException;

}
