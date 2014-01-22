/**
 * @author gengw
 * Created on Nov 30, 2012
 */
package com.bln.framework.factory.ioc.config.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.factory.exception.ConfigDependencyCircleException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.loader.IConfigLoader;
import com.bln.framework.factory.ioc.config.parser.extend.ExtendsParser;
import com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser;
import com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser;
import com.bln.framework.factory.ioc.config.parser.mixin.MixinParser;

/**
 * IOC工厂配置解析器
 */
public class ConfigParser implements IConfigParser {
		
	/**
	 * 配置文件加载器
	 */
	protected List<IConfigLoader> configLoaders = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.IConfigParser#parse(java.lang.String)
	 */
	public IBLNFactoryConfig parse(String configUrl) throws IOException{
		
		//1.加载配置对象
		IBLNFactoryConfig config = this.loadConfig(configUrl);
		
		//2.校验依赖循环
		checkDependencyCircle(configUrl, config);
		
		//3.处理继承
		config = this.parseExtends(configUrl, config);
		
		//4.处理混入
		config = this.parseMixin(configUrl, config);
		
		//5.设置父类原始配置
		config.setAncestorNames(this.parseAncestorNames(configUrl));
		
		//6.返回配置
		return config;
	}
	

		
	/**
	 * 校验是否存在依赖混入
	 */
	protected void checkDependencyCircle(String configUrl, IBLNFactoryConfig config){
		
		List<String> visitPath = new ArrayList<String>();
		visitPath.add(configUrl);
		
		this.visitDependency(config, visitPath);
		
	}
	
	/**
	 * 访问依赖路径
	 * @param config 配置信息
	 * @param visitPath 访问路径
	 */
	protected void visitDependency(IBLNFactoryConfig config, List<String> visitPath){
		
		List<String> dependencyConfigUrls = new ArrayList<String>();
		
		//添加继承
		String extendConfigUrl = config.getParentConfigUrl();
		if(!StringUtils.isEmpty(extendConfigUrl)){
			dependencyConfigUrls.add(extendConfigUrl);			
		}
		
		//添加混入
		String[] mixinConfigUrls = config.getMixinConfigUrls();
		if(mixinConfigUrls != null && mixinConfigUrls.length > 0){
			for ( String url : mixinConfigUrls){
				dependencyConfigUrls.add(url);
			}		
		}
		
		//校验
		if(dependencyConfigUrls != null && dependencyConfigUrls.size() > 0){
			
			for (String dependencyUrl : dependencyConfigUrls){
				
				//校验当前混入路径是否已经访问过
				if(visitPath.contains(dependencyUrl)){
					
					StringBuilder detail = new StringBuilder();
					for ( String visit: visitPath){
						detail.append(visit).append("--->");
					}
					detail.append(dependencyUrl);
					
					throw new ConfigDependencyCircleException("It founds ConfigDependCircle Exception, detail: " + detail.toString());
				
				}else{
					
					//生成混入配置
					IBLNFactoryConfig dependencyConfig = this.loadConfig(dependencyUrl);
					
					//备份访问路径
					List<String> newVisitPath = new ArrayList<String>(visitPath.size());
					newVisitPath.addAll(visitPath);
					
					//添加下一个访问者
					newVisitPath.add(dependencyUrl);
					
					//继续访问
					visitDependency(dependencyConfig, newVisitPath);					
				}	
			}
		}
	} 

	/**
	 * 解析继承
	 * @param configUrl 配置文件路径
	 * @param config 配置文件
	 * @return IBLNFactoryConfig config
	 * @throws IOException
	 */
	protected IBLNFactoryConfig parseExtends(String configUrl, IBLNFactoryConfig config) throws IOException{
		
		//定义继承解析
		IExtendsParser extendsParser = new ExtendsParser(this, configUrl, config);
		
		//执行解析
		config = extendsParser.parse();		
		extendsParser = null;
		
		return config;
	}
	
	/**
	 * 解析混入
	 * @param configUrl
	 * @param config
	 * @return
	 * @throws IOException
	 */
	protected IBLNFactoryConfig parseMixin(String configUrl, IBLNFactoryConfig config) throws IOException{
		
		//获取混入解析
		IMixinParser mixinParser = new MixinParser(this, configUrl, config);
		
		//执行解析
		config = mixinParser.parse();
		mixinParser = null;
		
		//返回配置
		return config;
	}
	
	/**
	 * 获取配置文件的祖先名称
	 */
	protected String[] parseAncestorNames(String configUrl){

		List<String> ancestors = new ArrayList<String>();
		
		//2.解析父类配置文件
		String parentConfigUrl = configUrl;
		while (!StringUtils.isEmpty(parentConfigUrl)){
			
			//读取父类配置文件
			IBLNFactoryConfig parentConfig = this.loadConfig(parentConfigUrl);
						
			//添加父类配置路径中
			ancestors.add(0, parentConfig.getFactoryName());
			
			//获取父类配置路径
			parentConfigUrl = parentConfig.getParentConfigUrl();
		}
		
		return ancestors.toArray(new String[]{});
	}
	
	/**
	 * 加载配置文件对象
	 * @param configUrl
	 * @return
	 */
	protected IBLNFactoryConfig loadConfig(String configUrl){
		
		//尝试所有加载器加载配置对象
		IBLNFactoryConfig config = null;
		for ( IConfigLoader configLoader : configLoaders){
			try {
				config = configLoader.load(configUrl);
				if(config != null){
					break;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		return config;	
	}

	/**
	 * @return the configLoaders
	 */
	public List<IConfigLoader> getConfigLoaders() {
		return configLoaders;
	}

	/**
	 * @param configLoaders the configLoaders to set
	 */
	public void setConfigLoaders(List<IConfigLoader> configLoaders) {
		this.configLoaders = configLoaders;
	}
	
	public ConfigParser() {
		super();
	}

	public ConfigParser(List<IConfigLoader> configLoaders) {
		super();
		this.configLoaders = configLoaders;
	}

}
