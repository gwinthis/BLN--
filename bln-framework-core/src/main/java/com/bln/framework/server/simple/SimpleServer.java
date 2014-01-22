/**
 * @author gengw
 * Created on Jan 20, 2013
 */
package com.bln.framework.server.simple;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.listener.IObjectListener;

/**
 * 简单的服务器，为Runnable对象提供运行服务
 */
public class SimpleServer implements IObjectListener, ISimpleServer{
	
	/**
	 * 工人对象
	 */
	protected Runnable worker = null;
	
	/**
	 * 工人数量默认为1
	 */
	protected int workerNumbers = 1;
	
	/**
	 * 服务名称
	 */
	protected String serverName = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.server.simple.ISimpleServer#work()
	 */
	public void work(){
		
		//1.初始化线程
		Thread[] threads = new Thread[workerNumbers];
		for ( int i = 0, n = threads.length; i < n; i++){
			Thread t = new Thread(worker);
			threads[i] = t;
			
		}
		
		//2.启动线程
		for(int i = 0, n = threads.length; i < n; i++){
			threads[i].start();
		}
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.listener.IObjectListener#afterLoad(com.bln.framework.factory.ioc.IBLNFactory)
	 */
	public void afterLoad(IBLNFactory factory) {
		this.work();
	}
	
	public void actived(IBLNFactory factory) {
		
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.server.simple.ISimpleServer#getWorkerNumbers()
	 */
	public int getWorkerNumbers() {
		return workerNumbers;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.server.simple.ISimpleServer#setWorkerNumbers(int)
	 */
	public void setWorkerNumbers(int workerNumbers) {
		this.workerNumbers = workerNumbers;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.server.simple.ISimpleServer#getWorker()
	 */
	public Runnable getWorker() {
		return worker;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.server.simple.ISimpleServer#setWorker(java.lang.Runnable)
	 */
	public void setWorker(Runnable worker) {
		this.worker = worker;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
