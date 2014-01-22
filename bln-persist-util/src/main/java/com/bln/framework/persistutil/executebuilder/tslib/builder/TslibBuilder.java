/**
 * @author gengw
 * Created on May 25, 2012
 */
package com.bln.framework.persistutil.executebuilder.tslib.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.xml.BLNFactoryXmlConfig;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persistutil.executebuilder.ExecuteBuilderBase;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * Oracle的XML表结构库生成器
 */
public class TslibBuilder extends ExecuteBuilderBase{

	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(TslibBuilder.class);
	
	/**
	 * 构造函数
	 */
	public TslibBuilder(){
		executeDesc = "build table structure libary property!";
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#build()
	 */
	public void build() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		
		List<ITaskConfigEntity> params = this.getExecuteEnity().getSubConfigEntitys();
		Assert.isTrue(params != null && params.size() > 0, " it not found param element in the buildTableStruLib execute!");
		
		//查找工厂路径
		ITaskConfigEntity param = this.findParam(params, "persistfactoryurl");
		Assert.isTrue(param != null);
		
		//获得路径
		String fileUrl = param.getAttr(ITaskConfig.ATTR_VALUE);
		byte[] bytes = IOUtils.toByteArray(new FileInputStream(fileUrl));
		
		//构造配置文件
		BLNFactoryXmlConfig config = new BLNFactoryXmlConfig();
		config.readFromData(bytes);
		
		//查找TableStruLib属性元素
		Element root = config.getObjectsElement();
		
		Element tablestrulibEle = findElementByName(root, IBLNFactoryConfig.NODE_CATEGORY_NM, "tablestrulib");
		Element tablestrulibDefaultEle = findElementByName(tablestrulibEle, IBLNFactoryConfig.NODE_OBJECT_NM, "Default");
		Element instanceMapEle = this.findElementByName(tablestrulibDefaultEle, IBLNFactoryConfig.NODE_PROPERTY_NM, "instanceMap");
		
		//构造节点
		ITaskConfigEntity classParam = this.findParam(params, "builderclass");
		Assert.isTrue(classParam != null);
		String builderClass = classParam.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		ITaskConfigEntity configpathParam = this.findParam(params, "configpath");
		Assert.isTrue(configpathParam != null);
		String configPath = configpathParam.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		if(!configPath.endsWith("/")){
			configPath += "/";
		}
		
		buildTable(instanceMapEle, builderClass, configPath);
		
		//输出配置文件
		File file = new File(fileUrl);
		FileUtils.writeByteArrayToFile(file, config.writeToBytes());
		
		log.info("build table structure in the file " + fileUrl + " sucessufully!");
	}
		
	/**
	 * @param instanceMapEle
	 * @param builderClass
	 * @param configPath
	 */
	protected void buildTable(Element instanceMapEle, String builderClass, String configPath){
		
		for ( IRow row : tables){
			String tableName = row.getValue("TABLE_NAME");
			
			Element key = instanceMapEle.addElement("prop");
			key.addAttribute(IBLNFactoryConfig.ATTR_NAME, "key");
			key.addAttribute(IBLNFactoryConfig.ATTR_VALUE, tableName);
			
			Element value = instanceMapEle.addElement("prop");
			value.addAttribute(IBLNFactoryConfig.ATTR_NAME, "value");
			value.addAttribute(IBLNFactoryConfig.ATTR_CREATOR, "builder");
			value.addAttribute(IBLNFactoryConfig.ATTR_CLASS, builderClass);
			value.addAttribute(IBLNFactoryConfig.ATTR_VALUE, configPath + tableName + ".xml" );
		}
	}
	
	protected Element findElementByName(Element root, String nodeType, String name){
		List<Element> eles = root.elements(nodeType);
		
		Element target = null;
		for ( Element e: eles){
			if(name.equals(e.attributeValue(IBLNFactoryConfig.ATTR_NAME))){
				target = e;
				break;
			}
		}
		return target;
	}
	
	
}
