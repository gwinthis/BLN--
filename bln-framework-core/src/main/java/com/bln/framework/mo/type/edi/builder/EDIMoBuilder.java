/**
 * @author gengw
 * Created on 2012-03-27
 */
package com.bln.framework.mo.type.edi.builder;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.IMessageObjectEdi;
import com.bln.framework.mo.buildable.IBuildMOable;
import com.bln.framework.mo.builder.MoBuilderBase;

/**
 * ʹ�ÿͻ��˱��Ĵ洢����Ϣ��������
 */
public class EDIMoBuilder extends MoBuilderBase<IMessageObjectEdi<?>, IBuildMOable>{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.builder.IMoBuilder#buildMo(byte[])
	 */
	public IMessageObject buildMo(byte[] bytes) throws Throwable {
		
		//2.��ȡEDI����
		IMessageObjectEdi<?> mo = (IMessageObjectEdi<?>)this.mo.emptyMo();
		
		mo.readFromData(bytes);
		
		//������Ϣ����
		return mo;
	}
}
