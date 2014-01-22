/**
 * @author gengw
 * Created on 2012-03-27
 */
package com.bln.framework.mo.builder;

import com.bln.framework.base.BaseObj;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.IBuildMOable;

/**
 * <p>MO�������Ļ���</p>
 * <p>M����Ϊ��Ϣ��������ͣ��̳���IMessageObject</p>
 * <p>B����Ϊ��������Ϣ��������ͣ��̳���IBuildMOable</p>
 */
public abstract class MoBuilderBase<M extends IMessageObject, B extends IBuildMOable> extends BaseObj implements IMoBuilder<M, B>{
	
	/**
	 * MessageObject����
	 */
	protected M mo = null;
	
	/**
	 * ��������Ϣ�����ʵ�������
	 */
	protected B buildMOable = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMoBuilder#getMo()
	 */
	public M getMo() {
		return mo;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMoBuilder#setMo(com.bln.framework.mo.IMessageObject)
	 */
	public void setMo(M mo) {
		this.mo = mo;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMoBuilder#getBuildMoAble()
	 */
	public B getBuildMOable() {
		return buildMOable;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMoBuilder#setBuildMoAble(com.bln.framework.mo.IBuildMOable)
	 */
	public void setBuildMOable(B buildMOable) {
		this.buildMOable = buildMOable;
	}

}
