/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.factory.ioc;

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

}
