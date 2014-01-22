/**
 * @author gengw
 * created on 2012-03-23
 */
package com.bln.framework.filestru.builder;

import com.bln.framework.filestru.IFileBuildable;

/**
 * 文件生成接口
 *
 */
public interface IFileBuilder {
	
	/**
	 * 获得文件类型
	 * @return 文件类型
	 */
	public IFileBuildable getFileBuildable();

	/**
	 * 需要生成的文件类型
	 * @param fileBuildableType 文件类型
	 */
	public void setFileBuildable(IFileBuildable fileBuildable);
	
}