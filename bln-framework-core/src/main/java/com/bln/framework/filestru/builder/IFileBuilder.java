/**
 * @author gengw
 * created on 2012-03-23
 */
package com.bln.framework.filestru.builder;

import com.bln.framework.filestru.IFileBuildable;

/**
 * �ļ����ɽӿ�
 *
 */
public interface IFileBuilder {
	
	/**
	 * ����ļ�����
	 * @return �ļ�����
	 */
	public IFileBuildable getFileBuildable();

	/**
	 * ��Ҫ���ɵ��ļ�����
	 * @param fileBuildableType �ļ�����
	 */
	public void setFileBuildable(IFileBuildable fileBuildable);
	
}