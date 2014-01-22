/**
 * @author gengw
 * Created 2013-9-26
 */
package com.bln.framework.biz.service.facade.listener;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.util.listener.IEvtListener2Param;

/**
 * 服务之前的事件
 */
public interface IServiceExceptionEventListener extends IEvtListener2Param<IMessageObject, Throwable>{

}
