/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.facade.listener;

import com.bln.framework.app.context.AppContext;
import com.bln.framework.base.BaseObj;
import com.bln.framework.mo.IMessageObject;

/**
 * 触发服务之前处理上下文信息
 */
public class AddContextBeforeService extends BaseObj implements IServiceBeforeEventListener{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.util.listener.IEvtListener1Param#fire(java.lang.Object)
	 */
	public void fire(IMessageObject reqMo){
		AppContext.singleton().setValue("REQUESTMO", reqMo);
		AppContext.singleton().setValue("EXTHEADER", reqMo.getAllValOfExtHeader());
	}
}
