/**
 * @author gengw
 * Craeted on 2012-03-27
 */
package com.bln.framework.edi.output.log;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.edi.output.EdiOutputBase;

/**
 * 使用LOG日志输出EDI
 */
public class EdiOutputUsingLog extends EdiOutputBase{

	private int THREE_M_IN_UNICODE = (256 * 1024)/2;
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(EdiOutputUsingLog.class);

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.output.IEdiOutput#output(byte[], java.lang.String)
	 */
	public void output(String ediData) throws IOException {
		
		if(ediData != null && ediData.length() > THREE_M_IN_UNICODE){
			log.info("Request ediData is too big to write to output!");
		}else{
			log.info(ediData);
		}
	}

}
