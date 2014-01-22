/**
 * @author gengw
 * created on 2012-03-23
 */
package com.bln.framework.filestru;

/**
 * �������ļ��ӿ�
 *
 */
public interface IFileBuildable {

	/**
	 * ������ֽ�������
	 * @return �ֽ�����
	 * @throws Throwable
	 */
	public abstract byte[] writeToBytes() throws Throwable;

	/**
	 * ��ʼ���ļ�����
	 */
	public abstract void initFile() throws Throwable;
	

}