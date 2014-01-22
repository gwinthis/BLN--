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
 * ���Ӽ���ҵ��ʵ�����
 */
public class EntityService extends ServiceBase{


	/**
	 * ʵ���
	 */
	protected IEntityLib entityLib = null;

	/**
	 * �������
	 */
	protected String tableName = null;

	/**
	 * �ӱ����
	 */
	protected String[] childTableNames = null;
	
	/**
	 * ��ִ��ɾ����ʱ���Ƿ�ɾ���ӱ��¼
	 */
	protected boolean deleteChildRowOnDelete = false;
	
	/**
	 * �����ڵ�����
	 */
	protected String operationNodeName = "operation";

	/**
	 * �����ڵ�����
	 */
	protected String conditionNodeName = "condition";
	
	/**
	 * ��ҳ��Ϣ��ȡ��
	 */
	protected IPaginationUtil paginationUtil = null;

	/**
	 * ������Ϣ��ȡ��
	 */
	protected IOrderbyExtractorUtil orderbyExtractor = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#service(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable{
		
		//1.У�����
		Assert.paramIsNotNull(reqMo, "reqMo");
		
		//2.��ȡ����
		String operation = reqMo.getParamOfReq(operationNodeName);
		if(operation == null){
			throw new OperationIsNullException("it not found " + operationNodeName + " in parameter of request!");
		}
		operation = operation.toLowerCase();
				
		//3.ҵ��ִ�в���
		IMessageObject respMo = executeOperation(reqMo, operation );
		
		//4.������ӦMO
		return respMo;
	}
	
	/**
	 * ִ��ҵ�񷽷�
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
	 * �����ѯ
	 * @param mo ����MO
	 * @return ��ӦMO
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	protected IMessageObject query(IMessageObject mo) throws SQLException, IOException, ParseException  {
		
		//1.��ȡ��ѯ����
		IRow condition = null;
		
		//��ȡ��һ����ѯ������¼
		List<IRow> cons = mo.getRowsOfReq(conditionNodeName);
		if(null != cons && cons.size() > 0){
			condition = cons.get(0);
		}
		
		//2.��ȡ��ҳ��Ϣ
		IPagination pagination = paginationUtil.pagination(mo);
		
		//3.��ȡ������Ϣ
		String orderby = this.orderbyExtractor.extractor(mo);
		
		//4.��ȡʵ��
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//4.ִ�в�ѯ
		List<IRow> rows = entity.query(condition, pagination, orderby);
		mo.setRowsOfResp(tableName, rows);
		
		//��������������ҳ��		
		mo = paginationUtil.setPageCount(mo, pagination);

		//5.����MO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * ����������ѯ��¼
	 * @param ����mo
	 * @return ��ӦMO
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected IMessageObject queryById(IMessageObject mo) throws SQLException, IOException, ParseException {
		
		return this.queryById(mo, this.tableName, this.childTableNames);
	}
	
	/**
	 * ����������ѯ
	 * @param mo ��Ϣ����
	 * @param tableName �������
	 * @param childTableNms Ҫ��ѯ���ӱ�
	 * @return ��Ӧ��Ϣ����
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @
	 */
	protected IMessageObject queryById(IMessageObject mo, String tableName, String[] childTableNms) throws SQLException, IOException, ParseException {
		
		//1.��ȡ��ѯ����
		IRow condition = mo.getRowOfReq(conditionNodeName);
		
		//2.��ȡʵ��
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//3.ִ�в�ѯ
		if(childTableNms != null && childTableNms.length > 0){
			Map<String, List<IRow>> respRowsMap = entity.queryByIdWithChilds(condition, childTableNms);
			mo.setAllRowsOfResp(respRowsMap);
		}else{
			IRow row = entity.queryById(condition);
			mo.setRowOfResp(tableName, row);
		}
		
		//4.������ӦMO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * ����������ѯ�ӱ�
	 * @param mo ��Ϣ����
	 * @param tableName �������
	 * @param childTableNames Ҫ��ѯ���ӱ�
	 * @return ��Ӧ��Ϣ����
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @
	 */
	protected IMessageObject queryChilds(IMessageObject mo, String tableName, String[] childTableNames) throws SQLException, IOException, ParseException {
		
		//У�����
		Assert.paramIsNotNull(childTableNames, "childTableNms");
		
		//1.��ȡ��ѯ����
		IRow condition = mo.getRowOfReq(conditionNodeName);
		
		//2.��ȡʵ��
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//3.ִ�в�ѯ
		Map<String, List<IRow>> respRowsMap = entity.queryChilds(condition, childTableNames);
		mo.setAllRowsOfResp(respRowsMap);
		
		//4.������ӦMO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * ��������
	 * @param mo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws PersistStaleEntityException 
	 * 
	 */
	protected IMessageObject insert(IMessageObject mo) throws InvalidRowException, SQLException, IOException  {
		
		//1.������¼
		List<IRow> rows = mo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1��ȡʵ��
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2������¼
			rows = entity.insertRows(rows);
			
			//1.3���÷�������
			mo.setRowsOfResp(tableName, rows);
			
		}
		
		//2.������ӦMO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * ��������
	 * @param mo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws PersistStaleEntityException 
	 * 
	 */
	protected IMessageObject update(IMessageObject mo) throws PersistStaleEntityException, InvalidRowException, SQLException, IOException  {
		
		//1.ɾ����¼
		List<IRow> rows = mo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1��ȡʵ��
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2���¼�¼
			rows = entity.updateRows(rows);
			
			//1.3���÷�������
			mo.setRowsOfResp(tableName, rows);

		}
		
		//2.������ӦMO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	/**
	 * ɾ������
	 * @param mo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 * 
	 */
	protected IMessageObject delete(IMessageObject mo) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException  {
		
		//1.ɾ����¼
		List<IRow> rows = mo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1��ȡʵ��
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2ɾ����¼
			entity.deleteRows(rows, deleteChildRowOnDelete);
		}
		
		//2.������ӦMO
		moTemplate.serviceSuccessfulEnd(mo);
		return mo;
	}
	
	/**
	 * ���������¼�Ͷ�����ӱ��¼
	 * @param mo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
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
	 * ����������ӱ��¼
	 * @param mo ������Ϣ����
	 * @param tableName �������
	 * @param childTableNms �ӱ����
	 * @return ��Ӧ��Ϣ����
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 * @
	 */
	protected IMessageObject save(IMessageObject mo, String tableName, String[] childTableNms) throws PersistStaleEntityException, RefenceConstraintException, InvalidRowException, SQLException, IOException, ParseException  {
		
		//1.��ȡ�����¼
		List<IRow> rows = mo.getRowsOfReq(tableName);
		
		//2.��ȡ�ӱ��¼
		Map<String, List<IRow>> childRowsMap = mo.getRowsOfReq(childTableNms);
		
		//3.��ȡʵ��
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//4.���游���¼
		Map<String, List<IRow>>  allRows = entity.save(rows, childRowsMap, deleteChildRowOnDelete);
		
		//5.���÷�������
		mo.setAllRowsOfResp(allRows);
		
		//6.������ӦMO
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
