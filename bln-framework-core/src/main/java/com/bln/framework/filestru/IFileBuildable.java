/**
 * @author gengw
 * created on 2012-03-23
 */
package com.bln.framework.filestru;

/**
 * 可生成文件接口
 *
 */
public interface IFileBuildable {

	/**
	 * 输出到字节数组中
	 * @return 字节数组
	 * @throws Throwable
	 */
	public abstract byte[] writeToBytes() throws Throwable;

	/**
	 * 初始化文件对象
	 */
	public abstract void initFile() throws Throwable;
	

}