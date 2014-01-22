package com.bln.framework.persist.sql.entity.delete;

import com.bln.framework.persist.sql.entity.SqlEntity;

public class DeleteSqlEntity extends SqlEntity<IDeleteSqlEntity> implements IDeleteSqlEntity{

	@Override
	protected IDeleteSqlEntity newInstance() {
		return new DeleteSqlEntity();
	}

}
