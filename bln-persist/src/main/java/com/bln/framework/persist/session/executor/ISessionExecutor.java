package com.bln.framework.persist.session.executor;

import java.sql.Connection;

/**
 * ���ݿ�Ựִ�����Ľӿ�
 * @param <E> ʵ������
 */
public interface ISessionExecutor<E> {

	/**
	 * @param connection the conn to set
	 */
	public abstract void setConnection(Connection connection);
	


}