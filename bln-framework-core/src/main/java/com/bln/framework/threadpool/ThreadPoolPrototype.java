/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bln.framework.util.prototype.PrototypeBase;

/**
 * �̳߳ع���
 */
public class ThreadPoolPrototype extends PrototypeBase<Integer, ExecutorService> implements IThreadPoolPrototype{

	/**
	 * ���캯��
	 */
	public ThreadPoolPrototype(){
		
		//��������
		this.isSingle = true;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.util.prototype.PrototypeBase#newInstance()
	 */
	@Override
	protected ExecutorService newInstance() throws Throwable {
		
		//1.�½�JNDI����
		ExecutorService es = null;
		if(this.property == null){
			es = Executors.newCachedThreadPool();
		}else{
			es = Executors.newFixedThreadPool(property.intValue());
		}
		return es;
	}
}
