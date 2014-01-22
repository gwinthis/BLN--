/**
 * @author gengw
 * Created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import java.util.List;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;

/**
 * ʵ����������
 */
public class EntityInsertService extends EntityServiceBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.������¼
		List<IRow> rows = reqMo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1��ȡʵ��
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2������¼
			rows = entity.insertRows(rows);
			
			//1.3���÷�������
			reqMo.setRowsOfResp(tableName, rows);
			
		}
		
		//2.������ӦMO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}
}
