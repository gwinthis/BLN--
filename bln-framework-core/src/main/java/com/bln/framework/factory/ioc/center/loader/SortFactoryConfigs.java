/**
 * @author gengw
 * Created on May 12, 2012
 */
package com.bln.framework.factory.ioc.center.loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.factory.exception.DependencyCircleException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

/**
 * ���򹤳�����
 */
class SortFactoryConfigs {

	/**
	 * �������й���
	 * @param configs δ����Ĺ���
	 * @return �����Ĺ���
	 */
	public List<IBLNFactoryConfig> sort(List<IBLNFactoryConfig> configs){
		
		if(configs == null || configs.size() <= 0){
			return configs;
		}
		
		//��ȡ������������ϵ
		Map<String, List<String>> allDependency = this.listAllFacotyDependency(configs);
		
		//1.��ȡ���м�Ȩ���ö���
		List<ConfigFactor> configFactors = this.countAllConfigLayer(configs, allDependency);
		
		//2.�����Ȩ���ö���
		Collections.sort(configFactors);
		
		//3.�������������
		List<IBLNFactoryConfig> orderConfigs = new ArrayList<IBLNFactoryConfig>();
		for ( ConfigFactor configFactor : configFactors){
			orderConfigs.add(configFactor.getConfig());
		}
		
		//4.�������������ö���
		return orderConfigs;
	}
	
	/**
	 * �����������ö���Ĳ������������֮���Ƿ��������ѭ�����׳��쳣
	 * @param configs ���������ļ�
	 * @param allDependency �����ļ�������
	 */
	private List<ConfigFactor> countAllConfigLayer(List<IBLNFactoryConfig> configs, Map<String, List<String>> allDependency){
		
		List<ConfigFactor> configFactors = new ArrayList<ConfigFactor>(configs.size());
		for ( IBLNFactoryConfig config : configs){
			
			//���㹤������������
			List<String> parents = new ArrayList<String>();
			
			String name = config.getFactoryName();
			parents.add(name);
			
			int layer = this.countConfigLayer(name, allDependency, parents);
			
			//���ɼ�Ȩ���ö���
			ConfigFactor configFactor = new ConfigFactor(config, layer);
			configFactors.add(configFactor);
		}
		
		//���ؼ�Ȩ������Ϣ
		return configFactors;
	}
	
	/**
	 * �������õ���������
	 * @param checkConfig ��ҪУ��������ļ�
	 * @param currentFactoryName ��ǰ��������Ϣ����
	 * @param allDependency ������������Ӧ����������
	 * @param visitInfo ����·��
	 * @return ���õ���������
	 */
	private int countConfigLayer(String currentFactoryName, Map<String, List<String>> allDependency, List<String> parents){
				
		//��ȡ��ǰ��������������
		List<String> dependencys = allDependency.get(currentFactoryName);
		if(dependencys == null || dependencys.size() <= 0){
			return 0;
		}
		
		//ÿ��������Ĳ���
		int[] depLayers = new int[dependencys.size()];
		
		for ( int i = 0, n = dependencys.size(); i < n; i++){
			
			//��ȡ��������
			String dependencyName = dependencys.get(i);
			 
			//�������·��
			if(parents.contains(dependencyName)){
				StringBuilder visitInfo = new StringBuilder();
				for ( String name : parents){
					visitInfo.append(name);
					visitInfo.append("--->");
				}
				visitInfo.append(dependencyName);
				throw new DependencyCircleException("it found exsits factory dependcy circle : " + visitInfo.toString());
			}else{
				
				List<String> newParents = new ArrayList<String>(parents.size());
				newParents.add(dependencyName);
				
				depLayers[i] = 1 + this.countConfigLayer(dependencyName, allDependency, newParents);

			}
		}
		
		//�������Ĳ���
		int maxLayer = 0;
		for (int layer : depLayers){
			if(maxLayer < layer){
				maxLayer = layer;
			}
		}
		
		//�������Ĳ���
		return maxLayer;
	}

	/**
	 * �г����й�������������
	 * @param configs ���������ļ�
	 * @return ���й�����������ϵ
	 */
	private Map<String, List<String>> listAllFacotyDependency(List<IBLNFactoryConfig> configs){
		
		Map<String, List<String>> allDependency = new HashMap<String, List<String>>(configs.size());
		for (IBLNFactoryConfig config : configs){
			List<String> dependencys = listDendencyFactorys(config);
			allDependency.put(config.getFactoryName(), dependencys);
		}
		
		return allDependency;
	}
	
	/**
	 * �г��ù�������������������
	 * @param config
	 * @return ���������б�
	 */
	private List<String> listDendencyFactorys(IBLNFactoryConfig config){
		
		List<String> result = null;
		
		Map<String, List<IBLNFactoryConfigEntity>> objectEntitys = config.getAllConfigEntityOfObjs();
		if(objectEntitys != null && !objectEntitys.isEmpty()){
			
			result = new ArrayList<String>();
			for ( Map.Entry<String, List<IBLNFactoryConfigEntity>> entry : objectEntitys.entrySet()){
				List<IBLNFactoryConfigEntity> configEntitys = entry.getValue();
				addDependency(configEntitys, result);
			}
		}
		
		return result;
	}
	
	/**
	 * �����������
	 * @param configEntitys ������Ϣ
	 * @param result ���
	 */
	private void addDependency(List<IBLNFactoryConfigEntity> configEntitys, List<String> result){
		
		if(configEntitys != null && configEntitys.size() > 0){
			for ( IBLNFactoryConfigEntity configEntity : configEntitys){
				if(IBLNFactoryConfig.VALUE_CREATOR_FACTORYCENTER.equals(configEntity.getAttr(IBLNFactoryConfig.ATTR_CREATOR))){
					result.add(configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE));
				}
				this.addDependency(configEntity.getSubConfigEntitys(), result);
			}
		}
	}
	
	/**
	 * ��Ȩ���ö���
	 */
	private class ConfigFactor implements Comparable<ConfigFactor>{
		
		IBLNFactoryConfig config = null;
		int factor = 0;
		
		protected ConfigFactor(IBLNFactoryConfig config, int factor){
			this.config = config;
			this.factor = factor;
		}

		/**
		 * @return the config
		 */
		public IBLNFactoryConfig getConfig() {
			return config;
		}

//		/**
//		 * @param config the config to set
//		 */
//		public void setConfig(IBLNFactoryConfig config) {
//			this.config = config;
//		}
//
//		/**
//		 * @return the factor
//		 */
//		public int getFactor() {
//			return factor;
//		}
//
//		/**
//		 * @param factor the factor to set
//		 */
//		public void setFactor(int factor) {
//			this.factor = factor;
//		}
//		
//		/**
//		 * @param factor the factor to set
//		 */
//		public void addFactor() {
//			this.factor++;
//		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((config == null) ? 0 : config.hashCode());
			result = prime * result + factor;
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final ConfigFactor other = (ConfigFactor) obj;
			if (config == null) {
				if (other.config != null)
					return false;
			} else if (!config.equals(other.config))
				return false;
			if (factor != other.factor)
				return false;
			return true;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(ConfigFactor configFactor){
			int result = 0;
						
			if(this.factor == configFactor.factor){
				result = 0;
			}else if(this.factor > configFactor.factor){
				result = 1;
			}else if(this.factor < configFactor.factor){
				result = -1;
			}

			return result;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString(){
			StringBuilder info = new StringBuilder("config name : ");
			
			info.append(this.config.getFactoryName());
			
			info.append(", factor : ");
			info.append(this.factor);
			
			return info.toString();
		}
	}
}
