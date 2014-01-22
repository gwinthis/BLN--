/**
 * @author gengw
 * created on 2012-03-12
 */
package com.bln.framework.mo.builder;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.IBuildMOable;

/**
 * MO�������ӿ�
 */
public interface IMoBuilder <M extends IMessageObject, B extends IBuildMOable> {

	/**
	 * ���ֽ����鹹��ɳ���Ϣ����
	 * @param bytes ���յ������ݣ����ֽ�����洢
	 * @return �����ֽ����������ɵ���Ϣ����
	 * @throws Throwable �����������׳����쳣
	 */
	public IMessageObject buildMo(byte[] bytes) throws Throwable;

	/**
	 * ��������ɵ�MO
	 * @return
	 */
	public M getMo();

	/**
	 * ����Ҫ���ɵ�MO
	 * @param mo
	 */
	public void setMo(M mo);

	/**
	 * ��ȡ������MO�Ķ���
	 * @return
	 */
	public B getBuildMOable();

	/**
	 * ���ÿɴ�EDI����MO�Ķ���
	 * @param buildMoAble
	 */
	public void setBuildMOable(B buildMOable);
}
