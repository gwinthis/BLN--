/**
 * @author gengw
 * Craeted at 2012-03-27
 */
package com.bln.framework.mo;

import com.bln.framework.filestru.IFileStru;

/**
 * <p>使用EDI存储的消息对象接口</p>
 * <p>直接可以读取EDI报文的数据，也可以直接生成EDI报文的数据</p>
 * <p>泛型T为EDI文件对象的类型</p>
 */
public interface IMessageObjectEdi <T extends IFileStru> extends IMessageObject{

	/**
	 * 可从外部读取数据
	 * @param bytes
	 */
	public void readFromData(byte[] bytes)throws Throwable;
	
	/**
	 * 初始化文件对象
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
