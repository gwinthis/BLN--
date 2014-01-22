/**
 * @author gengw
 * Created on May 16, 2012
 */
package com.bln.framework.persist.tablestru.util;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;
import com.bln.framework.persist.tablestru.exception.TableStruConsistentException;

/**
 * 表结构工具类
 */
public class TableStruUtil {
	
	/**
	 * 在表结构中查找指定子表的外键
	 * @param tableStru 在该表结构中查找
	 * @param childTableName 要查找的子表表名
	 * @return 子表对应的外键，如果没找到返回空
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
	 * 校验行对象的子集是否为表的子表
	 * @param tableStru 在该表结构中查找
	 * @param childTableName 要查找的子表表名
	 * @return true 子表，false 不是子表
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
