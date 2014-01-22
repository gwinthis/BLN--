package com.bln.framework.filestru.builder.bymo;

import com.bln.framework.filestru.builder.IFileBuilder;
import com.bln.framework.mo.IMessageObject;

public interface IFileBuilderByMo extends IFileBuilder {

	/**
	 * 生成EDIFile
	 * @param IMessageObject 从该对象获取数据生成Edi文件
	 * @return 所生成文件的字节数组
	 */
	public abstract byte[] buildFile(IMessageObject mo)throws Throwable;

}