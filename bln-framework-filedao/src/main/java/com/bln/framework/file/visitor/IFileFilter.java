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
	 * ÎÄ¼þ¼Ð¹ýÂË
	 * @param file
	 * @return
	 */
	public boolean dirFilter(File file);

}
