/**
 * @author Gengw
 * Created at 2008-05-06
 */
package com.bln.framework.persist.tablestru;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnsComponent;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;

/**
 * ���ݿ�����ṹ
 */
public class TableStru implements ITableStru {
	
	/**
	 * ����
	 */
	protected String tableName = null;

	/**
	 * ģʽ��
	 */
	protected String schemaName = null;

	
	/**
	 * ���������������ֶ�
	 */
	protected IAllColumnsComponent allColumns = null;
	
	/**
	 * �����������
	 */
	protected IColumn[] ids = null;
	
	/**
	 * ���ڱ�汾���Ƶ��ֶ�
	 */
	protected IColumn verCol = null;

	/**
	 * �����������������
	 */
	protected IForeignKey[] fks = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.ITableStru#getTableName()
	 */
	public String getTableName() {
		return tableName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.ITableStru#setTableName(java.lang.String)
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.ITableStru#getColumns()
	 */
	public IAllColumnsComponent getAllColumns() {
		return allColumns;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.ITableStru#setColumns(com.bln.framework.persist.tablestru.column.IColumn[])
	 */
	public void setAllColumns(IAllColumnsComponent allColumns) {
		this.allColumns = allColumns;
	}


	/**
	 * @return the ids
	 */
	public IColumn[] getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(IColumn[] ids) {
		this.ids = ids;
	}

	/**
	 * @return the verCol
	 */
	public IColumn getVerCol() {
		return verCol;
	}

	/**
	 * @param verCol the verCol to set
	 */
	public void setVerCol(IColumn verCol) {
		this.verCol = verCol;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.ITableStru#getFks()
	 */
	public IForeignKey[] getFks() {
		return fks;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.ITableStru#setFks(com.bln.framework.persist.tablestru.fk.IForeignKey[])
	 */
	public void setFks(IForeignKey[] fks) {
		this.fks = fks;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder("Table ");
		
		//tableName
		if(!StringUtils.isEmpty(this.schemaName)){
			info.append(this.schemaName).append(".");
		}
		info.append(tableName).append(" structure : \r\n");

		//columns
		info.append("columns: ").append(allColumns).append("\r\n");
		
		//ids
		info.append("ids: ").append(Arrays.toString(ids)).append("\r\n");
		
		//verCol
		info.append("verCol: ").append(verCol).append("\r\n");
		
		//fks
		info.append("fks: ").append(Arrays.toString(fks)).append("\r\n");
		
		//���ؽ��
		return info.toString();
	}

	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

}
