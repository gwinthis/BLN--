/**
 * @author gengw
 * Created at 2013-08-04
 */
package com.bln.framework.edi.edge.rcver.listener;

/**
 * EDI接受者的监听器
 */
public interface IRcverListener {

	/**
	 * 开始请求
	 * @param reqEdiData 请求数据
	 * @param originalRequestObject 请求原始对象
	 */
	public void startRequest(byte[] reqEdiData, Object originalRequestObject);

	/**
	 * 结束请求
	 * @param reqEdiData  请求数据
	 * @param respData 响应数据
	 * @param originalRequestObject 请求原始对象
	 */
	public void finishRequest(byte[] reqEdiData, byte[] respData, Object originalRequestObject);

}
