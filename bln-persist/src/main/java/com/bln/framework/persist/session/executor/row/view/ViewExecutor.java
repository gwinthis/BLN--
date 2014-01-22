/**
 * @author gengw
 * Created on Apr 26, 2012
 */
package com.bln.framework.persist.session.executor.row.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.executor.row.SessionExecutorRowBase;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * <p>�Ự��ͼִ����</p>
 * �ö���ֻ���Զ�ȡ���ݿ���Ϣ
 */
public class ViewExecutor extends SessionExecutorRowBase<ViewExecutor> implements IViewExecutor{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.view.IViewExecutor#queryFirstRow(com.bln.framework.persist.sql.entity.select.ISelectSqlEntity, com.bln.framework.persist.session.executor.material.pagination.IPagination)
	 */
	public IRow queryFirstRow(ISelectSqlEntity selectSqlEntity, IPagination pagination) throws SQLException, IOException{
		
		//1.��ȡ���������ļ�¼
		List<IRow> rows = this.query(selectSqlEntity, pagination);
		
		//2.��ȡ��һ�м�¼
		IRow row = null;
		if(rows != null && rows.size() > 0){
			row = rows.get(0);
		}
		
		//3.���ص�һ�м�¼
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.view.IViewSession#query(com.bln.framework.persist.sql.entity.select.ISelectSqlEntity, com.bln.framework.persist.session.material.pagination.IPagination)
	 */
	public List<IRow> query(ISelectSqlEntity selectSqlEntity, IPagination pagination) throws SQLException, IOException{
		
		//1.У�����
		Assert.paramIsNotNull(selectSqlEntity, "selectSqlEntity");
		
		//2.�����ҳ
		if(pagination != null){
			this.pagination(selectSqlEntity, pagination);
		}
		
		//3.ִ��SQL
		List<IRow> rows = jdbcTemplate.query(conn, selectSqlEntity.getSqlInfo(), selectSqlEntity.getParams(), selectSqlEntity.getColumns());
		
		//4.���ؽ��
		return rows;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.SessionBase#newInstance()
	 */
	@Override
	protected ViewExecutor newInstance() {
		return new ViewExecutor();
	}

}
