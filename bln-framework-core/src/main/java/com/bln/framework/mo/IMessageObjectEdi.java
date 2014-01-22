/**
 * @author gengw
 * Craeted at 2012-03-27
 */
package com.bln.framework.mo;

import com.bln.framework.filestru.IFileStru;

/**
 * <p>ʹ��EDI�洢����Ϣ����ӿ�</p>
 * <p>ֱ�ӿ��Զ�ȡEDI���ĵ����ݣ�Ҳ����ֱ������EDI���ĵ�����</p>
 * <p>����TΪEDI�ļ����������</p>
 */
public interface IMessageObjectEdi <T extends IFileStru> extends IMessageObject{

	/**
	 * �ɴ��ⲿ��ȡ����
	 * @param bytes
	 */
	public void readFromData(byte[] bytes)throws Throwable;
	
	/**
	 * ��ʼ���ļ�����
	 */
	public abstract void initFile() throws Throwable;

	/**
	 * @return the T
	 */
	public T getEdiFileStru();

	/**
	 * @param T the T to set
	 */
	public void setEdiFileStru(T ediFileStru);
	
}
