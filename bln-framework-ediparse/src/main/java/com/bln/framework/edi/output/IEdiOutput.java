/**
 * @author gengw
 * Created on 2012-03-27
 */
package com.bln.framework.edi.output;

import java.io.IOException;

/**
 * EDI����ӿ�
 */
public interface IEdiOutput {

	/**
	 * ���EDI��Ϣ
	 * @param ediData EDI��Ϣ�����ֽ�������ʽ�洢
	 * @throws IOException ����׳����쳣
	 */
	public void output(String ediData) throws IOException;
}
