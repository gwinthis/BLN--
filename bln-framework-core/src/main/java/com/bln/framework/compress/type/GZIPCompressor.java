/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.compress.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

import com.bln.framework.compress.ICompressor;
import com.bln.framework.util.asserter.Assert;

/**
 * GZIP��ѹ����ʽ����
 */
public class GZIPCompressor implements ICompressor{

	/* (non-Javadoc)
	 * @see com.bln.framework.compress.ICompresser#compress(byte[])
	 */
	public byte[] compress(byte[] data) throws IOException {
		
		//У�����
		Assert.paramIsNotNull(data, "data");
		
		//���������
		ByteArrayOutputStream bosResult = new ByteArrayOutputStream();
		
		//��ӵ�ѹ������
		GZIPOutputStream dos = new GZIPOutputStream(bosResult);
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
		GZIPInputStream iis = new GZIPInputStream(bisResult);

		//��ý�ѹ����ֽ�����
		byte[] respData = IOUtils.toByteArray(iis);
		
		//������ѹ
		iis.close();
		
		//���ؽ�ѹ���������
		return respData;
	}

}
