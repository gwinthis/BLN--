/**
 * @author gengw
 * Created on May 28, 2012
 */
package com.bln.framework.filestru.xml.strategy;

import org.dom4j.io.OutputFormat;

/**
 * 输出格式策略
 */
public class CompactWriteFormatStrategy implements IWriteFormatStrategy {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.strategy.IWriteFormatStrategy#createFormat(java.lang.String)
	 */
	public OutputFormat createFormat(String encoding){
		
		OutputFormat xmlFormat = new OutputFormat("", false, encoding);
		xmlFormat.setPadText(true);
		
		return xmlFormat;
		
	}

}
