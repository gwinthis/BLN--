/**
 * @author gengw
 * Created on May 18, 2012
 */
package com.bln.framework.factory.ioc.center.loader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.factory.exception.NotFoundFactoryException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

/**
 * <p>替换虚工厂</p>
 * 在实际运行时，FactoryCenter中没有父类工厂的名称，该类实现静态替换成实际的工厂对象功能。
 */
class ReplaceVirtualFactory {
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(ReplaceVirtualFactory.class);
	
	/**
	 * 要处理的所有配置对象
	 */
	protected List<IBLNFactoryConfig> allConfigs = null;

	/**
	 * 构造函数
	 * @param configs
	 */
	public ReplaceVirtualFactory(List<IBLNFactoryConfig> configs) {
		super();
		this.allConfigs = configs;
	}

	/**
	 * 替换所有配置文件
	 * @param allConfigs 所有配置文件
	 * @throws IOException 
	 */
	public void replaceAllConfigs() throws IOException{
		for ( IBLNFactoryConfig config : allConfigs){
			log.debug("Process virtrual factory in the factory " + config.getFactoryName());
			this.replaceConfig(config);
		}
	}
	
	/**
	 * 替换配置文件
	 * @param currentConfig 配置文件
	 * @throws IOException 
	 */
	private void replaceConfig(IBLNFactoryConfig currentConfig) throws IOException{
		
		Map<String, List<IBLNFactoryConfigEntity>> objectEntitys = currentConfig.getAllConfigEntityOfObjs();
		if(objectEntitys != null && !objectEntitys.isEmpty()){
			for ( Map.Entry<String, List<IBLNFactoryConfigEntity>> entry : objectEntitys.entrySet()){
				List<IBLNFactoryConfigEntity> configEntitys = entry.getValue();
				this.replaceEntitys(configEntitys);
			}			
		}
	}
	
	/**
	 * 替换多个实体
	 * @param configEntitys 要处理的实体配置
	 * @throws IOException
	 */
	private void replaceEntitys(List<IBLNFactoryConfigEntity> configEntitys) throws IOException{

		if(configEntitys != null && configEntitys.size() > 0){
			for ( IBLNFactoryConfigEntity configEntity : configEntitys){
				if(IBLNFactoryConfig.VALUE_CREATOR_FACTORYCENTER.equals(configEntity.getAttr(IBLNFactoryConfig.ATTR_CREATOR))){
					this.replaceClass(configEntity);
				}else{
					this.replaceEntitys(configEntity.getSubConfigEntitys());
				}
				
			}
		}
	}
	
	/**
	 * 替换属性的工厂名称
	 * @param configEntity 配置实体
	 * @throws IOException
	 */
	private void replaceClass(IBLNFactoryConfigEntity configEntity) throws IOException{

		//1.获得工厂名称
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//如果使用当前工厂不执行替换
		if(IBLNFactoryConfig.VAL_CURRENT_FACTORY_IDENTIFIER.equals(clazz)){
			return;
		}
		
		//2.查找要替换后工厂的名称
		String targetClazz = null;
		for ( IBLNFactoryConfig config : allConfigs){
			if(config.isTypeOf(clazz)){
				targetClazz = config.getFactoryName();
				break;
			}
		}
		
		//3.替换工厂名称
		if(targetClazz != null){
			configEntity.setAttr(IBLNFactoryConfig.ATTR_VALUE, targetClazz, true);
		}else{
			NotFoundFactoryException nffe = new NotFoundFactoryException("when process enity " + configEntity.getAttr(IBLNFactoryConfig.ATTR_NAME) + ", not found otherFactory " + clazz + " in the FactoryCenter!");
			throw nffe;
		}
	}

}
