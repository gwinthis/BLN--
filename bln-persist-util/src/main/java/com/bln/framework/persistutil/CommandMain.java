/**
 * @author gengw
 * Created on May 25, 2012
 */
package com.bln.framework.persistutil;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.TaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;
import com.bln.framework.persistutil.task.executor.ITaskExecutor;
import com.bln.framework.persistutil.task.executor.TaskExecutorBase;

/**
 * 命令行应用入口
 */
public class CommandMain {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args){
		
		try {
			if(args == null || args.length <= 0){
				throw new IllegalArgumentException("please set task list file url!");
			}
			
			//加载日志输出器
			BasicConfigurator.configure();
			
			//1.读取配置文件
			
			//获取配置文件路径
			String taskUrl = args[0];
			
			ITaskConfig taskConfig = new TaskConfig();
			taskConfig.readFromData(IOUtils.toByteArray(new FileInputStream(taskUrl)));
			
			//2.根据DBMS获取任务执行器
			ITaskConfigEntity dbmsEntity = taskConfig.getDBMS();
			String dbms = dbmsEntity.getAttr(ITaskConfig.ATTR_NAME).toUpperCase();
			
			ITaskExecutor taskExecutor = TaskExecutorBase.getTaskExecutor(dbms);
			
			//3.执行任务
			
			//设置持久层工厂
			BLNFactoryCenter.singleton().loadAllFactory();
			IBLNFactory persistFactory = BLNFactoryCenter.singleton().getFactory("persist4Util" + dbms);
			taskExecutor.setPersistFactory(persistFactory);

			//执行任务
			taskExecutor.execute(taskConfig);
			
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
			return;
		}
		
		System.exit(0);
	}

}
