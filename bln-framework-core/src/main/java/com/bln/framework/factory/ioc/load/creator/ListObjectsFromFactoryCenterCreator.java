/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * <p>
 * 从BLNFactoryCenter中查找指定对象，以Map<String, Object>方式返回。key为工厂名称，value为要查找的对象。
 * </p>
 * <p>
 * 根据class属性，从BLNFactoryCenter中遍历所有工厂，获取指定工厂路径的对象。
 * </p>
 */
public class ListObjectsFromFactoryCenterCreator extends ObjectLoaderBase {

	/**
	 * 静态缓存
	 */
	static Map<String, Map<String, Object>> cache = new HashMap<String, Map<String, Object>>();
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {

		// 定义要返回的结果
		Map<String, Object> result = null;

		// 获得要查找对象的工厂路径
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//获取对象结果
		if(cache.containsKey(clazz)){
			result = (Map<String, Object>)cache.get(clazz);
		}else{
			result = this.findObjects(clazz);
			cache.put(clazz, result);
		}

		return result;
	}

	/**
	 * 查找对象
	 * @param clazz
	 * @return
	 */
	protected Map<String, Object> findObjects(String clazz) {

		// 定义要返回的对象
		Map<String, Object> result = new HashMap<String, Object>();

		// 从工厂中心获取指定对象
		Map<String, IBLNFactory> factoryMap = BLNFactoryCenter.singleton().getAllFactories();
		for (Map.Entry<String, IBLNFactory> factoryEntry : factoryMap.entrySet()) {
			IBLNFactory blnFactory = factoryEntry.getValue();

			try {
				Object obj = blnFactory.getInstance(clazz);
				if (obj != null) {
					result.put(blnFactory.getFactoryName(), obj);
				}
			} catch (Throwable e) {

			}

		}

		return result;
	}
}
