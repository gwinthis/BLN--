/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.exception.LoadInstanceErrorException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.IObjectLoader;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;
import com.bln.framework.factory.ioc.load.ObjectLoaderFactory;
import com.bln.framework.factory.ioc.load.collections.MapLoader;
import com.bln.framework.util.listfrompackage.ListResourceFromPackage;

/**
 * 从包中获取数据生成Map
 */
public class FromPackageCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {
		
		//1.获取包下的资源
		
		//获取包名
		String packageName = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//获取包下的资源
		ListResourceFromPackage listResource = new ListResourceFromPackage(packageName);
		List<String> resources = listResource.list(null);
		
		//如果包下资源为空直接返回
		if(resources == null || resources.isEmpty()){
			return null;
		}
		
		//2.根据名称和生成器构造Map
		
		//获得值生成器
		IBuilder1Step<Object, String> builder = null;
		Class<?> compontClazz = null;
		
		String builderClazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		if(!StringUtils.isEmpty(builderClazz)){
			builder = (IBuilder1Step<Object, String>)factory.getInstance(builderClazz);
		}else{
			String componentClassName = configEntity.getAttr(IBLNFactoryConfig.ATTR_COMPONENT_CLASS);
			if(!StringUtils.isEmpty(builderClazz)){
				compontClazz = Class.forName(componentClassName);
			}
			
		}
		
		//获得子元素通用定义方式
		List<IBLNFactoryConfigEntity> subEntitys = configEntity.getSubEntitys();
		IBLNFactoryConfigEntity commonSubEntityConfig = findSubEntityConfig(subEntitys);
		if(commonSubEntityConfig != null && !StringUtils.isEmpty(commonSubEntityConfig.getAttr(IBLNFactoryConfig.ATTR_VALUE))){
			throw new LoadInstanceErrorException("The attribute value can't be set in commonSubEntityConfig!");
		}
		
		//获得通用子元素的对象加载器
		IObjectLoader commonSubObjectLoader = null;
		if(commonSubEntityConfig != null){
			commonSubObjectLoader = ObjectLoaderFactory.getObjLoader(commonSubEntityConfig);
		}

		//获得单独定义的对象
		Map<String, Object> specInstanceMap = this.specialInstanceMap(factory, configEntity, commonSubEntityConfig, subEntitys);

		//是否存在单独定义的子元素
		boolean hasSpecInstance = specInstanceMap != null && !specInstanceMap.isEmpty();
		
		//根据名称和生成器构造Map
		Map<String, Object> resourceMap = new HashMap<String, Object>(resources.size());
		for ( String resource : resources){
			String simpleName = this.getSimpleName(resource);
			
			//生成实例，如果定义了子元素生成方式，根据子元素方式生成，否则根据包的构造器创建
			Object object = null;
			if(hasSpecInstance && specInstanceMap.containsKey(simpleName)){
				
				object = specInstanceMap.get(simpleName);
				
			} else if(commonSubEntityConfig != null){
				
				commonSubEntityConfig.setAttr(IBLNFactoryConfig.ATTR_VALUE, resource);
				object = commonSubObjectLoader.load(factory, commonSubEntityConfig);
				
			}else{
				
				if(builder != null){
					object = builder.build(resource);
				}else{
					object = compontClazz.newInstance();
				}
			}

			//添加到实例池中
			resourceMap.put(simpleName, object);
		}
		
		//返回
		return resourceMap;
	}
	
	/**
	 * 获得资源的简单名称
	 * @param resource 资源名称
	 * @return 资源的简单名称
	 */
	protected String getSimpleName(String resource){
		
		int depotIdx = resource.indexOf(".");
		int splitIdx = resource.lastIndexOf("/");
		
		String simpleName = resource.substring(splitIdx + 1, depotIdx);
		return simpleName;
	}
	
	/**
	 * 获得子元素的通用配置
	 * @param subEntitys
	 * @return
	 * @throws Throwable 
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> specialInstanceMap(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity, IBLNFactoryConfigEntity commonSubEntityConfig, List<IBLNFactoryConfigEntity> subEntitys) throws Throwable{
		
		Map<String, Object> specInstanceMap = null;
		if(subEntitys != null && subEntitys.size() > 0){
			
			int idx = -1;
			if(commonSubEntityConfig != null){
				idx = subEntitys.indexOf(commonSubEntityConfig);
				subEntitys.remove(idx);
			}
			
			MapLoader mapLoader = new MapLoader();
			
			String oriCollectionClass = configEntity.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS);
			
			configEntity.setAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS, "org.apache.commons.collections.map.ListOrderedMap");
			specInstanceMap = (Map<String, Object>)mapLoader.load(factory, configEntity);
			configEntity.setAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS, oriCollectionClass);
			
			if(idx != -1){
				subEntitys.add(idx, commonSubEntityConfig);
			}
		}

		return specInstanceMap;
	}
	
	/**
	 * 获得子元素的通用配置
	 * @param subEntitys
	 * @return
	 */
	protected IBLNFactoryConfigEntity findSubEntityConfig(List<IBLNFactoryConfigEntity> subEntitys){
		
		IBLNFactoryConfigEntity configCommonEntity = null;
		if(subEntitys != null && subEntitys.size() > 0){
			for ( IBLNFactoryConfigEntity config : subEntitys){
				if("common".equals(config.getAttr(IBLNFactoryConfig.ATTR_NAME))){
					configCommonEntity = config;
					break;
				}
			}
		}
		
		return configCommonEntity;
	}
	

}
