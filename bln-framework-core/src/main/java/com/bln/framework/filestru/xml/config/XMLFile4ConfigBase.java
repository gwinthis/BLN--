/**
 * @author gengw
 * created on 2012-03-15
 */
package com.bln.framework.filestru.xml.config;

import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.bln.framework.config.entity.IConfigEntity;
import com.bln.framework.filestru.xml.XMLFileBase;
import com.bln.framework.filestru.xml.strategy.PrettyWriteFormatStrategy;

/**
 * XML类型的文件用于系统配置的存储
 * @param <T> 配置实体的类型
 */
public abstract class XMLFile4ConfigBase <T extends IConfigEntity<T>> extends XMLFileBase<T>{
	
	public XMLFile4ConfigBase(){
		super();
		this.writeFormatStrategy = new PrettyWriteFormatStrategy();
	}
	
	/**
	 * 把元素转换成配置信息
	 * @param element
	 * @return
	 */
	protected T element2Entity(Element element){
		
		//如果参数为空，直接返回
		if(element == null){
			return null;
		}
		
		//1.生成配置对象
		T configEntity = this.parseElement(element);
			
		//2.添加子配置
		List <Element> subElements = element.elements();
		this.parseSubElements(configEntity, subElements);
		
		//3.返回配置
		return configEntity;
	}
	
	/**
	 * 解析元素对象的配置信息
	 * @return 配置对象
	 */
	protected T parseElement(Element element){
		
		//1.生成配置对象
		T configEntity = newConfigEntity(element);
		
		//2.设置名称
		configEntity.setType(element.getName());
		
		//3.设置值
		configEntity.setTxtVal(element.getText());
		
		//4.设置属性信息
		List <Attribute> attrs = element.attributes();
		if(attrs != null && attrs.size() >= 0){
			for(Attribute attr : attrs){
				configEntity.setAttr(attr.getName(), attr.getValue());
			}
		}
		
		//5.返回配置对象
		return configEntity;
	}
	
	/**
	 * 解析子元素配置信息
	 * @param configEntity 配置对象
	 * @param subElements 子元素集合
	 */
	protected void parseSubElements(T configEntity, List <Element> subElements){
		
		//添加子配置
		if(subElements != null && !subElements.isEmpty()){
			
			//循环添加子配置
			for (Element e : subElements){
				
				//生成子配置对象
				T subConfig = element2Entity(e);
				
				//处理子配置对象
				procSubConfig(configEntity, subConfig);
			}
		}
	}
	
	/**
	 * 添加实体配置到元素中
	 * @param parentElement 父类元素
	 * @param entitys 实体配置
	 */
	protected void addEntitys2Element(Element parentElement, List<T> entitys){
		if(entitys != null && entitys.size() > 0){
			for ( T entity : entitys ){
				parentElement.add(entity2Element(entity));
			}
		}
	}
	
	/**
	 * 配置实体对象生成元素
	 * @param configEntity 配置实体
	 * @return 元素
	 */
	protected Element entity2Element(T configEntity){
		
		//1.新建元素
		Element e = DocumentHelper.createElement(configEntity.getType());
		
		//2.设置Text
		String txtValue = configEntity.getTxtVal();
		if(txtValue != null){
			e.setText(txtValue);
		}
		
		
		//3.设置属性
		Map<String, String> attrMap = configEntity.getAttrMap();
		for (Map.Entry<String, String> attrEntry: attrMap.entrySet()){
			e.addAttribute(attrEntry.getKey(), attrEntry.getValue());
		}
		
		//4.设置子元素
		List<T> subConfigEntitys = configEntity.getSubConfigEntitys();
		for ( T entity : subConfigEntitys){
			e.add(entity2Element(entity));
		}
		
		//5.返回元素
		return e;
	}
	
	/**
	 * 生成配置实体对象
	 * @return 返回新的配置实体对象
	 */
	protected abstract T newConfigEntity(Object oriElement);

	/**
	 * 处理子配置对象
	 * @param configEntity 当前对象
	 * @param subConfig 子配置对象
	 */
	protected abstract void procSubConfig(T configEntity, T subConfig);
}
