/**
 * @author gengw
 * Created on 2012-03-27
 */
package com.bln.framework.edi.output;

import java.io.IOException;

/**
 * EDI输出接口
 */
public interface IEdiOutput {

	/**
	 * 输出EDI信息
	 * @param ediData EDI信息，以字节数组形式存储
	 * @throws IOException 输出抛出的异常
	 */
	public void output(String ediData) throws IOException;
}
