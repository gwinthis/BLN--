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
 * <p>�Ự����ִ����</p>
 * �ö���ֻ���Ը������ݿ���Ϣ
 */
public class UpdateExecutor extends SessionExecutorRowBase<UpdateExecutor> implements IUpdateExecutor{
	

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.update.IUpdateExecutor#execute(com.bln.framework.persist.sql.entity.common.ICommonSqlEntity)
	 */
	public int execute(ICommonSqlEntity sqlEntity) throws SQLException, IOException {

		//1.У�����
		Assert.paramIsNotNull(sqlEntity, "sqlEntity");

		//2.ִ�и��²���
		int updateCount = this.jdbcTemplate.update(conn, sqlEntity.getSqlInfo(), sqlEntity.getParams());
		
		//3.���ؽ��
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
