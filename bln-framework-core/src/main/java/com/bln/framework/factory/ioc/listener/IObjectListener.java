/**
 * @author gengw
 * Created on Jan 20, 2013
 */
package com.bln.framework.factory.ioc.listener;

import com.bln.framework.factory.ioc.IBLNFactory;

/**
 * IOC�������йܶ���ļ�����
 */
public interface IObjectListener {
	
	/**
	 * load֮����¼�
	 */
	public void afterLoad(IBLNFactory factory);
	
	/**
	 * �ö�����Ҫʹ��
	 */
	public void actived(IBLNFactory factory);

}
