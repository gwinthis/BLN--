/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.factory.ioc.center;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.exception.NotFoundFactoryException;
import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.center.loader.IFactoryCenterLoader;
import com.bln.framework.factory.ioc.center.loader.inpackage.ReadPackageLoader;
import com.bln.framework.factory.ioc.config.loader.ConfigLoaderResource;
import com.bln.framework.factory.ioc.config.loader.IConfigLoader;

/**
 * BLN��������
 */
public class BLNFactoryCenter extends BaseObj{
	
	/**
	 * ����
	 */
	private static BLNFactoryCenter single = new BLNFactoryCenter();

	/**
	 * �Ƿ��Ѿ�ȫ�����ع���
	 */
	private boolean isLoadAllFactory = false;
	
	/**
	 * ��������
	 */
	private final Map<String, IBLNFactory> factoryCache = Collections.synchronizedMap(new HashMap<String, IBLNFactory>());

	/**
	 * �����ļ�������
	 */
	List<IConfigLoader> configLoaders = Collections.synchronizedList(new ArrayList<IConfigLoader>());

	/**
	 * ���й���������
	 */
	List<IFactoryCenterLoader> allFactroyLoaders = Collections.synchronizedList(new ArrayList<IFactoryCenterLoader>());
	
	/**
	 * ���캯��
	 */
	private BLNFactoryCenter(){
		
		super();
		
		//����Ĭ�������ļ�������
		configLoaders.add(new ConfigLoaderResource());
		
		//����Ĭ�����й���������
		allFactroyLoaders.add(new ReadPackageLoader());
	}
	
	/**
	 * ��õ���
	 * @return
	 */
	public static BLNFactoryCenter singleton(){
		return single;
	}

	/**
	 * �������й���
	 */
	public void loadAllFactory(){
		
		for ( IFactoryCenterLoader allFactoryLoader: allFactroyLoaders){
			try {
				allFactoryLoader.loadAllFactory(configLoaders, factoryCache);
				if(factoryCache != null && factoryCache.size() > 0){
					isLoadAllFactory = true;
					break;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���������ļ�·�����ع���
	 * @param configUrl �����ļ�·��
	 */
	public IBLNFactory getFactory(String factoryName){
		IBLNFactory blnFactory = factoryCache.get(factoryName);
		if(blnFactory == null){
			throw new NotFoundFactoryException(factoryName + " is not in BLNFactoryCenter");
		}
		return blnFactory;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.manager.IConfigManager#registerConfigLoader(com.bln.framework.factory.type.ioc.config.loader.IConfigLoader)
	 */
	public void registerConfigLoader(IConfigLoader configLoader){
		configLoaders.add(configLoader);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.manager.IConfigManager#registerConfigLoader(com.bln.framework.factory.type.ioc.config.manager.loader.IAllFactoryLoader)
	 */
	public void registerConfigCenterLoader(IFactoryCenterLoader allFactoryLoader){
		allFactroyLoaders.add(allFactoryLoader);
	}

	/**
	 * �Ƿ��Ѿ�ȫ���������
	 * @return
	 */
	public boolean isLoadAllFactory() {
		return isLoadAllFactory;
	}	
}
