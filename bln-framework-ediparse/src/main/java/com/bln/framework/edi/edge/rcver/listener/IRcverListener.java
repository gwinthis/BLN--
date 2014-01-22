/**
 * @author gengw
 * Created at 2013-08-04
 */
package com.bln.framework.edi.edge.rcver.listener;

/**
 * EDI�����ߵļ�����
 */
public interface IRcverListener {

	/**
	 * ��ʼ����
	 * @param reqEdiData ��������
	 * @param originalRequestObject ����ԭʼ����
	 */
	public void startRequest(byte[] reqEdiData, Object originalRequestObject);

	/**
	 * ��������
	 * @param reqEdiData  ��������
	 * @param respData ��Ӧ����
	 * @param originalRequestObject ����ԭʼ����
	 */
	public void finishRequest(byte[] reqEdiData, byte[] respData, Object originalRequestObject);

}
