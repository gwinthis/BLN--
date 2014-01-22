/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender.decorator.async.param;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.util.callback.CallbackParam;

/**
 * 网络请求失败时所调用的参数
 */
public class OnSuccessParam extends CallbackParam{

	/**
	 * 响应信息
	 */
	protected IMessageObject respData = null;

	/**
	 * 获得响应信息
	 * @return
	 */
	public IMessageObject getRespData() {
		return respData;
	}

	/**
	 * 设置相应信息
	 * @param respData
	 */
	public void setRespData(IMessageObject respData) {
		this.respData = respData;
	}
}
