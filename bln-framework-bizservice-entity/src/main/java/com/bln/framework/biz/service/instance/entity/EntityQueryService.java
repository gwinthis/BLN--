/**
 * @author gengw
 * Created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import java.util.List;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

/**
 * ʵ���ѯ����
 */
public class EntityQueryService extends EntityQueryServiceBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.��ȡ��ѯ����
		IRow condition = null;
		
		//��ȡ��һ����ѯ������¼
		List<IRow> cons = reqMo.getRowsOfReq(conditionNodeName);
		if(null != cons && cons.size() > 0){
			condition = cons.get(0);
		}
		
		//2.��ȡ��ҳ��Ϣ
		IPagination pagination = paginationUtil.pagination(reqMo);
		
		//3.��ȡ������Ϣ
		String orderby = this.orderbyExtractor.extractor(reqMo);
		
		//4.��ȡʵ��
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//5.ִ�в�ѯ
		List<IRow> rows = entity.query(condition, pagination, orderby);
		reqMo.setRowsOfResp(tableName, rows);
		
		//5.��������������ҳ��		
		reqMo = paginationUtil.setPageCount(reqMo, pagination);

		//6.����MO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}
}
