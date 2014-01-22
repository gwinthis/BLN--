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
 * <p>会话视图执行器</p>
 * 该对象只可以读取数据库信息
 */
public class ViewExecutor extends SessionExecutorRowBase<ViewExecutor> implements IViewExecutor{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.view.IViewExecutor#queryFirstRow(com.bln.framework.persist.sql.entity.select.ISelectSqlEntity, com.bln.framework.persist.session.executor.material.pagination.IPagination)
	 */
	public IRow queryFirstRow(ISelectSqlEntity selectSqlEntity, IPagination pagination) throws SQLException, IOException{
		
		//1.获取符合条件的记录
		List<IRow> rows = this.query(selectSqlEntity, pagination);
		
		//2.获取第一行记录
		IRow row = null;
		if(rows != null && rows.size() > 0){
			row = rows.get(0);
		}
		
		//3.返回第一行记录
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.view.IViewSession#query(com.bln.framework.persist.sql.entity.select.ISelectSqlEntity, com.bln.framework.persist.session.material.pagination.IPagination)
	 */
	public List<IRow> query(ISelectSqlEntity selectSqlEntity, IPagination pagination) throws SQLException, IOException{
		
		//1.校验参数
		Assert.paramIsNotNull(selectSqlEntity, "selectSqlEntity");
		
		//2.处理分页
		if(pagination != null){
			this.pagination(selectSqlEntity, pagination);
		}
		
		//3.执行SQL
		List<IRow> rows = jdbcTemplate.query(conn, selectSqlEntity.getSqlInfo(), selectSqlEntity.getParams(), selectSqlEntity.getColumns());
		
		//4.返回结果
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
