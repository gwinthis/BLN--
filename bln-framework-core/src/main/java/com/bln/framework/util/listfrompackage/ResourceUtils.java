/**
 * @author gengw
 * Created on Nov 29, 2012
 */
package com.bln.framework.util.listfrompackage;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.bln.framework.util.asserter.Assert;

/**
 * 资源工具
 */
public class ResourceUtils {
	
	
	/**
	 * 读取包中的配置文件
	 * @param fileUrl 文件所在的路径，按包的路径读取，包之间按/划分
	 * @return 配置文件的字节数组
	 * @throws IOException 读取文件所抛出的异常
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
