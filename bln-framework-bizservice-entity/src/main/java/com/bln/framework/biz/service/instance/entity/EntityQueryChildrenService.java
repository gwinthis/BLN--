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
import com.bln.framework.util.asserter.Assert;

/**
 * ͨ��ID��ȡ��¼�����ӱ��ѯ����
 */
public class EntityQueryChildrenService extends EntityQueryServiceBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//У�����
		Assert.paramIsNotNull(childTableNames, "childTableNms");
		
		//1.��ȡ��ѯ����
		IRow condition = reqMo.getRowOfReq(conditionNodeName);
		
		//2.��ȡʵ��
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//3.ִ�в�ѯ
		Map<String, List<IRow>> respRowsMap = entity.queryChilds(condition, childTableNames);
		reqMo.setAllRowsOfResp(respRowsMap);
		
		//4.������ӦMO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}
}
