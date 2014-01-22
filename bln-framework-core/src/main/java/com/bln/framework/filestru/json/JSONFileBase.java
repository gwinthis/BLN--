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
	 * 根对象
	 */
	protected JSONObject document = null;
	
	/**
	 * 构造函数
	 */
	public JSONFileBase(){
		
	}
	
	/**
	 * 构造函数
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
		
		//读取根节点
		try {
			this.document = JSON.parseObject(new String(fileData, this.encoding));
		} catch (UnsupportedEncodingException e) {
			ParseFileException pfe = new ParseFileException();
			pfe.initCause(e);
			throw pfe;
		}
		
		//初始化报文结构
		initStru();
		
		//保存原始报文数据
		this.fileData = fileData;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#buildFile()
	 */
	public void initFile(){
		
		//初始化Document
		this.document = new JSONObject();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#writeToBytes()
	 */
	public byte[] writeToBytes() throws IOException{
		
		if(this.document == null){
			return null;
		}
		
		//生成JSON字符串
		String jsonString = this.toString();
		
		//生成JSON数组
		byte[] jsonBytes = jsonString.getBytes(this.encoding);
		
		//返回JSON数组
		return jsonBytes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		//生成JSON字符串
		String jsonString = this.document.toJSONString();
		
		//返回字符串
		return jsonString;
	}
	
	/**
	 * 在指定节点下删除第一个名为entityNm的值的节点
	 * @param element 指定节点
	 * @param entityNm 要删除节点的名
	 * @return 是否删除成功
	 */
	protected boolean removeFirstElement(JSONObject element, String entityNm){
		
		//1.校验参数
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.查找要删除的节点
		JSONObject removeElement = element.getJSONObject(entityNm);
		
		//3.删除节点
		if(removeElement == null){
			return true;
		}else{
			return element.remove(removeElement) != null;
		}
		
	}
	/**
	 * 获得一个节点下的实体值
	 * @param element 从该节点中获取值
	 * @param entityNm 标签名称
	 * @return 实体值
	 */
	protected String getTxtOfElement(JSONObject element, String entityNm){
		
		//1.校验参数
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.获取entityNm的值
		return element.getString(entityNm);
		
	}
	
	/**
	 * 获得一个节点下的实体值
	 * @param owner 从该节点中获取值
	 * @param entityNm 标签名称
	 * @return 实体值
	 */
	protected void setTxtOfElement(JSONObject owner, String entityNm, String entityVal){
		
		//1.校验参数
		Assert.paramIsNotNull(owner, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
				
		//2.设置实体值
		owner.put(entityNm, entityVal);
	}
	
	/**
	 * 获取一个节点下的单个实体的第一行数据
	 * @param element 从该节点获取实体的第一行数据
	 * @param entityNm 实体名称
	 * @return 实体的第一行数据
	 */
	protected T getEntityOfElement(JSONObject element, String entityNm){
		
		//1.获得一个实体的数据
		List<T> entitys = getEntitysOfElement(element, entityNm);
		
		//2.获得该实体中的第一行数据
		T entity = null;
		if(entitys != null && !entitys.isEmpty()){
			entity = entitys.get(0);
		}
		
		//3.返回实体的第一行数据
		return entity;
	}
	
	/**
	 * 获取一个节点下的单个实体的所有数据
	 * @param element 从该节点获取配置信息
	 * @param entityNm 实体名称
	 * @return 单个实体的数据
	 */
	protected List<T> getEntitysOfElement(JSONObject element, String entityNm){
		
		//获得一个实体的数据
		Map<String, List<T>> entitys = getAllEntityOfElement(element, true, entityNm);
		
		//返回配置信息
		return entitys == null ? null : entitys.get(entityNm);
	}
	
	/**
	 * 获取一个节点下的所有实体的数据
	 * @param element 从该节点获取配置信息
	 * @return element下的所有实体的数据
	 */
	protected Map<String, List<T>> getAllEntityOfElement(JSONObject element){
		 return getAllEntityOfElement(element, false);
	}
	 
	/**
	 * 获取一个节点下的多个实体的数据
	 * @param element 从该节点获取配置信息
	 * @param isContain 包含过滤还是排除过滤
	 * @param entityNms 所过滤的实体名称
	 * @return 多个实体的数据
	 */
	protected Map<String, List<T>> getAllEntityOfElement(JSONObject element, boolean isContain, String...entityNms){
		
		//如果包含空的实体名称，返回空
		if(isContain && (entityNms == null || entityNms.length <= 0)){
			return null;
		}
		
		//1.校验参数
		Assert.paramIsNotNull(element, "element");
		//Assert.paramIsNotNull(entityNms, "entityNm");
		
		//2.获得该元素下的子元素
		
		//2.1判断是否取一个配置信息
		boolean isOneEntity = isContain && entityNms.length == 1;
		
		//判断是否获取所有的配置信息
		boolean isAllEntity = entityNms.length == 0;

		//2.2获得配置信息		
		Map<String, Object> elements = new HashMap<String, Object>();;
		if(isOneEntity){
			String name = entityNms[0];
			elements.put(name, element.get(name));
		}else{
			elements = element;
		}
		
		//2.3如果子节点为空，直接返回
		if(elements == null || elements.size() <= 0){
			return null;
		}
		
		//3.生成行对象结果集
		
		//所有实体的集合
		Map<String, List<T>> entityMaps = new HashMap<String, List<T>>();
				
		//添加到配置信息集合中
		for (Map.Entry<String, Object> e : elements.entrySet()){
			
			//只处理JSONObject或JSONArray类型
			Object value = e.getValue();
			if(!(value instanceof JSONObject || value instanceof JSONArray)){
				continue;
			}
			
			//获得元素名称
			String eleName = e.getKey();
			
			//如果不是请求一个实体并且不是请求所有实体，判断节点名称是否在请求范围内，如果不是不处理。
			if(!isOneEntity && !isAllEntity){
				int idx = ArrayUtils.indexOf(entityNms, eleName);

				//如果包含操作, idx < 0的记录不处理； 如果不是包含操作，处理idx >= 0 的记录不处理
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
			
			//生成行实体结果集
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
		
		//返回结果集
		return entityMaps;
	}
	
	/**
	 * 把元素转换成实体信息
	 * @param element
	 * @return
	 */
	protected  abstract T element2Entity(JSONObject element);

}
