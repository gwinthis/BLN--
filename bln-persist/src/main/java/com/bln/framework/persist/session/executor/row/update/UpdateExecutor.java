/**
 * @author gengw
 * Created on Apr 26, 2012
 */
package com.bln.framework.persist.session.executor.row.update;

import java.io.IOException;
import java.sql.SQLException;

import com.bln.framework.persist.session.executor.row.SessionExecutorRowBase;
import com.bln.framework.persist.sql.entity.common.ICommonSqlEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * <p>会话更新执行器</p>
 * 该对象只可以更新数据库信息
 */
public class UpdateExecutor extends SessionExecutorRowBase<UpdateExecutor> implements IUpdateExecutor{
	

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.update.IUpdateExecutor#execute(com.bln.framework.persist.sql.entity.common.ICommonSqlEntity)
	 */
	public int execute(ICommonSqlEntity sqlEntity) throws SQLException, IOException {

		//1.校验参数
		Assert.paramIsNotNull(sqlEntity, "sqlEntity");

		//2.执行更新操作
		int updateCount = this.jdbcTemplate.update(conn, sqlEntity.getSqlInfo(), sqlEntity.getParams());
		
		//3.返回结果
		return updateCount;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.SessionBase#newInstance()
	 */
	@Override
	protected UpdateExecutor newInstance() {
		return new UpdateExecutor();
	}


}
