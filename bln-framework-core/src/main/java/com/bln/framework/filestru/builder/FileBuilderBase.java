/**
 * @author gengw
 * craeted on 2012-03-27
 */
package com.bln.framework.filestru.builder;

import com.bln.framework.filestru.IFileBuildable;

/**
 * 文件生成器基类
 */
public abstract class FileBuilderBase implements IFileBuilder{
	
	/**
	 * 文件类型
	 */
	public IFileBuildable fileBuildable = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.builder.bymo.IFileBuildByMo#getFileType()
	 */
	public IFileBuildable getFileBuildable() {
		return fileBuildable;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.builder.bymo.IFileBuildByMo#setFileType(java.lang.String)
	 */
	public void setFileBuildable(IFileBuildable fileBuildable) {
		this.fileBuildable = fileBuildable;
	}

}
