/**
 * @author gengw
 * Created on May 16, 2012
 */
package com.bln.framework.persist.tablestru.util;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;
import com.bln.framework.persist.tablestru.exception.TableStruConsistentException;

/**
 * ��ṹ������
 */
public class TableStruUtil {
	
	/**
	 * �ڱ�ṹ�в���ָ���ӱ�����
	 * @param tableStru �ڸñ�ṹ�в���
	 * @param childTableName Ҫ���ҵ��ӱ����
	 * @return �ӱ��Ӧ����������û�ҵ����ؿ�
	 */
	public static IForeignKey findForeignKey(ITableStru tableStru, String childTableName){
		
		IForeignKey foreignKey = null;
		IForeignKey[] foreignKeys = tableStru.getFks();
		if(foreignKeys != null && foreignKeys.length > 0){
			for ( IForeignKey fk : foreignKeys){
				if(childTableName.equals(fk.getRefTableName())){
					foreignKey = fk;
					break;
				}
			}
		}
		
		if(foreignKey == null){
			TableStruConsistentException tsce = new TableStruConsistentException(" it does not found childTableName " + childTableName + " in tableStru!");
			tsce.setContextValue("tableStru", tableStru);
			throw tsce;
		}
		
		return foreignKey;
	}
	
	/**
	 * У���ж�����Ӽ��Ƿ�Ϊ����ӱ�
	 * @param tableStru �ڸñ�ṹ�в���
	 * @param childTableName Ҫ���ҵ��ӱ����
	 * @return true �ӱ�false �����ӱ�
	 */
	public static boolean isChildTable(ITableStru tableStru, String childTableName){
		
		boolean found = false;

		IForeignKey[] foreignKeys = tableStru.getFks();
		if(foreignKeys != null && foreignKeys.length > 0){
			for ( IForeignKey fk : foreignKeys){
				if(childTableName.equals(fk.getRefTableName())){
					found = true;
					break;
				}
			}			
		}
		
		return found;
	}
	
}
