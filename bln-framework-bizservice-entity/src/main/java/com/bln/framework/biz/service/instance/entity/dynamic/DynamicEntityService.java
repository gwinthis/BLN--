/**
 * @author gengw
 * Created on May 22, 2012
 */
package com.bln.framework.biz.service.instance.entity.dynamic;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.biz.service.instance.entity.complex.EntityService;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.util.asserter.Assert;

/**
 * 动态实体服务
 */
public class DynamicEntityService extends EntityService {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceProcessor#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.创建实体服务
		EntityService entityService = new EntityService();
		
		//entityLib
		entityService.setEntityLib(entityLib);

		//moTemplate
		entityService.setMoTemplate(moTemplate);

		//paginationUtil
		entityService.setPaginationUtil(paginationUtil);

		//orderbyExtractor
		entityService.setOrderbyExtractor(orderbyExtractor);
		
		//表名
		String tableName = reqMo.getParamOfReq("table");
		Assert.paramIsNotNull(tableName, "tableName");
		
		entityService.setTableName(tableName);
		
		//子表表名
		String childTables = reqMo.getParamOfReq("childtable");
		String[] childTableNames = StringUtils.split(childTables, ",");
		
		entityService.setChildTableNames(childTableNames);
		
		//当执行删除的时候是否删除子表记录
		String deleteChildRowOnDelete = reqMo.getParamOfReq("deleteChildRowOnDelete");
		if(!StringUtils.isEmpty(deleteChildRowOnDelete)){
			entityService.setDeleteChildRowOnDelete("true".equals(deleteChildRowOnDelete));
		}
		
		//2.执行请求
		return entityService.execute(reqMo);
	}

}
