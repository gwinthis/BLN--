/**
 * @author gengw
 * Created on Aug 9, 2012
 */
package com.bln.framework.biz.service.instance.entity.integrate;

import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;

import com.bln.framework.biz.service.instance.entity.complex.EntityService;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;

/**
 * ���ɲ�ѯʵ�����
 */
public class IntegrateEntityQueryService extends EntityService {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.��ȡҪ��ѯ������
		Map<String, List<IRow>> rowsMap = reqMo.getAllRowsOfReq();
		
		//2.��ȡ��ѯ������������Ϣ��ѯ��ļ�¼
		for ( Map.Entry<String, List<IRow>> entry : rowsMap.entrySet()){
			
			//��ȡ����
			String tableName = entry.getKey();
			
			//��ȡ��ļ�¼
			List<IRow> reqRows = entry.getValue();
			IRow row = reqRows.get(0);
			
			//��ȡ��ѯ����
			IRow condition = null;
			if(row != null){
				List<IRow> conditions = row.getSubRows("CONDITION");
				if(conditions != null && conditions.size() > 0){
					condition = conditions.get(0);
				}				
			}
			
			//��ȡ������Ϣ
			String orderbyClause = null;
			if(row != null){
				List<IRow> orderbys = row.getSubRows("ORDERBY");
				if(orderbys != null && orderbys.size() > 0){
					IRow orderby = orderbys.get(0);
					orderbyClause = orderby.getValue("CLAUSE");
				}				
			}
			
			//��ȡʵ��
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//ִ�в�ѯ
			List<IRow> rows = entity.query(condition, null, orderbyClause);
			reqMo.setRowsOfResp(tableName, rows);

		}
		
		//3.����MO
		moTemplate.serviceSuccessfulEnd(reqMo);
		return reqMo;
	}
}
