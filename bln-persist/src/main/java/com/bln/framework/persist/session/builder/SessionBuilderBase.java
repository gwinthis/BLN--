/**
 * @author gengw
 * Created on Apr 28, 2012
 */
package com.bln.framework.persist.session.builder;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.session.ISession;


/**
 * Session����������
 */
public abstract class SessionBuilderBase extends BaseObj implements ISessionBuilder, IConnectionBuilder{
	
	/**
	 * Sessionģ��
	 */
	protected ISession sessionTemplate = null;

	/**
	 * @param sessionTemplate the sessionTemplate to set
	 */
	public void setSessionTemplate(ISession sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
}
