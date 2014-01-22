/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.util.factory;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.factory.simple.SimpleFactory;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.lib.ITableStruLib;

/**
 * SqlTemplate�Ŀ����
 */
public class LibByTableStru<T> extends SimpleFactory<T> implements ILibByTableStru<T> {

	/**
	 * ͨ����ṹ����ʵ���Ĺ�����
	 */
	IBuilder1Step<T, ITableStru> instanceBuilder = null;
	
	/**
	 * ��ṹ�����
	 */
	ITableStruLib tableStruLib = null;

	/**
	 * @return the instanceBuilder
	 */
	public IBuilder1Step<T, ITableStru> getInstanceBuilder() {
		return instanceBuilder;
	}

	/**
	 * @param instanceBuilder the instanceBuilder to set
	 */
	public void setInstanceBuilder(IBuilder1Step<T, ITableStru> instanceBuilder) {
		this.instanceBuilder = instanceBuilder;
		readyGo();
	}

	/**
	 * @return the tableStruLib
	 */
	public ITableStruLib getTableStruLib() {
		return tableStruLib;
	}
	
	/**
	 * @param tableStruLib the tableStruLib to set
	 */
	public void setTableStruLib(ITableStruLib tableStruLib) {
		this.tableStruLib = tableStruLib;
		readyGo();
	}
	
	/**
	 * ��������Ѿ�׼������ôִ�г�ʼ������
	 */
	protected void readyGo(){
		if(tableStruLib != null && instanceBuilder != null){
			this.initAllInstance();
		}
	}
	
	/**
	 * ��ʼ������ʵ��
	 */
	protected void initAllInstance(){
		
		//1.�����ṹ��Ϊ�գ�ֱ�ӷ���
		if(tableStruLib == null || tableStruLib.getAllInstance() == null || tableStruLib.getAllInstance().isEmpty()){
			return;
		}
		
		//2.���ݱ�ṹ���ʼ��sqlt��ʵ����
		this.instanceMap = new HashMap<String, T>();
		Map<String, ITableStru> tableStruMap = tableStruLib.getAllInstance();
		for (Map.Entry<String, ITableStru> entry : tableStruMap.entrySet()){
			
			//����ʵ��
			T instance = instanceBuilder.build(entry.getValue());
			
			//��ӵ�ʵ������
			instanceMap.put(entry.getKey(), instance);
		}
	}
}
