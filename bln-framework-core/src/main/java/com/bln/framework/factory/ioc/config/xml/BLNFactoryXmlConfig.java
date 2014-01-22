/**
 * @author gengw
 * created at 2012-03-15
 */
package com.bln.framework.factory.ioc.config.xml;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.bln.framework.exception.UnsupportedException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.BLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.filestru.xml.config.XMLFile4ConfigBase;
//import com.bln.framework.filestru.xml.strategy.NormalWriteFormatStrategy;
import com.bln.framework.filestru.xml.strategy.PrettyWriteFormatStrategy;
import com.bln.framework.util.asserter.Assert;
import com.bln.framework.util.listfrompackage.ResourceUtils;

/**
 * IOC�����������ļ�
 */
public class BLNFactoryXmlConfig extends XMLFile4ConfigBase<IBLNFactoryConfigEntity> implements IBLNFactoryXmlConfig{
	
	/**
	 * objects�ڵ�
	 * �ýڵ�Ϊ���ڵ�
	 */
	protected Element objects = null;
	
	/**
	 * ���ȹ�������
	 */
	public String[] ancestorNames = null;

	/**
	 * ���캯��
	 */
	public BLNFactoryXmlConfig(){
		super();
		this.encoding = "UTF-8";
		this.writeFormatStrategy = new PrettyWriteFormatStrategy();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.FileStruBase#initStru()
	 */
	@Override
	protected void initStru(){
		
		//��ȡ���ڵ�
		this.objects = this.document.getRootElement();
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public String getFactoryName(){
		
		IBLNFactoryConfigEntity configEntity = this.getPropertyOfObjects();
		String factoryName = configEntity.getAttr(IBLNFactoryConfig.ATTR_NAME);
		
		return factoryName;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.IFactoryConfig#getAllCatogrys()
	 */
	public Map<String, List<IBLNFactoryConfigEntity>> getAllConfigEntityOfObjs(){
		return this.getAllEntityOfElement(objects);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.IBLNFactoryConfig#getAllEntityOfObject()
	 */
	public Map<String, List<IBLNFactoryConfigEntity>> getAllEntityOfObject(){
		return this.getAllEntityOfElement(objects, true, IBLNFactoryConfig.NODE_OBJECT_NM);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.xml.IIocFactoryXmlConfig#getPropertyOfObjs()
	 */
	public IBLNFactoryConfigEntity getPropertyOfObjects(){
		return this.parseElement(objects);
	}
	
	/**
	 * @return the objects
	 */
	public Element getObjectsElement() {
		return objects;
	}
		
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.IBLNFactoryConfig#hasParent()
	 */
	public boolean hasParent(){
		String parentConfigUrl = getParentConfigUrl();
		return !StringUtils.isEmpty(parentConfigUrl);
	}
			
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.xml.IBLNFactoryXmlConfig#getParentConfigUrl()
	 */
	public String getParentConfigUrl(){
		
		IBLNFactoryConfigEntity configEntity = getPropertyOfObjects();
		String parentConfigUrl = configEntity.getAttr(IBLNFactoryConfig.ATTR_EXTENDS);
		
		return parentConfigUrl;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.xml.IBLNFactoryXmlConfig#getMixinConfigUrls()
	 */
	public String[] getMixinConfigUrls(){
		
		IBLNFactoryConfigEntity configEntity = getPropertyOfObjects();
		String mixinConfigUrl = configEntity.getAttr(IBLNFactoryConfig.ATTR_MIXIN);
		
		String[] mixins = null;
		if(mixinConfigUrl != null){
			mixinConfigUrl = mixinConfigUrl.replaceAll("\\s+", "");
			mixins = mixinConfigUrl.split(",");
		}
		
		return mixins; 
	}

	/**
	 * @return the ancestorNames
	 */
	public String[] getAncestorNames() {
		return ancestorNames;
	}

	/**
	 * @param ancestorNames the ancestorNames to set
	 */
	public void setAncestorNames(String[] ancestorNames) {
		this.ancestorNames = ancestorNames;
	}
	
	/**
	 * �Ƿ���ָ������������
	 * @param factoryName
	 * @return
	 */
	public boolean isTypeOf(String factoryName){
		
		if(StringUtils.isEmpty(factoryName)){
			return false;
		}
		
		boolean found = false;
		for ( String name: ancestorNames){
			if(name.equals(factoryName)){
				found = true;
				break;
			}
		}
		
		return found;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.IBLNFactoryConfig#expand(com.bln.framework.factory.ioc.config.IBLNFactoryConfig)
	 */
	public void expand(IBLNFactoryConfig theConfig, ExpandType expandType){
		
		Assert.paramIsNotNull(theConfig, "config");
		
		if(theConfig instanceof BLNFactoryXmlConfig){
			BLNFactoryXmlConfig xmlConfig = (BLNFactoryXmlConfig)theConfig;
			
			ExpandConfig expandConfig = new ExpandConfig();
			expandConfig.overrideElement(objects, xmlConfig.getObjectsElement(), expandType);
			
		}else{
			throw new UnsupportedException();
		}
	}
	
	/**
	 * ͨ�������ļ���Դ·���������ö���
	 * @param configUrl �����ļ�·��
	 * @return IBLNFactoryConfig
	 * @throws IOException
	 */
	protected IBLNFactoryConfig loadConfig(String configUrl) throws IOException{
		
		//1.�������ö���
		IBLNFactoryConfig parentConfig = null;
		if(StringUtils.isEmpty(configUrl)){
			
			byte[] bytes = ResourceUtils.readFileInPackage(configUrl);
			
			parentConfig = new BLNFactoryXmlConfig();
			parentConfig.readFromData(bytes);
		}
		
		//2.���ؼ��غ�����ö���
		return parentConfig;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.config.XMLFile4ConfigBase#newConfig()
	 */
	@Override
	protected IBLNFactoryConfigEntity newConfigEntity(Object oriElement) {
		
		return new BLNFactoryConfigEntity(oriElement);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.config.XMLFileConfigBase#procSubConfig(com.bln.framework.config.ConfigEntity, com.bln.framework.config.ConfigEntity)
	 */
	@Override
	protected void procSubConfig(IBLNFactoryConfigEntity config, IBLNFactoryConfigEntity subConfig) {
		if(NODE_PROPERTY_NM.equals(subConfig.getType())){
			config.setProperty(subConfig.getAttr(ATTR_NAME), subConfig);
		}else{
			config.addSubEntity(subConfig);
		}
		config.addSubConfigEntity(subConfig);
	}
			
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		String theFactoryName = this.getFactoryName();
		result = prime * result
				+ ((theFactoryName == null) ? 0 : theFactoryName.hashCode());
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
		
		String theFactoryName = this.getFactoryName();
		final IBLNFactoryConfig other = (IBLNFactoryConfig) obj;
		String otherFactoryName = other.getFactoryName();
		
		if (theFactoryName == null) {
			if (otherFactoryName != null)
				return false;
		} else if (!theFactoryName.equals(otherFactoryName))
			return false;
		return true;
	}
	

	
	/**
	 * ��չ����
	 */
	public class ExpandConfig{
				
		/**
		 * ��Դ�ڵ㸲�ǵ�Ŀ�Ľڵ���
		 * @param desElement �����ǵĽڵ�
		 * @param srcElement �Ѹýڵ㸲�ǵ�desElement
		 */
		protected void overrideElement(Element desElement, Element srcElement, ExpandType expandType){
			
			//1.���ǽڵ�
			String eleType = desElement.getName();
			if(NODE_OBJECT_NM.equals(eleType) || NODE_PROPERTY_NM.equals(eleType)){
				
				Element parentElement = desElement.getParent();
				parentElement.remove(desElement);
				
				Element srcCopy = srcElement.createCopy();
				if(ExpandType.OVERRIDE_TYPE_ONLYSUB.equals(expandType)){
					srcCopy.setAttributes(desElement.attributes());
					srcCopy.setText(desElement.getText());
				}
				parentElement.add(srcCopy);
				
				return;
				
			}else{
				if(ExpandType.OVERRIDE_TYPE_ALL.equals(expandType)){
					desElement.setAttributes(srcElement.attributes());
					desElement.setText(srcElement.getText());
				}
			}

			//desElement.
			//2.ѭ��Դ�ڵ㣬�������µĽڵ�
			List<Element> subSrcElements = srcElement.elements();
			List<Element> subDesElements = desElement.elements();
			
			for ( Element subSrcElement : subSrcElements){
				
				//����Ŀ���ӽڵ��еĵȼ۽ڵ㣬���û�ҵ���ӵ�Ŀ��ڵ��У����򸲸ǵȼ۽ڵ㡣
				Element equalElement = findEqualElement(subDesElements, subSrcElement);
				if(equalElement == null){
					desElement.add(subSrcElement.createCopy());
				}else{
					overrideElement(equalElement, subSrcElement, ExpandType.OVERRIDE_TYPE_ALL);
				}
			}
		}
		
		/**
		 * ���ҵȼ�Ԫ�أ����δ�ҵ����ؿ�
		 * @param elements 
		 * @param target
		 * @return
		 */
		private Element findEqualElement(List<Element> elements, Element target){
			
			//1.У�����
			if(elements == null || elements.size() <= 0){
				return null;
			}
			
			//2.��elements�в���target�ĵȼ�Ԫ��
			Element equalElement = null;
			for(Element e : elements){
				if(isEqualElement(e, target)){
					equalElement = e;
					break;
				}
			}
			
			//3.���صȼ۽ڵ�
			return equalElement;
		}
		
		/**
		 * <p>�ж������ڵ��Ƿ����</p>
		 * ���ݽڵ����ͺ������жϽڵ��Ƿ���ͬ
		 * @param ele1 Ԫ��1
		 * @param ele2 Ԫ��2
		 * @return �Ƿ����
		 */
		private boolean isEqualElement(Element ele1, Element ele2){
			
			//���ݽڵ����ͺ������жϽڵ��Ƿ���ͬ
			boolean equal = false;
			if(ele1.getName().equals(ele2.getName())){
				if( ele1.attributeValue(ATTR_NAME).equals(ele2.attributeValue(ATTR_NAME))){
					equal = true;
				}
			}
			
			return equal;
		}
	}
}
