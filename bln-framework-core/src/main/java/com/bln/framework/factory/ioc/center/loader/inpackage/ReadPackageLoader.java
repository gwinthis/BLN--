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
 * ��ȡ���µ����������ļ�
 */
public class ReadPackageLoader extends FactoryCenterLoaderBase implements IFactoryCenterLoader{
		
	/**
	 * �Ӹð��ж�ȡ�����ļ�
	 */
	private static final String PACKAGE_NAME = "com.bln.framework.config.factory";
	
	/**
	 * �г����������ļ�·��
	 * @return ���й��������ļ�·��
	 * @throws IOException 
	 */
	protected List<String> listConfigUrls() throws IOException{
		
		ListResourceFromPackage listResource = new ListResourceFromPackage(PACKAGE_NAME);
		List<String> configUrls = listResource.list(".xml");
		
		return configUrls;
	}
}
