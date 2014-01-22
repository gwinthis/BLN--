/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.mo.buildable.edi;

import com.bln.framework.mo.buildable.IBuildMOable;

/**
 * 可从EDI生成MO的接口
 */
public interface IBuildMOByEdiable extends IBuildMOable{
	
	/**
	 * 可从外部读取数据
	 * @param bytes
	 */
	public void readFromData(byte[] bytes)throws Throwable;
}
