/**
 * @author gengw
 * Created on May 21, 2012
 */
package com.bln.framework.biz.service.instance.entity.complex;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;
import com.bln.framework.biz.module.entity.exception.InvalidRowException;
import com.bln.framework.biz.module.entity.exception.RefenceConstraintException;
import com.bln.framework.biz.module.entity.lib.IEntityLib;

import com.bln.framework.biz.service.entity.util.orderby.IOrderbyExtractorUtil;
import com.bln.framework.biz.service.entity.util.pagination.IPaginationUtil;
import com.bln.framework.biz.service.instance.ServiceBase;
import com.bln.framework.biz.service.instance.entity.exception.OperationIsNullException;
import com.bln.framework.exception.UnsupportedException;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.util.asserter.Assert;

/**
 * 复杂集成业务实体服务
 */
public class EntityService extends ServiceBase{


	/**
	 * 实体库
	 */
	protected IEntityLib entityLib = null;

	/**
	 * 主表表名
	 */
	protected String tableName = null;

	/**
	 * 子表表名
	 */
	protected String[] childTableNames = null;
	
	/**
	 * 当执行删除的时候是否删除子表记录
	 */
	protected boolean deleteChildRowOnDelete = false;
	
	/**
	 * 操作节点名称
	 */
	protected String operationNodeName = "operation";

	/**
	 * 条件节点名称
	 */
	protected String conditionNodeName = "condition";
	
	/**
	 * 分页信息提取器
	 */
	protected IPaginationUtil paginationUtil = null;

	/**
	 * 排序信息提取器
	 */
	protected IOrderbyExtractorUtil orderbyExtractor = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#service(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable{
		
		//1.校验参数
		Assert.paramIsNotNull(reqMo, "reqMo");
		
		//2.获取操作
		String operation = reqMo.getParamOfReq(operationNodeName);
		if(operation == null){
			throw new OperationIsNullException("it not found " + operationNodeName + " in parameter of request!");
		}
		operation = operation.toLowerCase();
				
		//3.业务执行操作
		IMessageObject respMo = executeOperation(reqMo, operation );
		
