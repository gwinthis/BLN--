package com.bln.framework.persist.row;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IRow extends Serializable{

	/**
	 * �Ƿ�����Ӽ�¼
	 * @return <p>true: ����</p><p>false: ������</p>
	 * 
	 */
	public abstract boolean isHasSubRows();

	/**
	 * ����������ֵ
	 * @param ����������
	 * @return ������ֵ
	 */
	public abstract String getValue(String fieldNm);

	/**
	 * �����������ֵ
	 * @param ����������
	 * @return ������ֵ
	 */
	public abstract void setValue(String fieldNm, String value);
	
	/**
	 * ɾ��ָ���ֶ�
	 * @param fieldNm �ֶ���
	 */
	public void removeField(String fieldNm);
	
	/**
	 * ����Ӽ�
	 * @param Ҫ����Ӽ�������
	 * @return �Ӽ�
	 */
	public abstract List<IRow> getSubRows(String subSetNm);

	/**
	 * ����Ӽ�
	 * @param �Ӽ�����
	 * @param Ҫ��ӵ��Ӽ�
	 */
	
	public abstract void addSubRows(String subSetNm, List<IRow> rows);

	/**
	 * ɾ���Ӽ�
	 * @param �Ӽ�����
	 */
	
	public abstract void removeSubRows(String subSetNm);

	/**
	 * ����ж�������������������ֵ
	 * @return ENTRY���͵�����
	 */
	public abstract Map.Entry<String, String>[] getDataEntrys();

	/**
	 * �����ж�������ݽṹ
	 * @return String���͵�����
	 */
	public abstract List<String> getAllFields();

	/**
	 * ����Ӽ��е���
	 * @param subSetNm Ҫ��ӵ��Ӽ�����
	 * @param row �Ӽ�
	 */
	public void addSubRow(String subSetNm, IRow row);

	/**
	 * ����һ���ֶε����Ա�
	 * @param fieldNm Ҫ�������Ա���ֶ�����
	 * @param attrs ���Ա�
	 */
	public Map<String, String> getFieldAttrs(String fieldNm);

	/**
	 * ����һ���ֶε����Ա�
	 * @param fieldNm Ҫ�������Ա���ֶ�����
	 * @param theAttrs ���Ա�
	 */
	public void setFieldAttrs(String fieldNm, Map<String, String> theAttrs);

	/**
	 * ����һ���ֶε�����ֵ
	 * @param fieldNm Ҫ��ȡ���Ե��ֶ�����
	 * @param attrNm ��������
	 * @return ����ֵ
	 */
	public String getFieldAttr(String fieldNm, String attrNm);

	/**
	 * ����һ���ֶε�����ֵ
	 * @param fieldNm Ҫ���������ֶ�����
	 * @param attrNm ��������
	 * @param attrVal ����ֵ
	 */
	public void setFieldAttr(String fieldNm, String attrNm, String attrVal);

	/**
	 * ����м�¼�е��Ӽ�����
	 * @return �Ӽ�����
	 */
	public Map<String, List<IRow>> getAllSubRows();

	/**
	 * �����м�¼���Ӽ�����
	 * @param subRows ��subRows���õ���¼���Ӽ�
	 */
	public void setAllSubRows(Map<String, List<IRow>> subRows);

	/**
	 * �ѵ�ǰ�ж����������ΪMap���󷵻�
	 * @return ����KeyΪ���������ƣ�ValueΪ������ֵ��Map
	 */
	public Map<String, String> writeToMap();

	/**
	 * <p>��һ��Map�����ж�ȡ����</p>
	 * �÷��������õ�ǰ�ж���Ĵ洢
	 * @param rowData ���Ӹö����ж�ȡ����
	 */
	public void readDataFromMap(Map<String, String> rowData);

	/**
	 * ����ֶε�����ֵ
	 * @return �ֶ�ֵ������
	 */
	public List<String> getAllFieldVals();

	/**
	 * �Ƿ�ռ�¼
	 * @return
	 */
	public boolean isEmpty();

	/**
	 * ��������ֶε������б�
	 * @return �����ֶε������б�
	 */
	public Map<String, Map<String, String>> getAllAttrsOfFields();

	/**
	 * ������������ֵ
	 * @param allAttrs �����ֶε������б�
	 */
	public void setAllAttrsOfFields(Map<String, Map<String, String>> allAttrs);

	/**
	 * �������е�������
	 * @param row �Ӹü�¼�����¼
	 */
	public void importRow(IRow row);

}