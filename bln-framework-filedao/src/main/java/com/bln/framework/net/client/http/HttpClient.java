/**
 * @author gengw
 * Created on May 23, 2012
 */
package com.bln.framework.net.client.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.bln.framework.exception.UnsupportedException;

/**
 * HTTP客户端
 */
public class HttpClient implements IHttpClient{
	
	/**
	 * 访问地址
	 */
	String url = null;
	
	/**
	 * 字符集编码
	 */
	String encoding = null;

	/**
	 * 构造函数
	 */
	public HttpClient() {
		super();
	}
	
	/**
	 * 构造函数
	 * @param url
	 * @param encoding
	 */
	public HttpClient(String url, String encoding) {
		super();
		this.url = url;
		this.encoding = encoding;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#get(java.util.Map)
	 */
	public byte[] get(Map<String, Object> params) throws ClientProtocolException, IOException{
		HttpGet request = new HttpGet(url);
		if(params != null && !params.isEmpty()){
			HttpParams basicParams = new BasicHttpParams();

			for ( Map.Entry<String, Object> param : params.entrySet()){
				basicParams.setParameter(param.getKey(), param.getValue());
			}
		}
		return this.executeRequest(request);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#post(byte[])
	 */
	public byte[] post(byte[] bytes) throws ClientProtocolException, IOException{
		
		HttpEntity entity = new ByteArrayEntity(bytes);
		return this.executePostRequest(null, entity);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#postParams(java.util.Map)
	 */
	public byte[] postParams(Map<String, Object> params) throws ClientProtocolException, IOException{
		
		HttpPost request = new HttpPost(url);
		if(params != null && !params.isEmpty()){
			HttpParams basicParams = new BasicHttpParams();

			for ( Map.Entry<String, Object> param : params.entrySet()){
				basicParams.setParameter(param.getKey(), param.getValue());
			}
		}
		return this.executeRequest(request);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#postMultiParams(java.util.Map)
	 */
	public byte[] postMultiParams(Map<String, Object> params) throws ClientProtocolException, IOException{
		
		MultipartEntity entity = new MultipartEntity();
		if(params != null && !params.isEmpty()){
			for ( Map.Entry<String, Object> paramEntry : params.entrySet()){
				String paramName = paramEntry.getKey();
				ContentBody content = createContent(paramEntry.getValue());
				
				entity.addPart(paramName, content);
			}
		}
		
		return this.executePostRequest(null, entity);
	}
	
	/**
	 * 创建Mulitpart内容
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected ContentBody createContent(Object value) throws UnsupportedEncodingException{
		if(value == null){
			return null;
		}

		ContentBody content = null;
		if(value instanceof File){
			content = new FileBody((File)value);
		}else if (value instanceof String){
			content = new StringBody((String)value);
		}else{	
			throw new UnsupportedException();
		}
		
		return content;
		
	}
	
	/**
	 * 执行Post请求
	 * @param entity
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected byte[] executePostRequest(Header[] headers, HttpEntity entity) throws ClientProtocolException, IOException{

		HttpPost request = new HttpPost(url);
		request.setEntity(entity);
		request.setHeaders(headers);
		
		return executeRequest(request);
	}
	
	/**
	 * 执行Get请求
	 * @param headers 协议头信息
	 * @param params 请求参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected byte[] executeGetRequest(Header[] headers, HttpParams params) throws ClientProtocolException, IOException{

		HttpGet request = new HttpGet(url);
		request.setParams(params);
		request.setHeaders(headers);
		
		return executeRequest(request);
	}
	
	/**
	 * 执行请求,获取content数据
	 * @param request 请求对象
	 * @return 响应信息
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected byte[] executeRequest(HttpUriRequest request) throws ClientProtocolException, IOException{

		org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
		
		byte[] respBytes = null;
		InputStream in = null;
		
		try {
			
			HttpResponse response = httpClient.execute(request);
			
			HttpEntity resEntity = response.getEntity();
			if(resEntity != null){
				in = resEntity.getContent();
				respBytes = IOUtils.toByteArray(in);
			}
			
		} finally {
			if(in != null){
				in.close();
			}
			httpClient.getConnectionManager().shutdown();
		}
		return respBytes;
	}

	/**
	 * 执行请求,获取content数据
	 * @param request 请求对象
	 * @return 响应信息
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected byte[] executeRequestForHeader(HttpUriRequest request) throws ClientProtocolException, IOException{

		org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
		
		byte[] respBytes = null;
		InputStream in = null;
		
		try {
			
			HttpResponse response = httpClient.execute(request);
			
			HttpEntity resEntity = response.getEntity();
			if(resEntity != null){
				in = resEntity.getContent();
				respBytes = IOUtils.toByteArray(in);
			}
			
		} finally {
			if(in != null){
				in.close();
			}
			httpClient.getConnectionManager().shutdown();
		}
		return respBytes;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#getUrl()
	 */
	public String getUrl() {
		return url;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#setUrl(java.lang.String)
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#getEncoding()
	 */
	public String getEncoding() {
		return encoding;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.http.IHttpClient#setEncoding(java.lang.String)
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
