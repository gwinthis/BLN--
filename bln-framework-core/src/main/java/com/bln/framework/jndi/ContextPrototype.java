/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.jndi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.bln.framework.util.prototype.PrototypeBase;

/**
 * �����������Ķ���ԭ��
 */
public class ContextPrototype extends PrototypeBase<Properties, Context> implements IContextPrototype{
	
	/**
	 * ���캯��
	 */
	public ContextPrototype(){
		
		//��������
		this.isSingle = true;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.util.prototype.PrototypeBase#newInstance()
	 */
	@Override
	protected Context newInstance() throws Throwable {
		
		//1.�½�JNDI����
		Context context = null;
		if(this.property == null){
			context = new InitialContext();
		}else{
			context = new InitialContext(property);
		}
		return context;
	}

}
