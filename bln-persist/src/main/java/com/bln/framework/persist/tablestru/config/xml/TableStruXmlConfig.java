package com.bln.framework.persist.tablestru.config.xml;

import java.util.List;

import org.dom4j.Element;

import com.bln.framework.filestru.xml.config.XMLFile4ConfigBase;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;
import com.bln.framework.persist.tablestru.config.entity.TableStruConfigEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * ��ṹ������Ϣ
		<table name="T_YDBS_IOPPS" schemaName="COSCO">
		
		<id name="IOPPS_ID" type="long">
		<generator type="sequence">
		<param name = "seqname" value = "SEQ_T_YDBS_IOPPS"/>
		</generator>
		</id>
		
		<vercol name = "UPD_DATE" type="timestamp"/>
		
		<column name="IOPPSCD" type="string" />
		<column name="IOPPSNM"  type="string" />
		<column name="INTYPE"  type="string" />
		<column name="OUTYTPE"  type="string" />
		<column name="RECORDER_CD" type="string" />
		<column name="RECORDER_NM"  type="string" />
		<column name="RECORD_DT"  type="date" />
		<column name="UPD_OPER_CD"  type="string" />
		<column name="UPD_OPER_NM"  type="string" />
		<column name="ORG_LEVEL_NO"  type="string" />
		<column name="OPER_OU_CD"  type="string" />
		<column name="OPER_BU_CD"  type="string" />
		<column name="IS_DELETED"  type="string" />
		<column name="VERSIONNO"  type="long" />
		
		<fk name = "FK_O_LP_CONTRACTID" reftable = "CONTRACTCUSTOM">
		<ref mcolnm = "PRIMARYCONTRACTID" ccolnm = "CONTRACTID"/>
		<ref mcolnm = "COMPANYID" ccolnm = "COMPANYID"/>
		</fk>
		
		</table>
*
 */
public class TableStruXmlConfig extends XMLFile4ConfigBase<ITableStruConfigEntity> implements ITableStruXmlConfig{

	/**
	 * table�ڵ�
	 * �ýڵ�Ϊ���ڵ�
	 */
	public Element table = null;
	
	/**
	 * ���캯��
	 */
	public TableStruXmlConfig(){
		super();
		this.encoding = "UTF-8";
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.config.XMLFile4ConfigBase#procSubConfig(com.bln.framework.config.entity.ConfigEntity, com.bln.framework.config.entity.ConfigEntity)
	 */
	@Override
	protected void procSubConfig(ITableStruConfigEntity config, ITableStruConfigEntity subConfig) {
		config.addSubConfigEntity(subConfig);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.FileStruBase#initStru()
	 */
	@Override
	protected void initStru() {
		
		//��ȡ���ڵ�
		this.table = this.document.getRootElement();		
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getTableNm(){
		return this.table.attributeValue(ATTR_NAME);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.config.ITableStruConfig#getSchemaNm()
	 */
	public String getSchemaNm(){
		return this.table.attributeValue(ATTR_SCHEMA_NAME);
	}
	
	/**
	 * ��ʼ��ڵ�
	 * @return
	 */
	public void initTable(String tableName, String schemaName){
		Assert.paramIsNotNull ( "tableName", tableName);
		this.table = this.document.addElement("table");
		table.addAttribute(ATTR_NAME, tableName);
		table.addAttribute(ATTR_SCHEMA_NAME, schemaName);
	}
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	public List<ITableStruConfigEntity> getIDS(){
		return this.getEntitysOfElement(table, NODE_ID_NM);
	}
	
	/**
	 * ���ñ������
	 */
	public void setIDS(List<ITableStruConfigEntity> ids){
		this.addEntitys2Element(table, ids);
	}
	
	/**
	 * ��ȡ��İ汾������Ϣ
	 * @return
	 */
	public ITableStruConfigEntity getVerCol(){
		return this.getEntityOfElement(table, NODE_VERCOL_NM);
	}
	
	/**
	 * ���ñ�İ汾������Ϣ
	 * @return
	 */
	public void setVerCol(ITableStruConfigEntity verCol){
		
		table.add(entity2Element(verCol));
	}
	
	/**
	 * ��ȡ����ֶ�
	 * @return
	 */
	public List<ITableStruConfigEntity> getColumns(){
		return this.getEntitysOfElement(table, NODE_COLUMN_NM);
	}
	
	/**
	 * ���ñ���ֶ�
	 */
	public void setColumns(List<ITableStruConfigEntity> columns){
		this.addEntitys2Element(table, columns);
	}

	/**
	 * ��ȡ������
	 * @return
	 */
	public List<ITableStruConfigEntity> getFks(){
		return this.getEntitysOfElement(table, NODE_CHILDREF_NM);
	}
	
	/**
	 * ���ñ�����
	 */
	public void setFks(List<ITableStruConfigEntity> fks){
		this.addEntitys2Element(table, fks);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.config.ITableStruConfig#getConfigsOfTable(java.lang.String)
	 */
	public List<ITableStruConfigEntity> getConfigsOfTable(String configName){
		return this.getEntitysOfElement(table, configName);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.config.ITableStruConfig#getConfigOfTable(java.lang.String)
	 */
	public ITableStruConfigEntity getConfigOfTable(String configName){
		return this.getEntityOfElement(table, configName);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.config.XMLFile4ConfigBase#newConfig(java.lang.Object)
	 */
	@Override
	protected ITableStruConfigEntity newConfigEntity(Object oriElement) {
		return new TableStruConfigEntity(oriElement);
	}

}
