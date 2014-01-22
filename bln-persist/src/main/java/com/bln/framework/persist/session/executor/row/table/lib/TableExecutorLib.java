/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.session.executor.row.table.lib;

import java.util.Map;

import com.bln.framework.exception.UnsupportedException;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;
import com.bln.framework.persist.util.factory.LibByTableStru;

/**
 * ITableQueryExecutor�Ŀ����
 */
public abstract class TableExecutorLib<T extends ITableExecutor> extends LibByTableStru<T> implements ITableExecutorLib<T>{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.simple.ISimpleFactory#getInstance(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public T getInstance(String objNm){
		
		//��ȡtableExecutorģ��
		T tableExecutor = super.getInstance(objNm);
		
		//��¡tableSession
		T newTableExecutor = (T)tableExecutor.clone();
		
		//�����µ�ʵ��
		return newTableExecutor;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.simple.SimpleFactory#getAllInstance()
	 */
	public Map<String, T> getAllInstance() {
		throw new UnsupportedException();
	}
}
