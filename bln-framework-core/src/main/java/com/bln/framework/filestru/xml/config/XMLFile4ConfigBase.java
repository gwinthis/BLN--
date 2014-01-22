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
 * XML���͵��ļ�����ϵͳ���õĴ洢
 * @param <T> ����ʵ�������
 */
public abstract class XMLFile4ConfigBase <T extends IConfigEntity<T>> extends XMLFileBase<T>{
	
	public XMLFile4ConfigBase(){
		super();
		this.writeFormatStrategy = new PrettyWriteFormatStrategy();
	}
	
	/**
	 * ��Ԫ��ת����������Ϣ
	 * @param element
	 * @return
	 */
	protected T element2Entity(Element element){
		
		//�������Ϊ�գ�ֱ�ӷ���
		if(element == null){
			return null;
		}
		
		//1.�������ö���
		T configEntity = this.parseElement(element);
			
		//2.���������
		List <Element> subElements = element.elements();
		this.parseSubElements(configEntity, subElements);
		
		//3.��������
		return configEntity;
	}
	
	/**
	 * ����Ԫ�ض����������Ϣ
	 * @return ���ö���
	 */
	protected T parseElement(Element element){
		
		//1.�������ö���
		T configEntity = newConfigEntity(element);
		
		//2.��������
		configEntity.setType(element.getName());
		
		//3.����ֵ
		configEntity.setTxtVal(element.getText());
		
		//4.����������Ϣ
		List <Attribute> attrs = element.attributes();
		if(attrs != null && attrs.size() >= 0){
			for(Attribute attr : attrs){
				configEntity.setAttr(attr.getName(), attr.getValue());
			}
		}
		
		//5.�������ö���
		return configEntity;
	}
	
	/**
	 * ������Ԫ��������Ϣ
	 * @param configEntity ���ö���
	 * @param subElements ��Ԫ�ؼ���
	 */
	protected void parseSubElements(T configEntity, List <Element> subElements){
		
		//���������
		if(subElements != null && !subElements.isEmpty()){
			
			//ѭ�����������
			for (Element e : subElements){
				
				//���������ö���
				T subConfig = element2Entity(e);
				
				//���������ö���
				procSubConfig(configEntity, subConfig);
			}
		}
	}
	
	/**
	 * ���ʵ�����õ�Ԫ����
	 * @param parentElement ����Ԫ��
	 * @param entitys ʵ������
	 */
	protected void addEntitys2Element(Element parentElement, List<T> entitys){
		if(entitys != null && entitys.size() > 0){
			for ( T entity : entitys ){
				parentElement.add(entity2Element(entity));
			}
		}
	}
	
	/**
	 * ����ʵ���������Ԫ��
	 * @param configEntity ����ʵ��
	 * @return Ԫ��
	 */
	protected Element entity2Element(T configEntity){
		
		//1.�½�Ԫ��
		Element e = DocumentHelper.createElement(configEntity.getType());
		
		//2.����Text
		String txtValue = configEntity.getTxtVal();
		if(txtValue != null){
			e.setText(txtValue);
		}
		
		
		//3.��������
		Map<String, String> attrMap = configEntity.getAttrMap();
		for (Map.Entry<String, String> attrEntry: attrMap.entrySet()){
			e.addAttribute(attrEntry.getKey(), attrEntry.getValue());
		}
		
		//4.������Ԫ��
		List<T> subConfigEntitys = configEntity.getSubConfigEntitys();
		for ( T entity : subConfigEntitys){
			e.add(entity2Element(entity));
		}
		
		//5.����Ԫ��
		return e;
	}
	
	/**
	 * ��������ʵ�����
	 * @return �����µ�����ʵ�����
	 */
	protected abstract T newConfigEntity(Object oriElement);

	/**
	 * ���������ö���
	 * @param configEntity ��ǰ����
	 * @param subConfig �����ö���
	 */
	protected abstract void procSubConfig(T configEntity, T subConfig);
}