		//4.返回响应MO
		return respMo;
	}
	
	/**
	 * 执行业务方法
	 * @param reqMo
	 * @param operation
	 * @return
	 * @throws Throwable
	 */
	protected IMessageObject executeOperation(IMessageObject reqMo, String operation) throws Throwable{
		
		IMessageObject respMo = null;
		if("query".equals(operation)){
			respMo = this.query(reqMo);

		}else if("querychilds".equals(operation)){
			respMo = this.queryChilds(reqMo, tableName, childTableNames);
			
		}else if("querybyid".equals(operation)){
			respMo = this.queryById(reqMo);
		
		}else if("save".equals(operation)){
			respMo = this.save(reqMo);

		}else if("insert".equals(operation)){
			respMo = this.insert(reqMo);
			
		}else if("delete".equals(operation)){
			respMo = this.delete(reqMo);
			
		}else if("update".equals(operation)){
			respMo = this.update(reqMo);
			
		}else{
			throw new UnsupportedException("it does not supporte operation " + operation);
		}
		
		return respMo;
	}
	
	/**
	 * 单表查询
	 * @param mo 请求MO
	 * @return 响应MO
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	protected IMessageObject query(IMessageObject mo) throws SQLException, IOException, ParseException  {
		
		//1.获取查询条件
		IRow condition = null;
		
		//获取第一条查询条件记录
		List<IRow> cons = mo.getRowsOfReq(conditionNodeName);
		if(null != cons && cons.size() > 0){
			condition = cons.get(0);
		}
		
		//2.获取分页信息
		IPagination pagination = paginationUtil.pagination(mo);
		
		//3.提取排序信息
		String orderby = this.orderbyExtractor.extractor(mo);
		
		//4.获取实体
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//4.执行查询
		List<IRow> rows = entity.query(condition, pagination, orderby);
		mo.setRowsOfResp(tableName, rows);
		
		//设置总行数和总页数		
		mo = paginationUtil.setPageCount(mo, pagination);

		//5.返回MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * 根据主键查询记录
	 * @param 请求mo
	 * @return 响应MO
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected IMessageObject queryById(IMessageObject mo) throws SQLException, IOException, ParseException {
		
		return this.queryById(mo, this.tableName, this.childTableNames);
	}
	
	/**
	 * 根据主键查询
	 * @param mo 消息对象
	 * @param tableName 主表表名
	 * @param childTableNms 要查询的子表
	 * @return 响应消息对象
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @
	 */
	protected IMessageObject queryById(IMessageObject mo, String tableName, String[] childTableNms) throws SQLException, IOException, ParseException {
		
		//1.获取查询条件
		IRow condition = mo.getRowOfReq(conditionNodeName);
		
		//2.获取实体
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//3.执行查询
		if(childTableNms != null && childTableNms.length > 0){
			Map<String, List<IRow>> respRowsMap = entity.queryByIdWithChilds(condition, childTableNms);
			mo.setAllRowsOfResp(respRowsMap);
		}else{
			IRow row = entity.queryById(condition);
			mo.setRowOfResp(tableName, row);
		}
		
		//4.返回响应MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * 根据主键查询子表
	 * @param mo 消息对象
	 * @param tableName 主表表名
	 * @param childTableNames 要查询的子表
	 * @return 响应消息对象
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @
	 */
	protected IMessageObject queryChilds(IMessageObject mo, String tableName, String[] childTableNames) throws SQLException, IOException, ParseException {
		
		//校验参数
		Assert.paramIsNotNull(childTableNames, "childTableNms");
		
		//1.获取查询条件
		IRow condition = mo.getRowOfReq(conditionNodeName);
		
		//2.获取实体
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//3.执行查询
		Map<String, List<IRow>> respRowsMap = entity.queryChilds(condition, childTableNames);
		mo.setAllRowsOfResp(respRowsMap);
		
		//4.返回响应MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * 新增数据
	 * @param mo 请求消息对象
	 * @return 响应消息对象
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws PersistStaleEntityException 
	 * 
	 */
	protected IMessageObject insert(IMessageObject mo) throws InvalidRowException, SQLException, IOException  {
		
		//1.新增记录
		List<IRow> rows = mo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1获取实体
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2新增记录
			rows = entity.insertRows(rows);
			
			//1.3设置返回数据
			mo.setRowsOfResp(tableName, rows);
			
		}
		
		//2.返回响应MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * 更新数据
	 * @param mo 请求消息对象
	 * @return 响应消息对象
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws PersistStaleEntityException 
	 * 
	 */
	protected IMessageObject update(IMessageObject mo) throws PersistStaleEntityException, InvalidRowException, SQLException, IOException  {
		
		//1.删除记录
		List<IRow> rows = mo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1获取实体
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2更新记录
			rows = entity.updateRows(rows);
			
			//1.3设置返回数据
			mo.setRowsOfResp(tableName, rows);

		}
		
		//2.返回响应MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	/**
	 * 删除数据
	 * @param mo 请求消息对象
	 * @return 响应消息对象
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 * 
	 */
	protected IMessageObject delete(IMessageObject mo) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException  {
		
		//1.删除记录
		List<IRow> rows = mo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1获取实体
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2删除记录
			entity.deleteRows(rows, deleteChildRowOnDelete);
		}
		
		//2.返回响应MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * 保存主表记录和定义的子表记录
	 * @param mo 请求消息对象
	 * @return 响应消息对象
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 * @
	 */
	protected IMessageObject save(IMessageObject mo) throws PersistStaleEntityException, RefenceConstraintException, InvalidRowException, SQLException, IOException, ParseException  {
		
		mo = save(mo, tableName, childTableNames);
		return mo;		
	}

	/**
	 * 保存主表和子表记录
	 * @param mo 请求消息对象
	 * @param tableName 主表表名
	 * @param childTableNms 子表表名
	 * @return 响应消息对象
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 * @
	 */
	protected IMessageObject save(IMessageObject mo, String tableName, String[] childTableNms) throws PersistStaleEntityException, RefenceConstraintException, InvalidRowException, SQLException, IOException, ParseException  {
		
		//1.获取父表记录
		List<IRow> rows = mo.getRowsOfReq(tableName);
		
		//2.获取子表记录
		Map<String, List<IRow>> childRowsMap = mo.getRowsOfReq(childTableNms);
		
		//3.获取实体
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//4.保存父表记录
		Map<String, List<IRow>>  allRows = entity.save(rows, childRowsMap, deleteChildRowOnDelete);
		
		//5.设置返回数据
		mo.setAllRowsOfResp(allRows);
		
		//6.返回响应MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the childTableNames
	 */
	public String[] getChildTableNames() {
		return childTableNames;
	}

	/**
	 * @param childTableNames the childTableNames to set
	 */
	public void setChildTableNames(String[] childTableNames) {
		this.childTableNames = childTableNames;
	}

	/**
	 * @return the operationNodeName
	 */
	public String getOperationNodeName() {
		return operationNodeName;
	}

	/**
	 * @param operationNodeName the operationNodeName to set
	 */
	public void setOperationNodeName(String operationNodeName) {
		this.operationNodeName = operationNodeName;
	}

	/**
	 * @return the conditionNodeName
	 */
	public String getConditionNodeName() {
		return conditionNodeName;
	}

	/**
	 * @param conditionNodeName the conditionNodeName to set
	 */
	public void setConditionNodeName(String conditionNodeName) {
		this.conditionNodeName = conditionNodeName;
	}

	/**
	 * @return the deleteChildRowOnDelete
	 */
	public boolean isDeleteChildRowOnDelete() {
		return deleteChildRowOnDelete;
	}

	/**
	 * @param deleteChildRowOnDelete the deleteChildRowOnDelete to set
	 */
	public void setDeleteChildRowOnDelete(boolean deleteChildRowOnDelete) {
		this.deleteChildRowOnDelete = deleteChildRowOnDelete;
	}

	/**
	 * @return the entityLib
	 */
	public IEntityLib getEntityLib() {
		return entityLib;
	}

	/**
	 * @param entityLib the entityLib to set
	 */
	public void setEntityLib(IEntityLib entityLib) {
		this.entityLib = entityLib;
	}
	
	/**
	 * @return the orderbyExtractor
	 */
	public IOrderbyExtractorUtil getOrderbyExtractor() {
		return orderbyExtractor;
	}

	/**
	 * @param orderbyExtractor the orderbyExtractor to set
	 */
	public void setOrderbyExtractor(IOrderbyExtractorUtil orderbyExtractor) {
		this.orderbyExtractor = orderbyExtractor;
	}
	
	/**
	 * @return the paginationUtil
	 */
	public IPaginationUtil getPaginationUtil() {
		return paginationUtil;
	}

	/**
	 * @param paginationUtil the paginationUtil to set
	 */
	public void setPaginationUtil(IPaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}
}
