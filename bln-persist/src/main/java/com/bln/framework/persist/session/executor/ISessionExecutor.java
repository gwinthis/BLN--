package com.bln.framework.persist.session.executor;

import java.sql.Connection;

/**
 * 数据库会话执行器的接口
 * @param <E> 实体类型
 */
public interface ISessionExecutor<E> {

	/**
	 * @param connection the conn to set
	 */
	public abstract void setConnection(Connection connection);
	


}