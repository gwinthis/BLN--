package com.bln.framework.filestru.builder.bymo;

import com.bln.framework.filestru.builder.IFileBuilder;
import com.bln.framework.mo.IMessageObject;

public interface IFileBuilderByMo extends IFileBuilder {

	/**
	 * ����EDIFile
	 * @param IMessageObject �Ӹö����ȡ��������Edi�ļ�
	 * @return �������ļ����ֽ�����
	 */
	public abstract byte[] buildFile(IMessageObject mo)throws Throwable;

}