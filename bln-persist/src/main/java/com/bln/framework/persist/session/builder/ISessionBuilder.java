package com.bln.framework.persist.session.builder;

import com.bln.framework.builder.IBuilder;
import com.bln.framework.persist.session.ISession;

public interface ISessionBuilder extends IBuilder<ISession>{

	/**
	 * ����Session
	 */
	public abstract ISession build();

}