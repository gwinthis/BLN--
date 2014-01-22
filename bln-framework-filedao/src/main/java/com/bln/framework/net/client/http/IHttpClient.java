package com.bln.framework.net.client.http;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface IHttpClient {

	/**
	 * post����
	 * @param bytes Ҫ���͵�����
	 * @return ��Ӧ����
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public abstract byte[] post(byte[] bytes) throws ClientProtocolException,
			IOException;

	/**
	 * ��������POST����
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public abstract byte[] postParams(Map<String, Object> params)
			throws ClientProtocolException, IOException;

	/**
	 * MultiPart������ʽ����
	 * @param params ����
	 * @return ��Ӧ����
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public abstract byte[] postMultiParams(Map<String, Object> params)
			throws ClientProtocolException, IOException;

	/**
	 * @return the url
	 */
	public abstract String getUrl();

	/**
	 * @param url the url to set
	 */
	public abstract void setUrl(String url);

	/**
	 * @return the encoding
	 */
	public abstract String getEncoding();

	/**
	 * @param encoding the encoding to set
	 */
	public abstract void setEncoding(String encoding);

	/**
	 * http get��������
	 * @param params �������
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public byte[] get(Map<String, Object> params) throws ClientProtocolException, IOException;

}