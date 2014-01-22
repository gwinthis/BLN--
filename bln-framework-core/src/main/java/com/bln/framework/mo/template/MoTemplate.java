/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.mo.template;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.bln.framework.config.IAppConfig;
import com.bln.framework.mo.IMessageObject;

/**
 * 消息对象模板
 */
public class MoTemplate implements IMoTemplate {
	
	/**
	 * 在MO结束时是否需要删除请求节点
	 */
	protected boolean removeRequestWhenEnd = true;

	/**
	 * 应用对象
	 */
	protected IAppConfig appConfig = null;
	
	/**
	 * 请求成功的值
	 */
	protected String successValue = "00";
	
	/**
	 * 请求失败的值
	 */
	protected String failedValue = "30";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.template.IMoTemplate#requestEnd(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject requestEnd(IMessageObject mo){
		
		//1.清除请求信息
		if(this.isRemoveRequestWhenEnd()){
			mo.clearRequest();
		}

		//2.设置响应时间
		String respDate = DateFormatUtils.format(new Date(), appConfig.getDatetimeFormat());
		mo.setResponseDate(respDate);
		
		//3.返回消息对象
		return mo;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.template.IMoTemplate#serviceSuccessfulEnd(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject serviceSuccessfulEnd(IMessageObject mo){
		
		//返回码为成功
		mo.setReturnCode(successValue);
		
		return mo;
	}
	
	/**
	 * 服务失败结束 
	 * @param mo 要处理的消息对象
	 * @param errorCode 错误码
	 * @return 处理完的消息对象
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode){
		
		return serviceFailedEnd(mo, errorCode, null);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.template.IMoTemplate#serviceFailedEnd(com.bln.framework.mo.IMessageObject, java.lang.String, java.lang.String)
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode, String respDesc){
		
		//返回码为失败
		mo.setReturnCode(failedValue);
		
		//设置错误码
		mo.setErrorCode(errorCode);
		
		//设置错误描述信息
		if(!StringUtils.isEmpty(respDesc)){
			mo.setErrorDesc(respDesc);
		}
		
		//返回处理完的消息对象
		return mo;
	}

	/**
	 * @return
	 */
	public boolean isRemoveRequestWhenEnd() {
		return removeRequestWhenEnd;
	}

	/**
	 * @param removeRequestWhenEnd the removeRequestWhenEnd to set
	 */
	public void setRemoveRequestWhenEnd(boolean removeRequestWhenEnd) {
		this.removeRequestWhenEnd = removeRequestWhenEnd;
	}
	
	/**
	 * @return the successValue
	 */
	public String getSuccessValue() {
		return successValue;
	}

	/**
	 * @param successValue the successValue to set
	 */
	public void setSuccessValue(String successValue) {
		this.successValue = successValue;
	}

	/**
	 * @return the failedValue
	 */
	public String getFailedValue() {
		return failedValue;
	}

	/**
	 * @param failedValue the failedValue to set
	 */
	public void setFailedValue(String failedValue) {
		this.failedValue = failedValue;
	}

	/**
	 * @return the appConfig
	 */
	public IAppConfig getAppConfig() {
		return appConfig;
	}

	/**
	 * @param appConfig the appConfig to set
	 */
	public void setAppConfig(IAppConfig appConfig) {
		this.appConfig = appConfig;
	}


}
