/**
 * @author gengw
 * craeted on 2012-03-27
 */
package com.bln.framework.filestru.builder;

import com.bln.framework.filestru.IFileBuildable;

/**
 * �ļ�����������
 */
public abstract class FileBuilderBase implements IFileBuilder{
	
	/**
	 * �ļ�����
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
