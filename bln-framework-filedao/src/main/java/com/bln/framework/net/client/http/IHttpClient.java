package com.bln.framework.net.client.http;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface IHttpClient {

	/**
	 * post请求
	 * @param bytes 要发送的数据
	 * @return 响应数据
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public abstract byte[] post(byte[] bytes) throws ClientProtocolException,
			IOException;

	/**
	 * 按参数做POST请求
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public abstract byte[] postParams(Map<String, Object> params)
			throws ClientProtocolException, IOException;

	/**
	 * MultiPart参数方式请求
	 * @param params 参数
	 * @return 响应数据
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
	 * http get方法请求
	 * @param params 请求参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public byte[] get(Map<String, Object> params) throws ClientProtocolException, IOException;

}