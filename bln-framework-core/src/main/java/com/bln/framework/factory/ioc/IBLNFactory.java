/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.factory.ioc;

import java.util.List;

import com.bln.framework.factory.IFactory;

/**
 * BLN�����Ľӿ�
 */
public interface IBLNFactory extends IFactory<Object>{
	
	/**
	 * ��ù�������
	 * @return the factoryName
	 */
	public String getFactoryName();

	/**
	 * ��ȡ���ж���·����
	 * @return ���ж���·����
	 */
	public abstract List<String> getObjectNames();

}
