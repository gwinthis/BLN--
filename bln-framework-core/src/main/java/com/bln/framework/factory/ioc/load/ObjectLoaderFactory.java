/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.factory.ioc.load;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.base.cacheable.BaseObjCacheable;
import com.bln.framework.factory.exception.LoadInstanceErrorException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.bean.BeanLoader;
import com.bln.framework.factory.ioc.load.collections.ArrayLoader;
import com.bln.framework.factory.ioc.load.collections.CollectionLoader;
import com.bln.framework.factory.ioc.load.collections.MapLoader;
import com.bln.framework.factory.ioc.load.collections.PropertiesLoader;
import com.bln.framework.factory.ioc.load.creator.BuilderCreator;
import com.bln.framework.factory.ioc.load.creator.FactoryCenterCreator;
import com.bln.framework.factory.ioc.load.creator.FromFactoryCreator;
import com.bln.framework.factory.ioc.load.creator.FromOtherFactoryCreator;
import com.bln.framework.factory.ioc.load.creator.FromPackageCreator;
import com.bln.framework.factory.ioc.load.creator.FromThisFactoryCreator;
import com.bln.framework.factory.ioc.load.creator.ListObjectsFromFactoryCenterCreator;
import com.bln.framework.factory.ioc.load.creator.NewObjectCreator;
import com.bln.framework.factory.ioc.load.creator.NullObjectCreator;
import com.bln.framework.factory.ioc.load.creator.OutServiceCreator;
import com.bln.framework.factory.ioc.load.creator.TransValueToOthCreator;
import com.bln.framework.factory.ioc.load.desc.DescLoader;


/**
 * 
 * 对象加载器工厂
 */
public class ObjectLoaderFactory extends BaseObjCacheable{

	/**
	 * 本地Bean加载器
	 */
	static IObjectLoader beanLoader = new BeanLoader();
	
	/**
	 * 描述信息加载器
	 */
	static IObjectLoader descLoader = new DescLoader();
	
	/**
	 * 通过构造器的对象加载器
	 */
	static IObjectLoader builderLoader = new BuilderCreator();
	
	/**
	 * 服务对象加载器
	 */
	static IObjectLoader serviceCreator = new OutServiceCreator();

	/**
	 * 从当前工厂获取对象的加载器
	 */
	static IObjectLoader fromThisFactoryCreator = new FromThisFactoryCreator();

	/**
	 * 从其他工厂获取对象的加载器
	 */
	static IObjectLoader fromOtherFactoryCreator = new FromOtherFactoryCreator();

	/**
	 * 从指定工厂获取对象的加载器
	 */
	static IObjectLoader fromTheFactoryCreator = new FromFactoryCreator();

	/**
	 * 新建对象的加载器
	 */
	static IObjectLoader newObjectCreator = new NewObjectCreator();
	
	/**
	 * 根据Value值转换成指定类型的加载器
	 */
	static IObjectLoader transValueToOthCreator = new TransValueToOthCreator();

	/**
	 * 从包中加载资源的加载器
	 */
	static IObjectLoader fromPackgeCreator = new FromPackageCreator();

	/**
	 * 从工厂中心中获取工厂对象
	 */
	static IObjectLoader factoryCenterCreator = new FactoryCenterCreator();

	/**
	 * 从工厂中心获取指定路径的对象
	 */
	static IObjectLoader listObjectsFromFactoryCenterCreater = new ListObjectsFromFactoryCenterCreator();
	
	/**
	 * 空值对象加载器
	 */
	static IObjectLoader nullObjectCreator = new NullObjectCreator();

	/**
	 * 数组类型加载器
	 */
	static IObjectLoader arrayLoader = new ArrayLoader();

	/**
	 * 容器类型加载器
	 */
	static IObjectLoader collectionLoader = new CollectionLoader();

	/**
	 * 映射类型加载器
	 */
	static IObjectLoader mapLoader = new MapLoader();

	/**
	 *  配置类型加载器
	 */
	static IObjectLoader propertiesLoader = new PropertiesLoader();
	
	/**
	 * 获得对象加载器
	 * @param factory
	 * @param config
	 * @return 对象加载器
	 */
	public static IObjectLoader getObjLoader(IBLNFactoryConfigEntity config) {
		
		
		IObjectLoader objectLoader = null;
		
		//根据creator、collection_type和clazz判断加载器
		String creatorType = config.getAttr(IBLNFactoryConfig.ATTR_CREATOR);
		String collectionType = config.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONTYPE);
		String clazz = config.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		String value = config.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		if(!StringUtils.isEmpty(creatorType)){
				
			//Creator加载器
			if(IBLNFactoryConfig.VALUE_CREATOR_FROM_THIS_FACTORY.equals(creatorType)){
				objectLoader = fromThisFactoryCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FROM_OTHER_FACTORY.equals(creatorType)){
				objectLoader = fromOtherFactoryCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_TRANS_FROM_VALUE.equals(creatorType)){
				objectLoader = transValueToOthCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FROM_THE_FACTORY.equals(creatorType)){
				objectLoader = fromTheFactoryCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_OTS_LOCATER.equals(creatorType)){
				objectLoader = serviceCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_BUILDER.equals(creatorType)){
				objectLoader = builderLoader;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_NEW_OBJECT.equals(creatorType)){
				objectLoader = newObjectCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FACTORYCENTER.equals(creatorType)){
				objectLoader = factoryCenterCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_LISTOBJECTSFROMFACTORYCENTER.equals(creatorType)){
				objectLoader = listObjectsFromFactoryCenterCreater;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FROMPACKAGE.equals(creatorType)){
				objectLoader = fromPackgeCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_NULLVALE.equals(creatorType)){
				objectLoader = nullObjectCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_STRING_VALUE.equals(creatorType)){
				objectLoader = descLoader;
			}
		}else if(!StringUtils.isEmpty(collectionType)){
			
			//容器类型加载器
			if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_ARRAY.equals(collectionType)){
				objectLoader = arrayLoader;
			}else if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_COLLECTION.equals(collectionType)){
				objectLoader = collectionLoader;
			}else if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_MAP.equals(collectionType)){
				objectLoader = mapLoader;
			}else if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_PROPERTIES.equals(collectionType)){
				objectLoader = propertiesLoader;
			}
				
		}else if(!StringUtils.isEmpty(clazz)){
			
			//类加载器
			objectLoader = beanLoader;
			
		}else if(!StringUtils.isEmpty(value)){
			
			//信息描述加载器
			objectLoader = descLoader;
		}
		
		//如果未找到加载器抛出异常
		if(objectLoader == null){
			LoadInstanceErrorException liee = new LoadInstanceErrorException("not found object loader in the context!");
			liee.setContextValue("config", config);
			throw liee;
		}
		
		//返回加载器
		return objectLoader;
	}
}
