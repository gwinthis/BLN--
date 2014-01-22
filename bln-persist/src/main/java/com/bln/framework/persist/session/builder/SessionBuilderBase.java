/**
 * @author gengw
 * Created on Apr 28, 2012
 */
package com.bln.framework.persist.session.builder;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.session.ISession;


/**
 * Session生成器基类
 */
public abstract class SessionBuilderBase extends BaseObj implements ISessionBuilder, IConnectionBuilder{
	
	/**
	 * Session模板
	 */
	protected ISession sessionTemplate = null;

	/**
	 * @param sessionTemplate the sessionTemplate to set
	 */
	public void setSessionTemplate(ISession sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
}
