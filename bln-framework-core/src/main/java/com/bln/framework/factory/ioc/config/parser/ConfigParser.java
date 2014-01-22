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
 * IOC�������ý�����
 */
public class ConfigParser implements IConfigParser {
		
	/**
	 * �����ļ�������
	 */
	protected List<IConfigLoader> configLoaders = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.IConfigParser#parse(java.lang.String)
	 */
	public IBLNFactoryConfig parse(String configUrl) throws IOException{
		
		//1.�������ö���
		IBLNFactoryConfig config = this.loadConfig(configUrl);
		
		//2.У������ѭ��
		checkDependencyCircle(configUrl, config);
		
		//3.����̳�
		config = this.parseExtends(configUrl, config);
		
		//4.�������
		config = this.parseMixin(configUrl, config);
		
		//5.���ø���ԭʼ����
		config.setAncestorNames(this.parseAncestorNames(configUrl));
		
		//6.��������
		return config;
	}
	

		
	/**
	 * У���Ƿ������������
	 */
	protected void checkDependencyCircle(String configUrl, IBLNFactoryConfig config){
		
		List<String> visitPath = new ArrayList<String>();
		visitPath.add(configUrl);
		
		this.visitDependency(config, visitPath);
		
	}
	
	/**
	 * ��������·��
	 * @param config ������Ϣ
	 * @param visitPath ����·��
	 */
	protected void visitDependency(IBLNFactoryConfig config, List<String> visitPath){
		
		List<String> dependencyConfigUrls = new ArrayList<String>();
		
		//��Ӽ̳�
		String extendConfigUrl = config.getParentConfigUrl();
		if(!StringUtils.isEmpty(extendConfigUrl)){
			dependencyConfigUrls.add(extendConfigUrl);			
		}
		
		//��ӻ���
		String[] mixinConfigUrls = config.getMixinConfigUrls();
		if(mixinConfigUrls != null && mixinConfigUrls.length > 0){
			for ( String url : mixinConfigUrls){
				dependencyConfigUrls.add(url);
			}		
		}
		
		//У��
		if(dependencyConfigUrls != null && dependencyConfigUrls.size() > 0){
			
			for (String dependencyUrl : dependencyConfigUrls){
				
				//У�鵱ǰ����·���Ƿ��Ѿ����ʹ�
				if(visitPath.contains(dependencyUrl)){
					
					StringBuilder detail = new StringBuilder();
					for ( String visit: visitPath){
						detail.append(visit).append("--->");
					}
					detail.append(dependencyUrl);
					
					throw new ConfigDependencyCircleException("It founds ConfigDependCircle Exception, detail: " + detail.toString());
				
				}else{
					
					//���ɻ�������
					IBLNFactoryConfig dependencyConfig = this.loadConfig(dependencyUrl);
					
					//���ݷ���·��
					List<String> newVisitPath = new ArrayList<String>(visitPath.size());
					newVisitPath.addAll(visitPath);
					
					//�����һ��������
					newVisitPath.add(dependencyUrl);
					
					//��������
					visitDependency(dependencyConfig, newVisitPath);					
				}	
			}
		}
	} 

	/**
	 * �����̳�
	 * @param configUrl �����ļ�·��
	 * @param config �����ļ�
	 * @return IBLNFactoryConfig config
	 * @throws IOException
	 */
	protected IBLNFactoryConfig parseExtends(String configUrl, IBLNFactoryConfig config) throws IOException{
		
		//����̳н���
		IExtendsParser extendsParser = new ExtendsParser(this, configUrl, config);
		
		//ִ�н���
		config = extendsParser.parse();		
		extendsParser = null;
		
		return config;
	}
	
	/**
	 * ��������
	 * @param configUrl
	 * @param config
	 * @return
	 * @throws IOException
	 */
	protected IBLNFactoryConfig parseMixin(String configUrl, IBLNFactoryConfig config) throws IOException{
		
		//��ȡ�������
		IMixinParser mixinParser = new MixinParser(this, configUrl, config);
		
		//ִ�н���
		config = mixinParser.parse();
		mixinParser = null;
		
		//��������
		return config;
	}
	
	/**
	 * ��ȡ�����ļ�����������
	 */
	protected String[] parseAncestorNames(String configUrl){

		List<String> ancestors = new ArrayList<String>();
		
		//2.�������������ļ�
		String parentConfigUrl = configUrl;
		while (!StringUtils.isEmpty(parentConfigUrl)){
			
			//��ȡ���������ļ�
			IBLNFactoryConfig parentConfig = this.loadConfig(parentConfigUrl);
						
			//��Ӹ�������·����
			ancestors.add(0, parentConfig.getFactoryName());
			
			//��ȡ��������·��
			parentConfigUrl = parentConfig.getParentConfigUrl();
		}
		
		return ancestors.toArray(new String[]{});
	}
	
	/**
	 * ���������ļ�����
	 * @param configUrl
	 * @return
	 */
	protected IBLNFactoryConfig loadConfig(String configUrl){
		
		//�������м������������ö���
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
