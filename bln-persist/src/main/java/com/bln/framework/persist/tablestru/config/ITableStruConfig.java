package com.bln.framework.persist.tablestru.config;

import java.util.List;

import com.bln.framework.filestru.IFileStru;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;

/**
 * ��ṹ�ĵ��Ľṹ
 */
public interface ITableStruConfig extends IFileStru{
	
	/**
	 * table�ڵ�����
	 */
	public static final String NODE_TABLE_NM = "table";
	
	/**
	 * id�ڵ�����
	 */
	public static final String NODE_ID_NM = "id";
	
	/**
	 * generator�ڵ�����
	 */
	public static final String NODE_GENERATOR_NM = "generator";

	/**
	 * childref�ڵ�����
	 */
	public static final String NODE_CHILDREF_NM = "childref";

	/**
	 * ref�ڵ�����
	 */
	public static final String NODE_REF_NM = "ref";
	
	/**
	 * param�ڵ�����
	 */
	public static final String NODE_PARM_NM = "param";
	
	/**
	 * <p>vercol�ڵ�����</p>
	 */
	public static final String NODE_VERCOL_NM = "vercol";

	/**
	 * <p>column�ڵ�����</p>
	 */
	public static final String NODE_COLUMN_NM = "column";

	/**
	 * name��������
	 * �ڵ������
	 */
	public static final String ATTR_NAME = "name";

	/**
	 * schemaName��������
	 * ��ڵ��schema����
	 */
	public static final String ATTR_SCHEMA_NAME = "schemaName";

	
	/**
	 * <p>type��������</p>
	 * �ڵ������
	 */
	public static final String ATTR_TYPE = "type";

	/**
	 * <p>default_value��������</p>
	 * <p>�ֶε�Ĭ��ֵ</p>
	 */
	public static final String ATTR_DEFAULT_VALUE = "default_value";
	
	/**
	 * <p>value��������</p>
	 * <p>�ڵ��ֵ</p>
	 */
	public static final String ATTR_VALUE = "value";
	
	/**
	 * <p>childtable��������</p>
	 * <p>����������������,childref�ڵ������</p>
	 */
	public static final String ATTR_CHILDTABLE = "childtable";
	
	/**
	 * <p>pcolname��������</p>
	 * <p>����й�����ʹ�õ��ֶ����ƣ���ref�ڵ���ʹ��</p>
	 */
	public static final String ATTR_PCOLNAME = "pcolname";
	
	
	/**
	 * <p>childcolname��������</p>
	 * <p>����й�������ʹ�õ��ֶ����ƣ���ref�ڵ���ʹ��</p>
	 */
	public static final String ATTR_CHILDCOLNAME = "childcolname";

	/**
	 * <p>isidenty��������</p>
	 * <p>�ֶ��Ƿ�Ϊidenty: true false</p>
	 */
	public static final String ATTR_IS_IDENTY = "isidenty";
	/**
	 * <p>mandtory��������</p>
	 * <p>�ֶ��Ƿ�ǿ�</p>
	 */
	public static final String ATTR_IS_MANDATORY = "mandatory";
	/**
	 * <p>length��������</p>
	 * <p>�ֶγ��ȣ��ֽ�Ϊ��λ</p>
	 */
	public static final String ATTR_LENGTH = "length";

	/**
	 * <p>scale��������</p>
	 * <p>����С�����λ��</p>
	 */
	public static final String ATTR_SCALE = "scale";



	/**
	 * ��ȡ����
	 * @return
	 */
	public String getTableNm();


	/**
	 * ��ȡ�������
	 * @return
	 */
	public List<ITableStruConfigEntity> getIDS();


	/**
	 * ��ȡ��İ汾������Ϣ
	 * @return
	 */
	public ITableStruConfigEntity getVerCol();


	/**
	 * ��ȡ����ֶ�
	 * @return
	 */
	public List<ITableStruConfigEntity> getColumns();


	/**
	 * ��ȡ������
	 * @return
	 */
	public List<ITableStruConfigEntity> getFks();

	/**
	 * ��ȡSchema��
	 * @return
	 */
	public String getSchemaNm();

	/**
	 * ��ȡ��ڵ��µ������б�
	 * @param configName ��������
	 * @return �����б�
	 */
	public List<ITableStruConfigEntity> getConfigsOfTable(String configName);


	public ITableStruConfigEntity getConfigOfTable(String configName);


	/**
	 * ���ñ������
	 */
	public void setIDS(List<ITableStruConfigEntity> ids);


	/**
	 * ���ñ�İ汾������Ϣ
	 * @return
	 */
	public void setVerCol(ITableStruConfigEntity verCol);


	/**
	 * ���ñ���ֶ�
	 */
	public void setColumns(List<ITableStruConfigEntity> columns);


	/**
	 * ���ñ�����
	 */
	public void setFks(List<ITableStruConfigEntity> fks);


	/**
	 * ��ʼ��ڵ�
	 * @return
	 */
	public void initTable(String tableName, String schemaName);
	
	

	
}