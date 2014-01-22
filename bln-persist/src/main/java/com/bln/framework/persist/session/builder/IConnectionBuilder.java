/**
 * @author gengw
 * Created on Jan 24, 2013
 */
package com.bln.framework.persist.session.builder;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ���ݿ����ӹ�����
 */
public interface IConnectionBuilder {
	
	/**
	 * ������ݿ�����
	 * @return Connection
	 * @throws SQLException 
	 */
	public Connection buildConnection() throws SQLException;

}
