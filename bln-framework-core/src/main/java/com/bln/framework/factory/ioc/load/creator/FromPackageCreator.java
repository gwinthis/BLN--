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
 * �Ӱ��л�ȡ��������Map
 */
public class FromPackageCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {
		
		//1.��ȡ���µ���Դ
		
		//��ȡ����
		String packageName = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//��ȡ���µ���Դ
		ListResourceFromPackage listResource = new ListResourceFromPackage(packageName);
		List<String> resources = listResource.list(null);
		
		//���������ԴΪ��ֱ�ӷ���
		if(resources == null || resources.isEmpty()){
			return null;
		}
		
		//2.�������ƺ�����������Map
		
		//���ֵ������
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
		
		//�����Ԫ��ͨ�ö��巽ʽ
		List<IBLNFactoryConfigEntity> subEntitys = configEntity.getSubEntitys();
		IBLNFactoryConfigEntity commonSubEntityConfig = findSubEntityConfig(subEntitys);
		if(commonSubEntityConfig != null && !StringUtils.isEmpty(commonSubEntityConfig.getAttr(IBLNFactoryConfig.ATTR_VALUE))){
			throw new LoadInstanceErrorException("The attribute value can't be set in commonSubEntityConfig!");
		}
		
		//���ͨ����Ԫ�صĶ��������
		IObjectLoader commonSubObjectLoader = null;
		if(commonSubEntityConfig != null){
			commonSubObjectLoader = ObjectLoaderFactory.getObjLoader(commonSubEntityConfig);
		}

		//��õ�������Ķ���
		Map<String, Object> specInstanceMap = this.specialInstanceMap(factory, configEntity, commonSubEntityConfig, subEntitys);

		//�Ƿ���ڵ����������Ԫ��
		boolean hasSpecInstance = specInstanceMap != null && !specInstanceMap.isEmpty();
		
		//�������ƺ�����������Map
		Map<String, Object> resourceMap = new HashMap<String, Object>(resources.size());
		for ( String resource : resources){
			String simpleName = this.getSimpleName(resource);
			
			//����ʵ���������������Ԫ�����ɷ�ʽ��������Ԫ�ط�ʽ���ɣ�������ݰ��Ĺ���������
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

			//��ӵ�ʵ������
			resourceMap.put(simpleName, object);
		}
		
		//����
		return resourceMap;
	}
	
	/**
	 * �����Դ�ļ�����
	 * @param resource ��Դ����
	 * @return ��Դ�ļ�����
	 */
	protected String getSimpleName(String resource){
		
		int depotIdx = resource.indexOf(".");
		int splitIdx = resource.lastIndexOf("/");
		
		String simpleName = resource.substring(splitIdx + 1, depotIdx);
		return simpleName;
	}
	
	/**
	 * �����Ԫ�ص�ͨ������
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
	 * �����Ԫ�ص�ͨ������
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
