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
 * 连接池
 */
@SuppressWarnings("deprecation")
public class ConnectionPool implements IObjectListener{
	
	/**
	 * 最大连接数
	 */
	protected int maxTotalConnection = 800;

    /** 
     * 获取连接的最大等待时间 
     */  
	protected int waitTimeOut = 60000; 
	
    /** 
     * 每个路由最大连接数,针对每个目标机 
     */  
	protected int maxRouteConnection = 400; 
	
    /** 
     * 连接超时时间 
     */  
	protected int connectTimeOut = 10000;
	
    /** 
     * 读取超时时间 
     */  
	protected int readTimeOut = 10000;
	
	/**
	 * 连接管理器
	 */
	protected ClientConnectionManager connectionManager = null;

	/**
	 * HTTP参数
	 */
	protected HttpParams httpParams = null;
	
	/**
	 * 初始连接池
	 */
	protected void initPool(){
		
		httpParams = new BasicHttpParams();
		
        // 设置最大连接数  
        ConnManagerParams.setMaxTotalConnections(httpParams, this.maxTotalConnection); 
        
        // 设置获取连接的最大等待时间  
        ConnManagerParams.setTimeout(httpParams, this.waitTimeOut);  
        
        // 设置每个路由最大连接数  
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(this.maxRouteConnection);  
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams,connPerRoute);
        
        // 设置连接超时时间  
        HttpConnectionParams.setConnectionTimeout(httpParams, this.connectTimeOut); 
        
        // 设置读取超时时间  
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
