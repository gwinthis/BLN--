/**
 * @author gengw
 * Created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;

/**
 * ͨ��ID��ȡ��¼�����ӱ��ѯ����
 */
public class EntityQueryByIDService extends EntityQueryServiceBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.��ȡ��ѯ����
		IRow condition = reqMo.getRowOfReq(conditionNodeName);
		
		//2.��ȡʵ��
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//3.ִ�в�ѯ
		if(childTableNames != null && childTableNames.length > 0){
			Map<String, List<IRow>> respRowsMap = entity.queryByIdWithChilds(condition, childTableNames);
			reqMo.setAllRowsOfResp(respRowsMap);
		}else{
			IRow row = entity.queryById(condition);
			reqMo.setRowOfResp(tableName, row);
		}
		
		//4.������ӦMO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}

}
