/**
 * @author gengw
 */
package com.bln.framework.file.visitor;

import java.io.File;

/**
 * 
 *
 */
public interface IFileFilter {
	
	/**
	 * @param file
	 */
	public boolean fileFilter(File file);

	/**
	 * �ļ��й���
	 * @param file
	 * @return
	 */
	public boolean dirFilter(File file);

}
