package com.bln.framework.persist.tablestru;

import com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnsComponent;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;

public interface ITableStru {

	/**
	 * 字段组件名称
	 */
	public static final String COMPONENT_ALLCOLUMNS_NAME = "allColumns";

	/**
	 * 主键组件名称
	 */
	public static final String COMPONENT_IDS_NAME = "ids";

	
	/**
	 * 版本控制字段组件名称
	 */
	public static final String COMPONENT_VERCOL_NAME = "verCol";

	
	/**
	 * 外键组件名称
	 */
	public static final String COMPONENT_FKS_NAME = "fks";

	
	/**
	 * @return the tableName
	 */
	public abstract String getTableName();

	/**
	 * @param tableName the tableName to set
	 */
	public abstract void setTableName(String tableName);

	/**
	 * @return the columns
	 */
	public abstract IAllColumnsComponent getAllColumns();

	/**
	 * @param allColumns the columns to set
	 */
	public abstract void setAllColumns(IAllColumnsComponent allColumns);


	/**
	 * @return the fks
	 */
	public abstract IForeignKey[] getFks();

	/**
	 * @param fks the fks to set
	 */
	public abstract void setFks(IForeignKey[] fks);

	/**
	 * @return the ids
	 */
	public IColumn[] getIds();

	/**
	 * @param ids the ids to set
	 */
	public void setIds(IColumn[] ids);

	/**
	 * @return the verCol
	 */
	public IColumn getVerCol();

	/**
	 * @param verCol the verCol to set
	 */
	public void setVerCol(IColumn verCol);

	/**
	 * @return the schemaName
	 */
	public String getSchemaName();

	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName);

}