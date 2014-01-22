/**
 * @author gengw
 * Created on Apr 24, 2012
 */
package com.bln.framework.persist.session.executor.row;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.SessionExecutorBase;


/**
 * 使用行对象的Session执行器基类
 */
public abstract class SessionExecutorRowBase<T extends SessionExecutorRowBase<T> > extends SessionExecutorBase<T, IRow>{
	
}
