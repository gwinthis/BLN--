/**
 * @author gengw
 * Created at 2013-10-21
 */
package com.bln.framework.persist.sql.entity.common;

import java.util.List;

import com.bln.framework.persist.sql.entity.SqlEntity;

/**
 * SQL实体可实例化对象
 */
public class CommonSqlEntity extends SqlEntity<CommonSqlEntity> implements ICommonSqlEntity{

	public CommonSqlEntity() {
		super();
	}

	public CommonSqlEntity(String sqlInfo, List<Object> params, String[] tableNames) {
		super(sqlInfo, params, tableNames);
	}

	@Override
	protected CommonSqlEntity newInstance() {
		return new CommonSqlEntity();
	}

}
