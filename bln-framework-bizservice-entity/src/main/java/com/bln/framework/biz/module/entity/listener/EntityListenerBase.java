/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.biz.module.entity.listener;

import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

/**
 * EntityStatelessModule¼àÌýÆ÷»ùÀà
 */
public abstract class EntityListenerBase implements IEntityListener{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#beforeQueryEvt(com.bln.framework.persist.session.executor.material.condition.IConditionClause, com.bln.framework.persist.session.executor.material.pagination.IPagination, java.lang.String)
	 */
	public void beforeQueryEvt(IConditionClause conClause, IPagination pagination, String orderby){
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#afterQueryEvt(java.util.List)
	 */
	public List<IRow> afterQueryEvt(List<IRow> rows){
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#beforeInsertEvt(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeInsertEvt(IRow row){
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#afterInsertEvt(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterInsertEvt(IRow row){
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#beforeUpdateEvt(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeUpdateEvt(IRow row){
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#afterUpdateEvt(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterUpdateEvt(IRow row){
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#beforeDeleteEvt(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeDeleteEvt(IRow row){
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.IEntityListener#afterDeleteEvt(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterDeleteEvt(IRow row){
		return row;
	}
}
