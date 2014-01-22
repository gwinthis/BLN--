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
 * 使用客户端报文存储的消息对象构造器
 */
public class EDIMoBuilder extends MoBuilderBase<IMessageObjectEdi<?>, IBuildMOable>{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.builder.IMoBuilder#buildMo(byte[])
	 */
	public IMessageObject buildMo(byte[] bytes) throws Throwable {
		
		//2.读取EDI数据
		IMessageObjectEdi<?> mo = (IMessageObjectEdi<?>)this.mo.emptyMo();
		
		mo.readFromData(bytes);
		
		//返沪消息对象
		return mo;
	}
}
