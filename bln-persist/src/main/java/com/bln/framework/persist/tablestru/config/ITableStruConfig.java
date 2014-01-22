package com.bln.framework.persist.tablestru.config;

import java.util.List;

import com.bln.framework.filestru.IFileStru;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;

/**
 * 表结构文档的结构
 */
public interface ITableStruConfig extends IFileStru{
	
	/**
	 * table节点名称
	 */
	public static final String NODE_TABLE_NM = "table";
	
	/**
	 * id节点名称
	 */
	public static final String NODE_ID_NM = "id";
	
	/**
	 * generator节点名称
	 */
	public static final String NODE_GENERATOR_NM = "generator";

	/**
	 * childref节点名称
	 */
	public static final String NODE_CHILDREF_NM = "childref";

	/**
	 * ref节点名称
	 */
	public static final String NODE_REF_NM = "ref";
	
	/**
	 * param节点名称
	 */
	public static final String NODE_PARM_NM = "param";
	
	/**
	 * <p>vercol节点名称</p>
	 */
	public static final String NODE_VERCOL_NM = "vercol";

	/**
	 * <p>column节点名称</p>
	 */
	public static final String NODE_COLUMN_NM = "column";

	/**
	 * name属性名称
	 * 节点的名称
	 */
	public static final String ATTR_NAME = "name";

	/**
	 * schemaName属性名称
	 * 表节点的schema名称
	 */
	public static final String ATTR_SCHEMA_NAME = "schemaName";

	
	/**
	 * <p>type属性名称</p>
	 * 节点的类型
	 */
	public static final String ATTR_TYPE = "type";

	/**
	 * <p>default_value属性名称</p>
	 * <p>字段的默认值</p>
	 */
	public static final String ATTR_DEFAULT_VALUE = "default_value";
	
	/**
	 * <p>value属性名称</p>
	 * <p>节点的值</p>
	 */
	public static final String ATTR_VALUE = "value";
	
	/**
	 * <p>childtable属性名称</p>
	 * <p>外键所关联表的名称,childref节点的属性</p>
	 */
	public static final String ATTR_CHILDTABLE = "childtable";
	
	/**
	 * <p>pcolname属性名称</p>
	 * <p>外键中关联所使用的字段名称，在ref节点中使用</p>
	 */
	public static final String ATTR_PCOLNAME = "pcolname";
	
	
	/**
	 * <p>childcolname属性名称</p>
	 * <p>外键中关联表所使用的字段名称，在ref节点中使用</p>
	 */
	public static final String ATTR_CHILDCOLNAME = "childcolname";

	/**
	 * <p>isidenty属性名称</p>
	 * <p>字段是否为identy: true false</p>
	 */
	public static final String ATTR_IS_IDENTY = "isidenty";
	/**
	 * <p>mandtory属性名称</p>
	 * <p>字段是否非空</p>
	 */
	public static final String ATTR_IS_MANDATORY = "mandatory";
	/**
	 * <p>length属性名称</p>
	 * <p>字段长度，字节为单位</p>
	 */
	public static final String ATTR_LENGTH = "length";

	/**
	 * <p>scale属性名称</p>
	 * <p>保留小数点后位数</p>
	 */
	public static final String ATTR_SCALE = "scale";



	/**
	 * 获取表名
	 * @return
	 */
	public String getTableNm();


	/**
	 * 获取表的主键
	 * @return
	 */
	public List<ITableStruConfigEntity> getIDS();


	/**
	 * 获取表的版本控制信息
	 * @return
	 */
	public ITableStruConfigEntity getVerCol();


	/**
	 * 获取表的字段
	 * @return
	 */
	public List<ITableStruConfigEntity> getColumns();


	/**
	 * 获取表的外键
	 * @return
	 */
	public List<ITableStruConfigEntity> getFks();

	/**
	 * 获取Schema名
	 * @return
	 */
	public String getSchemaNm();

	/**
	 * 获取表节点下的配置列表
	 * @param configName 配置名称
	 * @return 配置列表
	 */
	public List<ITableStruConfigEntity> getConfigsOfTable(String configName);


	public ITableStruConfigEntity getConfigOfTable(String configName);


	/**
	 * 设置表的主键
	 */
	public void setIDS(List<ITableStruConfigEntity> ids);


	/**
	 * 设置表的版本控制信息
	 * @return
	 */
	public void setVerCol(ITableStruConfigEntity verCol);


	/**
	 * 设置表的字段
	 */
	public void setColumns(List<ITableStruConfigEntity> columns);


	/**
	 * 设置表的外键
	 */
	public void setFks(List<ITableStruConfigEntity> fks);


	/**
	 * 初始表节点
	 * @return
	 */
	public void initTable(String tableName, String schemaName);
	
	

	
}