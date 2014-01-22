/**
 * @author gengw
 * Created on Jul 16, 2012
 */
package com.bln.framework.filestru.json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.bln.framework.filestru.FileStruBase;
import com.bln.framework.filestru.exception.ParseFileException;
import com.bln.framework.util.asserter.Assert;

/**
 * 
 */
public abstract class JSONFileBase<T> extends FileStruBase{

	/**
	 * ������
	 */
	protected JSONObject document = null;
	
	/**
	 * ���캯��
	 */
	public JSONFileBase(){
		
	}
	
	/**
	 * ���캯��
	 * @param data is the <code>byte[]</code> to read from
	 * @throws ParseFileException if an error occurs during parsing
	 */
	public JSONFileBase(byte[] data){
		this.readFromData(data);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#readFromData(byte[])
	 */
	public void readFromData(byte[] fileData){
		
		//��ȡ���ڵ�
		try {
			this.document = JSON.parseObject(new String(fileData, this.encoding));
		} catch (UnsupportedEncodingException e) {
			ParseFileException pfe = new ParseFileException();
			pfe.initCause(e);
			throw pfe;
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
		this.document = new JSONObject();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#writeToBytes()
	 */
	public byte[] writeToBytes() throws IOException{
		
		if(this.document == null){
			return null;
		}
		
		//����JSON�ַ���
		String jsonString = this.toString();
		
		//����JSON����
		byte[] jsonBytes = jsonString.getBytes(this.encoding);
		
		//����JSON����
		return jsonBytes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		//����JSON�ַ���
		String jsonString = this.document.toJSONString();
		
		//�����ַ���
		return jsonString;
	}
	
	/**
	 * ��ָ���ڵ���ɾ����һ����ΪentityNm��ֵ�Ľڵ�
	 * @param element ָ���ڵ�
	 * @param entityNm Ҫɾ���ڵ����
	 * @return �Ƿ�ɾ���ɹ�
	 */
	protected boolean removeFirstElement(JSONObject element, String entityNm){
		
		//1.У�����
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.����Ҫɾ���Ľڵ�
		JSONObject removeElement = element.getJSONObject(entityNm);
		
		//3.ɾ���ڵ�
		if(removeElement == null){
			return true;
		}else{
			return element.remove(removeElement) != null;
		}
		
	}
	/**
	 * ���һ���ڵ��µ�ʵ��ֵ
	 * @param element �Ӹýڵ��л�ȡֵ
	 * @param entityNm ��ǩ����
	 * @return ʵ��ֵ
	 */
	protected String getTxtOfElement(JSONObject element, String entityNm){
		
		//1.У�����
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.��ȡentityNm��ֵ
		return element.getString(entityNm);
		
	}
	
	/**
	 * ���һ���ڵ��µ�ʵ��ֵ
	 * @param owner �Ӹýڵ��л�ȡֵ
	 * @param entityNm ��ǩ����
	 * @return ʵ��ֵ
	 */
	protected void setTxtOfElement(JSONObject owner, String entityNm, String entityVal){
		
		//1.У�����
		Assert.paramIsNotNull(owner, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
				
		//2.����ʵ��ֵ
		owner.put(entityNm, entityVal);
	}
	
	/**
	 * ��ȡһ���ڵ��µĵ���ʵ��ĵ�һ������
	 * @param element �Ӹýڵ��ȡʵ��ĵ�һ������
	 * @param entityNm ʵ������
	 * @return ʵ��ĵ�һ������
	 */
	protected T getEntityOfElement(JSONObject element, String entityNm){
		
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
	protected List<T> getEntitysOfElement(JSONObject element, String entityNm){
		
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
	protected Map<String, List<T>> getAllEntityOfElement(JSONObject element){
		 return getAllEntityOfElement(element, false);
	}
	 
	/**
	 * ��ȡһ���ڵ��µĶ��ʵ�������
	 * @param element �Ӹýڵ��ȡ������Ϣ
	 * @param isContain �������˻����ų�����
	 * @param entityNms �����˵�ʵ������
	 * @return ���ʵ�������
	 */
	protected Map<String, List<T>> getAllEntityOfElement(JSONObject element, boolean isContain, String...entityNms){
		
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
		Map<String, Object> elements = new HashMap<String, Object>();;
		if(isOneEntity){
			String name = entityNms[0];
			elements.put(name, element.get(name));
		}else{
			elements = element;
		}
		
		//2.3����ӽڵ�Ϊ�գ�ֱ�ӷ���
		if(elements == null || elements.size() <= 0){
			return null;
		}
		
		//3.�����ж�������
		
		//����ʵ��ļ���
		Map<String, List<T>> entityMaps = new HashMap<String, List<T>>();
				
		//��ӵ�������Ϣ������
		for (Map.Entry<String, Object> e : elements.entrySet()){
			
			//ֻ����JSONObject��JSONArray����
			Object value = e.getValue();
			if(!(value instanceof JSONObject || value instanceof JSONArray)){
				continue;
			}
			
			//���Ԫ������
			String eleName = e.getKey();
			
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
			
			//������ʵ������
			List<T> entitys = null; 
			
			if(value instanceof JSONObject ){
				
				entitys = new ArrayList<T>(1);
				
				T entity = element2Entity((JSONObject)value);
				entitys.add(entity);
				
			}else{
				
				JSONArray jsonArray = (JSONArray)value;
				entitys = new ArrayList<T>(jsonArray.size());
				
				for ( Object o : jsonArray){
					if(o instanceof JSONObject ){
						
						T entity = element2Entity((JSONObject)o);
						entitys.add(entity);
					}
				}
			}
			entityMaps.put(eleName, entitys);
			
		}
		
		//���ؽ����
		return entityMaps;
	}
	
	/**
	 * ��Ԫ��ת����ʵ����Ϣ
	 * @param element
	 * @return
	 */
	protected  abstract T element2Entity(JSONObject element);

}
