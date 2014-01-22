/**
 * @author gengw
 * Created on Jan 21, 2013
 */
package com.bln.framework.net.client.http;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.listener.IObjectListener;

/**
 * ���ӳ�
 */
@SuppressWarnings("deprecation")
public class ConnectionPool implements IObjectListener{
	
	/**
	 * ���������
	 */
	protected int maxTotalConnection = 800;

    /** 
     * ��ȡ���ӵ����ȴ�ʱ�� 
     */  
	protected int waitTimeOut = 60000; 
	
    /** 
     * ÿ��·�����������,���ÿ��Ŀ��� 
     */  
	protected int maxRouteConnection = 400; 
	
    /** 
     * ���ӳ�ʱʱ�� 
     */  
	protected int connectTimeOut = 10000;
	
    /** 
     * ��ȡ��ʱʱ�� 
     */  
	protected int readTimeOut = 10000;
	
	/**
	 * ���ӹ�����
	 */
	protected ClientConnectionManager connectionManager = null;

	/**
	 * HTTP����
	 */
	protected HttpParams httpParams = null;
	
	/**
	 * ��ʼ���ӳ�
	 */
	protected void initPool(){
		
		httpParams = new BasicHttpParams();
		
        // �������������  
        ConnManagerParams.setMaxTotalConnections(httpParams, this.maxTotalConnection); 
        
        // ���û�ȡ���ӵ����ȴ�ʱ��  
        ConnManagerParams.setTimeout(httpParams, this.waitTimeOut);  
        
        // ����ÿ��·�����������  
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(this.maxRouteConnection);  
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams,connPerRoute);
        
        // �������ӳ�ʱʱ��  
        HttpConnectionParams.setConnectionTimeout(httpParams, this.connectTimeOut); 
        
        // ���ö�ȡ��ʱʱ��  
        HttpConnectionParams.setSoTimeout(httpParams, this.readTimeOut);  
  
        SchemeRegistry registry = new SchemeRegistry();  
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));  
  
        connectionManager = new ThreadSafeClientConnManager();  
	        
	}

	public void afterLoad(IBLNFactory factory) {
		// TODO Auto-generated method stub
		
	} 
	
	public void actived(IBLNFactory factory) {
		
	}
	
}
