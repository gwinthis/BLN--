/**
 * @author gengw
 */
package com.bln.framework.file.visitor;

import java.io.File;
import java.io.IOException;

/**
 * 
 *
 */
public interface IFileHandler {
	
	/**
	 * @param file
	 * @param contextPath
	 * @throws IOException 
	 */
	public void handle(File file, String contextPath) throws IOException;

}
