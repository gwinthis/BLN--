/**
 * @author gengw
 * Created on 2012-03-27
 */
package com.bln.framework.mo.builder;

import com.bln.framework.base.BaseObj;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.IBuildMOable;

/**
 * <p>MO构造器的基类</p>
 * <p>M泛型为消息对象的类型，继承于IMessageObject</p>
 * <p>B泛型为可生成消息对象的类型，继承于IBuildMOable</p>
 */
public abstract class MoBuilderBase<M extends IMessageObject, B extends IBuildMOable> extends BaseObj implements IMoBuilder<M, B>{
	
	/**
	 * MessageObject对象
	 */
	protected M mo = null;
	
	/**
	 * 可生成消息对象的实体的类型
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
