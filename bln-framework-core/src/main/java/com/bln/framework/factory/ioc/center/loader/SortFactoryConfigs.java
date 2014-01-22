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
 * 排序工厂配置
 */
class SortFactoryConfigs {

	/**
	 * 排序所有工厂
	 * @param configs 未排序的工厂
	 * @return 排序后的工厂
	 */
	public List<IBLNFactoryConfig> sort(List<IBLNFactoryConfig> configs){
		
		if(configs == null || configs.size() <= 0){
			return configs;
		}
		
		//获取工厂的依赖关系
		Map<String, List<String>> allDependency = this.listAllFacotyDependency(configs);
		
		//1.获取所有加权配置对象
		List<ConfigFactor> configFactors = this.countAllConfigLayer(configs, allDependency);
		
		//2.排序加权配置对象
		Collections.sort(configFactors);
		
		//3.获得排序后的配置
		List<IBLNFactoryConfig> orderConfigs = new ArrayList<IBLNFactoryConfig>();
		for ( ConfigFactor configFactor : configFactors){
			orderConfigs.add(configFactor.getConfig());
		}
		
		//4.返回排序后的配置对象
		return orderConfigs;
	}
	
	/**
	 * 计算所有配置对象的层数，如果工厂之间是否存在依赖循环将抛出异常
	 * @param configs 所有配置文件
	 * @param allDependency 配置文件的依赖
	 */
	private List<ConfigFactor> countAllConfigLayer(List<IBLNFactoryConfig> configs, Map<String, List<String>> allDependency){
		
		List<ConfigFactor> configFactors = new ArrayList<ConfigFactor>(configs.size());
		for ( IBLNFactoryConfig config : configs){
			
			//计算工厂的依赖层数
			List<String> parents = new ArrayList<String>();
			
			String name = config.getFactoryName();
			parents.add(name);
			
			int layer = this.countConfigLayer(name, allDependency, parents);
			
			//生成加权配置对象
			ConfigFactor configFactor = new ConfigFactor(config, layer);
			configFactors.add(configFactor);
		}
		
		//返回加权配置信息
		return configFactors;
	}
	
	/**
	 * 计算配置的依赖层数
	 * @param checkConfig 需要校验的配置文件
	 * @param currentFactoryName 当前的配置信息名称
	 * @param allDependency 所有配置所对应的依赖配置
	 * @param visitInfo 访问路径
	 * @return 配置的依赖层数
	 */
	private int countConfigLayer(String currentFactoryName, Map<String, List<String>> allDependency, List<String> parents){
				
		//获取当前工厂的依赖工厂
		List<String> dependencys = allDependency.get(currentFactoryName);
		if(dependencys == null || dependencys.size() <= 0){
			return 0;
		}
		
		//每个依赖项的层数
		int[] depLayers = new int[dependencys.size()];
		
		for ( int i = 0, n = dependencys.size(); i < n; i++){
			
			//获取依赖工厂
			String dependencyName = dependencys.get(i);
			 
			//添加依赖路径
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
		
		//查找最大的层数
		int maxLayer = 0;
		for (int layer : depLayers){
			if(maxLayer < layer){
				maxLayer = layer;
			}
		}
		
		//返回最大的层数
		return maxLayer;
	}

	/**
	 * 列出所有工厂的依赖工厂
	 * @param configs 所有配置文件
	 * @return 所有工厂的依赖关系
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
	 * 列出该工厂所依赖的其他工厂
	 * @param config
	 * @return 依赖工厂列表
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
	 * 添加依赖工厂
	 * @param configEntitys 配置信息
	 * @param result 结果
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
	 * 加权配置对象
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
