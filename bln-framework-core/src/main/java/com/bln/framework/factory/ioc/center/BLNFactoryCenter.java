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
 * BLN工厂中心
 */
public class BLNFactoryCenter extends BaseObj{
	
	/**
	 * 单例
	 */
	private static BLNFactoryCenter single = new BLNFactoryCenter();

	/**
	 * 是否已经全部加载工厂
	 */
	private boolean isLoadAllFactory = false;
	
	/**
	 * 工厂缓存
	 */
	private final Map<String, IBLNFactory> factoryCache = Collections.synchronizedMap(new HashMap<String, IBLNFactory>());

	/**
	 * 配置文件加载器
	 */
	List<IConfigLoader> configLoaders = Collections.synchronizedList(new ArrayList<IConfigLoader>());

	/**
	 * 所有工厂加载器
	 */
	List<IFactoryCenterLoader> allFactroyLoaders = Collections.synchronizedList(new ArrayList<IFactoryCenterLoader>());
	
	/**
	 * 构造函数
	 */
	private BLNFactoryCenter(){
		
		super();
		
		//配置默认配置文件加载器
		configLoaders.add(new ConfigLoaderResource());
		
		//配置默认所有工厂加载器
		allFactroyLoaders.add(new ReadPackageLoader());
	}
	
	/**
	 * 获得单例
	 * @return
	 */
	public static BLNFactoryCenter singleton(){
		return single;
	}

	/**
	 * 加载所有工厂
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
	 * 根据配置文件路径加载工厂
	 * @param configUrl 配置文件路径
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
	 * 是否已经全部加载完成
	 * @return
	 */
	public boolean isLoadAllFactory() {
		return isLoadAllFactory;
	}	
}
