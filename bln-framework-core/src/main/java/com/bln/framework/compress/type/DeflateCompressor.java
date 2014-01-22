/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.compress.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

import com.bln.framework.compress.ICompressor;
import com.bln.framework.util.asserter.Assert;

/**
 * Deflate��ѹ����ʽ����
 */
public class DeflateCompressor implements ICompressor{

	/* (non-Javadoc)
	 * @see com.bln.framework.compress.ICompresser#compress(byte[])
	 */
	public byte[] compress(byte[] data) throws IOException {
		
		//У�����
		Assert.paramIsNotNull(data, "data");
		
		//���������
		ByteArrayOutputStream bosResult = new ByteArrayOutputStream();
		
		//��ӵ�ѹ������
		DeflaterOutputStream dos = new DeflaterOutputStream(bosResult);
		dos.write(data);
		
		//�������
		dos.finish();
		
		dos.flush();
		dos.close();
		
		//����ѹ���������
		return bosResult.toByteArray();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.compress.ICompresser#decompress(byte[])
	 */
	public byte[] decompress(byte[] reqData) throws IOException {
		
		//У�����
		Assert.paramIsNotNull(reqData, "data");
		
		//����������
		ByteArrayInputStream bisResult = new ByteArrayInputStream(reqData);
		
		//��ӵ���ѹ����
		InflaterInputStream iis = new InflaterInputStream(bisResult);

		//��ý�ѹ����ֽ�����
		byte[] respData = IOUtils.toByteArray(iis);
		
		//������ѹ
		iis.close();
		
		//���ؽ�ѹ���������
		return respData;
	}

}
