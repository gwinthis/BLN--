/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.mo.buildable.edi;

import com.bln.framework.mo.buildable.IBuildMOable;

/**
 * �ɴ�EDI����MO�Ľӿ�
 */
public interface IBuildMOByEdiable extends IBuildMOable{
	
	/**
	 * �ɴ��ⲿ��ȡ����
	 * @param bytes
	 */
	public void readFromData(byte[] bytes)throws Throwable;
}
