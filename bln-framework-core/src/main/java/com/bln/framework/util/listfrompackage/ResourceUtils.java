/**
 * @author gengw
 * Created on Nov 29, 2012
 */
package com.bln.framework.util.listfrompackage;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.bln.framework.util.asserter.Assert;

/**
 * ��Դ����
 */
public class ResourceUtils {
	
	
	/**
	 * ��ȡ���е������ļ�
	 * @param fileUrl �ļ����ڵ�·����������·����ȡ����֮�䰴/����
	 * @return �����ļ����ֽ�����
	 * @throws IOException ��ȡ�ļ����׳����쳣
	 */
	public static byte[] readFileInPackage(String fileUrl) throws IOException{
		Assert.paramIsNotNull(fileUrl, "fileUrl");
		
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(ResourceUtils.class.getResourceAsStream(fileUrl));
		} catch (Throwable e) {
			IOException ioe = new IOException("read file " + fileUrl + " error!");
			ioe.initCause(e);
			throw ioe; 
		}
		return bytes;
	}

}
