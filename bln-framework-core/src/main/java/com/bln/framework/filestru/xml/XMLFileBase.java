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
 * XML EDI格式
 */
public abstract class XMLFileBase <T> extends FileStruBase implements IXMLFileStru{
	
	/**
	 * DOM内容
	 */
	protected Document document = null;
	
	/**
	 * 输出格式策略
	 */
	protected IWriteFormatStrategy writeFormatStrategy = null;
	
	/**
	 * 构造函数
	 */
	public XMLFileBase() {
		
	}
	
	/**
	 * 构造函数
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
				
		//生成读取对象
		SAXReader reader = new SAXReader();
		
		//设置字符集编码方式
		reader.setEncoding(this.encoding);
		
		//读取数据生成DOM
		InputStream bais = null;
		try{
			//从字节数组生成字节流
			bais = new ByteArrayInputStream ( fileData );
			
			//读取数据生成DOM
			try {
				this.document = reader.read(bais);
			} catch (DocumentException e) {
				ParseFileException pfe = new ParseFileException();
				pfe.initCause(e);
				throw pfe; 
			}

		}finally{
			
			//关闭输入流
			try {
				bais.close();
			} catch (IOException e) {
				ParseFileException pfe = new ParseFileException();
				pfe.initCause(e);
				throw pfe; 
			}
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
		this.document = DocumentFactory.getInstance().createDocument(encoding);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.IFileStru#writeToBytes()
	 */
	public byte[] writeToBytes() throws IOException{
		
		if(this.document == null){
			return null;
		}
		
		//建立XML格式
		OutputFormat xmlFormat = null;
		if(writeFormatStrategy != null){
			
			xmlFormat = writeFormatStrategy.createFormat(encoding);
			
		}else{
			
			//默认的处理方式
			xmlFormat = new OutputFormat("", false, encoding);
			xmlFormat.setPadText(true);
		}
		
		//定义输出字节流
		ByteArrayOutputStream ops = null;
		XMLWriter writer = null;
		try{
			
			//建立输出字节流
			ops = new ByteArrayOutputStream();
			
			//建立输出对象
			writer = new XMLWriter(ops, xmlFormat);
						
			//输出到字节流中
			writer.write(this.document);
			
			//结束输出
			writer.flush();
			ops.flush();
			
			//输出到字节数组中
			byte[] bytes = ops.toByteArray();
			
			//返回字节数组
			return bytes;
			
		}finally{
			
			//关闭输出
			writer.close();
			
			//关闭字节流
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
	 * 在指定节点下删除第一个名为entityNm的值的节点
	 * @param element 指定节点
	 * @param entityNm 要删除节点的名
	 * @return 是否删除成功
	 */
	protected boolean removeFirstElement(Element element, String entityNm){
		
		//1.校验参数
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.查找要删除的节点
		Element removeElement = element.element(entityNm);
		
		//3.删除节点
		if(removeElement == null){
			return true;
		}else{
			return element.remove(removeElement);
		}
		
	}
	/**
	 * 获得一个节点下的实体值
	 * @param element 从该节点中获取值
	 * @param entityNm 标签名称
	 * @return 实体值
	 */
	protected String getTxtOfElement(Element element, String entityNm){
		
		//1.校验参数
		Assert.paramIsNotNull(element, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.获取entityNm的值
		return element.elementText(entityNm);
		
	}
	
	/**
	 * 获得一个节点下的实体值
	 * @param owner 从该节点中获取值
	 * @param entityNm 标签名称
	 * @return 实体值
	 */
	protected void setTxtOfElement(Element owner, String entityNm, String entityVal){
		
		//1.校验参数
		Assert.paramIsNotNull(owner, "element");
		Assert.paramIsNotNull(entityNm, "entityNm");
		
		//2.赋值entityNm的值
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
	 * 获取一个节点下的单个实体的第一行数据
	 * @param element 从该节点获取实体的第一行数据
	 * @param entityNm 实体名称
	 * @return 实体的第一行数据
	 */
	protected T getEntityOfElement(Element element, String entityNm){
		
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
	protected List<T> getEntitysOfElement(Element element, String entityNm){
		
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
	protected Map<String, List<T>> getAllEntityOfElement(Element element){
		 return getAllEntityOfElement(element, false);
	}
	 
	/**
	 * 获取一个节点下的多个实体的数据
	 * @param element 从该节点获取配置信息
	 * @param isContain 包含过滤还是排除过滤
	 * @param entityNms 所过滤的实体名称
	 * @return 多个实体的数据
	 */
	protected Map<String, List<T>> getAllEntityOfElement(Element element, boolean isContain, String...entityNms){
		
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
		List<Element> elements = null;
		if(isOneEntity){
			elements = element.elements(entityNms[0]);
		}else{
			elements = element.elements();
		}
		
		//2.3如果子节点为空，直接返回
		if(elements == null || elements.size() <= 0){
			return null;
		}
		
		//3.生成行对象结果集
		
		//所有实体的集合
		Map<String, List<T>> entityMaps = new HashMap<String, List<T>>();
		
		//单个实体的数据集
		List<T> entitys = null;
		if(isOneEntity){
			entitys = new ArrayList<T>();
			entityMaps.put(entityNms[0], entitys);
		}
		
		//添加到配置信息集合中
		for (Element e : elements){
			String eleName = e.getName();
			
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
			
			//生成行对象
			T entity = element2Entity(e);
			
			//添加到配置信息集合中
			if(!isOneEntity){
				entitys = entityMaps.get(eleName);
				if(entitys == null){
					entitys = new ArrayList<T>();
					entityMaps.put(eleName, entitys);
				}
			}
			
			//添加行记录
			entitys.add(entity);
		}
		
		//返回结果集
		return entityMaps;
	}
	
	/**
	 * 把元素转换成实体信息
	 * @param element
	 * @return
	 */
	protected  abstract T element2Entity(Element element);

}
