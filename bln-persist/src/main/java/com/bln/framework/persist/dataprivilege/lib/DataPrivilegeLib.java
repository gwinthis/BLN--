/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.persist.dataprivilege.lib;

import java.util.Map;

import com.bln.framework.factory.simple.SimpleFactory;
import com.bln.framework.persist.dataprivilege.IDataPrivilege;

/**
 * 表数据权限对象库
 */
public class DataPrivilegeLib extends SimpleFactory<IDataPrivilege> implements IDataPrivilegeLib {

	/**
	 * @param objInstanceMap the objInstanceMap to set
	 */
	public void setInstanceMap(Map<String, IDataPrivilege> instanceMap) {
		this.instanceMap = instanceMap;
		
		if(this.instanceMap != null && this.instanceMap.size() > 0){
			for ( Map.Entry<String, IDataPrivilege> entry : this.instanceMap.entrySet()){
				String tableName = entry.getKey();
				IDataPrivilege dp = entry.getValue();
				
				dp.setTableName(tableName);
			}
		}
	}
}
