/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.factory.ioc.config.loader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.bln.framework.util.asserter.Assert;

/**
 * 通过资源加载配置文件
 */
public class ConfigLoaderResource extends ConfigLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.loader.ConfigLoaderBase#loadConfig(java.lang.Object)
	 */
	@Override
	protected byte[] loadConfig(String fileUrl) throws IOException {
		Assert.paramIsNotNull(fileUrl, "fileUrl");
		
		InputStream is = this.getClass().getResourceAsStream(fileUrl);
		Assert.notNull(is, "%s not found in the claspath!", fileUrl);
		
		byte[] bytes = IOUtils.toByteArray(is);
		return bytes;
	}

}
