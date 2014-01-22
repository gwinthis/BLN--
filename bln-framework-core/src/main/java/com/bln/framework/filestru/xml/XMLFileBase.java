/**
 * @author gengw
 * Created at 2011-02-24
 */
package com.bln.framework.filestru.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.bln.framework.filestru.FileStruBase;
import com.bln.framework.filestru.exception.ParseFileException;
import com.bln.framework.filestru.xml.strategy.IWriteFormatStrategy;
import com.bln.framework.util.asserter.Assert;

/**
 * XML EDI��ʽ
 */
public abstract class XMLFileBase <T> extends FileStruBase implements IXMLFileStru{
	
	/**
	 * DOM����
	 */
	protected Document document = null;
	
	/**
	 * �����ʽ����
	 */
	protected IWriteFormatStrategy writeFormatStrategy = null;
	
	/**
	 * ���캯��
	 */
	public XMLFileBase() {
		
	}
	
	/**
	 * ���캯��
	 * @param data is the <code>byte[]</code> to read from
	 * @throws DocumentException if an error occurs during parsing
	 */
	public XMLFileBase(byte[] data){
		this.readFromData(data);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#readFromData(byte[])
	 */
	public void readFromData(byte[] fileData){
				
		//���ɶ�ȡ����
		SAXReader reader = new SAXReader();
		
		//�����ַ������뷽ʽ
		reader.setEncoding(this.encoding);
		
		//��ȡ��������DOM
		InputStream bais = null;
		try{
			//���ֽ����������ֽ���
			bais = new ByteArrayInputStream ( fileData );
			
			//��ȡ��������DOM
			try {
				this.document = reader.read(bais);
			} catch (DocumentException e) {
				ParseFileException pfe = new ParseFileException();
				pfe.initCause(e);
				throw pfe; 
			}

		}finally{
			
			//�ر�������
			try {
				bais.close();
			} catch (IOException e) {
				ParseFileException pfe = new ParseFileException();
				pfe.initCause(e);
				throw pfe; 
			}
		}

		//��ʼ�����Ľṹ
		initStru();
		
		//����ԭʼ��������
		this.fileData = fileData;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#buildFile()
	 */
	public void initFile(){
		
		//��ʼ��Document
		this.document = DocumentFactory.getInstance().createDocument(encoding);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#writeToBytes()
	 */
	public byte[] writeToBytes() throws IOException{
		
		if(this.document == null){
			return null;
		}
		
		//����XML��ʽ
		OutputFormat xmlFormat = null;
		if(writeFormatStrategy != null){
			
			xmlFormat = writeFormatStrategy.createFormat(encoding);
			
		}else{
			
			//Ĭ�ϵĴ���ʽ
			xmlFormat = new OutputFormat("", false, encoding);
			xmlFormat.setPadText(true);
		}
		
		//��������ֽ���
		ByteArrayOutputStream ops = null;
		XMLWriter writer = null;
		try{
			
			//��������ֽ���
			ops = new ByteArrayOutputStream();
			
			//�����������
			writer = new XMLWriter(ops, xmlFormat);
						
			//������ֽ�����
			writer.write(this.document);
			
			//�������
			writer.flush();
			ops.flush();
			
			//������ֽ�������
			byte[] bytes = ops.toByteArray();
			
			//�����ֽ�����
			return bytes;
			
		}finally{
			
			//�ر����
			writer.close();
			
			//�ر��ֽ���
			ops.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String xml = "";
		try {
			byte[] bytes = writeToBytes();
			if(bytes != null){
				xml = new String(bytes, encoding);
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	/**
	 * ��ָ���ڵ���ɾ����һ����ΪentityNm��ֵ�Ľڵ�
	 * @param element ָ���ڵ�
	 * @param entityNm Ҫɾ���ڵ����
	 * @return �Ƿ�ɾ���ɹ�
	 */
	protected boolean removeFirstElement(Element element, String entityNm){
		
		//1.У�����
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.����Ҫɾ���Ľڵ�
		Element removeElement = element.element(entityNm);
		
		//3.ɾ���ڵ�
		if(removeElement == null){
			return true;
		}else{
			return element.remove(removeElement);
		}
		
	}
	/**
	 * ���һ���ڵ��µ�ʵ��ֵ
	 * @param element �Ӹýڵ��л�ȡֵ
	 * @param entityNm ��ǩ����
	 * @return ʵ��ֵ
	 */
	protected String getTxtOfElement(Element element, String entityNm){
		
		//1.У�����
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.��ȡentityNm��ֵ
		return element.elementText(entityNm);
		
	}
	
	/**
	 * ���һ���ڵ��µ�ʵ��ֵ
	 * @param owner �Ӹýڵ��л�ȡֵ
	 * @param entityNm ��ǩ����
	 * @return ʵ��ֵ
	 */
	protected void setTxtOfElement(Element owner, String entityNm, String entityVal){
		
		//1.У�����
		Assert.paramIsNotNull(owner, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.��ֵentityNm��ֵ
		if(entityVal == null){
			return;
		}
		
		Element ele = owner.element(entityNm);
		if(ele == null){
			ele = owner.addElement(entityNm);
		}
		ele.setText(entityVal);
	}
	
	/**
	 * ��ȡһ���ڵ��µĵ���ʵ��ĵ�һ������
	 * @param element �Ӹýڵ��ȡʵ��ĵ�һ������
	 * @param entityNm ʵ������
	 * @return ʵ��ĵ�һ������
	 */
	protected T getEntityOfElement(Element element, String entityNm){
		
		//1.���һ��ʵ�������
		List<T> entitys = getEntitysOfElement(element, entityNm);
		
		//2.��ø�ʵ���еĵ�һ������
		T entity = null;
		if(entitys != null && !entitys.isEmpty()){
			entity = entitys.get(0);
		}
		
		//3.����ʵ��ĵ�һ������
		return entity;
	}
	
	/**
	 * ��ȡһ���ڵ��µĵ���ʵ�����������
	 * @param element �Ӹýڵ��ȡ������Ϣ
	 * @param entityNm ʵ������
	 * @return ����ʵ�������
	 */
	protected List<T> getEntitysOfElement(Element element, String entityNm){
		
		//���һ��ʵ�������
		Map<String, List<T>> entitys = getAllEntityOfElement(element, true, entityNm);
		
		//����������Ϣ
		return entitys == null ? null : entitys.get(entityNm);
	}
	
	/**
	 * ��ȡһ���ڵ��µ�����ʵ�������
	 * @param element �Ӹýڵ��ȡ������Ϣ
	 * @return element�µ�����ʵ�������
	 */
	protected Map<String, List<T>> getAllEntityOfElement(Element element){
		 return getAllEntityOfElement(element, false);
	}
	 
	/**
	 * ��ȡһ���ڵ��µĶ��ʵ�������
	 * @param element �Ӹýڵ��ȡ������Ϣ
	 * @param isContain �������˻����ų�����
	 * @param entityNms �����˵�ʵ������
	 * @return ���ʵ�������
	 */
	protected Map<String, List<T>> getAllEntityOfElement(Element element, boolean isContain, String...entityNms){
		
		//��������յ�ʵ�����ƣ����ؿ�
		if(isContain && (entityNms == null || entityNms.length <= 0)){
			return null;
		}
		
		//1.У�����
		Assert.paramIsNotNull(element, "element");
		//Assert.paramIsNotNull(entityNms, "entityNm");
		
		//2.��ø�Ԫ���µ���Ԫ��
		
		//2.1�ж��Ƿ�ȡһ��������Ϣ
		boolean isOneEntity = isContain && entityNms.length == 1;
		
		//�ж��Ƿ��ȡ���е�������Ϣ
		boolean isAllEntity = entityNms.length == 0;

		//2.2���������Ϣ
		List<Element> elements = null;
		if(isOneEntity){
			elements = element.elements(entityNms[0]);
		}else{
			elements = element.elements();
		}
		
		//2.3����ӽڵ�Ϊ�գ�ֱ�ӷ���
		if(elements == null || elements.size() <= 0){
			return null;
		}
		
		//3.�����ж�������
		
		//����ʵ��ļ���
		Map<String, List<T>> entityMaps = new HashMap<String, List<T>>();
		
		//����ʵ������ݼ�
		List<T> entitys = null;
		if(isOneEntity){
			entitys = new ArrayList<T>();
			entityMaps.put(entityNms[0], entitys);
		}
		
		//��ӵ�������Ϣ������
		for (Element e : elements){
			String eleName = e.getName();
			
			//�����������һ��ʵ�岢�Ҳ�����������ʵ�壬�жϽڵ������Ƿ�������Χ�ڣ�������ǲ�����
			if(!isOneEntity && !isAllEntity){
				int idx = ArrayUtils.indexOf(entityNms, eleName);

				//�����������, idx < 0�ļ�¼������ ������ǰ�������������idx >= 0 �ļ�¼������
				if(isContain){
					if(idx < 0 ){
						continue;
					}
				}else{
					if(idx >= 0 ){
						continue;
					}
				}
			}
			
			//�����ж���
			T entity = element2Entity(e);
			
			//��ӵ�������Ϣ������
			if(!isOneEntity){
				entitys = entityMaps.get(eleName);
				if(entitys == null){
					entitys = new ArrayList<T>();
					entityMaps.put(eleName, entitys);
				}
			}
			
			//����м�¼
			entitys.add(entity);
		}
		
		//���ؽ����
		return entityMaps;
	}
	
	/**
	 * ��Ԫ��ת����ʵ����Ϣ
	 * @param element
	 * @return
	 */
	protected  abstract T element2Entity(Element element);

}
