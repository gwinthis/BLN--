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
 * <p>�滻�鹤��</p>
 * ��ʵ������ʱ��FactoryCenter��û�и��๤�������ƣ�����ʵ�־�̬�滻��ʵ�ʵĹ��������ܡ�
 */
class ReplaceVirtualFactory {
	
	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(ReplaceVirtualFactory.class);
	
	/**
	 * Ҫ������������ö���
	 */
	protected List<IBLNFactoryConfig> allConfigs = null;

	/**
	 * ���캯��
	 * @param configs
	 */
	public ReplaceVirtualFactory(List<IBLNFactoryConfig> configs) {
		super();
		this.allConfigs = configs;
	}

	/**
	 * �滻���������ļ�
	 * @param allConfigs ���������ļ�
	 * @throws IOException 
	 */
	public void replaceAllConfigs() throws IOException{
		for ( IBLNFactoryConfig config : allConfigs){
			log.debug("Process virtrual factory in the factory " + config.getFactoryName());
			this.replaceConfig(config);
		}
	}
	
	/**
	 * �滻�����ļ�
	 * @param currentConfig �����ļ�
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
	 * �滻���ʵ��
	 * @param configEntitys Ҫ�����ʵ������
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
	 * �滻���ԵĹ�������
	 * @param configEntity ����ʵ��
	 * @throws IOException
	 */
	private void replaceClass(IBLNFactoryConfigEntity configEntity) throws IOException{

		//1.��ù�������
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//���ʹ�õ�ǰ������ִ���滻
		if(IBLNFactoryConfig.VAL_CURRENT_FACTORY_IDENTIFIER.equals(clazz)){
			return;
		}
		
		//2.����Ҫ�滻�󹤳�������
		String targetClazz = null;
		for ( IBLNFactoryConfig config : allConfigs){
			if(config.isTypeOf(clazz)){
				targetClazz = config.getFactoryName();
				break;
			}
		}
		
		//3.�滻��������
		if(targetClazz != null){
			configEntity.setAttr(IBLNFactoryConfig.ATTR_VALUE, targetClazz, true);
		}else{
			NotFoundFactoryException nffe = new NotFoundFactoryException("when process enity " + configEntity.getAttr(IBLNFactoryConfig.ATTR_NAME) + ", not found otherFactory " + clazz + " in the FactoryCenter!");
			throw nffe;
		}
	}

}
