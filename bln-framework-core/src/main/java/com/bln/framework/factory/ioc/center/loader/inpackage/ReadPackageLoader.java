/**
 * @author gengw
 * Created on May 10, 2012
 */
package com.bln.framework.factory.ioc.center.loader.inpackage;

import java.io.IOException;
import java.util.List;

import com.bln.framework.factory.ioc.center.loader.FactoryCenterLoaderBase;
import com.bln.framework.factory.ioc.center.loader.IFactoryCenterLoader;
import com.bln.framework.util.listfrompackage.ListResourceFromPackage;

/**
 * 读取包下的所有配置文件
 */
public class ReadPackageLoader extends FactoryCenterLoaderBase implements IFactoryCenterLoader{
		
	/**
	 * 从该包中读取配置文件
	 */
	private static final String PACKAGE_NAME = "com.bln.framework.config.factory";
	
	/**
	 * 列出所有配置文件路径
	 * @return 所有工厂配置文件路径
	 * @throws IOException 
	 */
	protected List<String> listConfigUrls() throws IOException{
		
		ListResourceFromPackage listResource = new ListResourceFromPackage(PACKAGE_NAME);
		List<String> configUrls = listResource.list(".xml");
		
		return configUrls;
	}
}
