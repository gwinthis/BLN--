/**
 * @author gengw
 * Created at 2012-03-20
 */
package com.bln.framework.base;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.bln.framework.config.IAppConfig;

/**
 * �������
 */
public class BaseObj {

	/**
	 * Ӧ������
	 */
	protected IAppConfig appConfig = null;

	/**
	 * ���ص�ǰ���ڵ��ı�
	 * @return ��ǰ����ʱ��
	 */
	protected String getCurrentDateAsString(){
		String now = DateFormatUtils.format(new Date(), appConfig.getDateFormat());
		return now;
	}
	
	/**
	 * ���ص�ǰ����ʱ����ı�
	 * @return ��ǰ����ʱ��
	 */
	protected String getCurrentDateTimeAsString(){
		String now = DateFormatUtils.format(new Date(), appConfig.getDatetimeFormat());
		return now;
	}

	/**
	 * @return the appConfig
	 */
	public IAppConfig getAppConfig() {
		return appConfig;
	}

	/**
	 * @param appConfig the appConfig to set
	 */
	public void setAppConfig(IAppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("unchecked")
	public String toString(){
		StringBuilder info = new StringBuilder( this.getClass().getSimpleName() + " info: ");
		
		try {
			Map<String, String> spec = BeanUtils.describe(this);
			info.append(spec);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return info.toString();
	}
}
