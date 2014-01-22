package com.bln.framework.persistutil.task.executor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.factory.ISessionFactory;
import com.bln.framework.persistutil.executebuilder.IExecuteBuilder;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;
import com.bln.framework.persistutil.task.executor.db2.TaskExecutorDB2;
import com.bln.framework.persistutil.task.executor.mssql2008.TaskExecutorMSSQL2008;
import com.bln.framework.persistutil.task.executor.oracle.TaskExecutorOracle;

public abstract class TaskExecutorBase implements ITaskExecutor {
	
	
	/**
	 * ִ�й�����
	 */
	protected Map<String, IExecuteBuilder> executeBuilderMap = new HashMap<String, IExecuteBuilder>();
	
	/**
	 * ����ִ����Map
	 */
	protected static Map<String, ITaskExecutor> taskExecutorMap = null;
	
	static{
		taskExecutorMap = new HashMap<String, ITaskExecutor>();
		taskExecutorMap.put("ORACLE", new TaskExecutorOracle());
		taskExecutorMap.put("DB2", new TaskExecutorDB2());
		taskExecutorMap.put("MSSQL2008", new TaskExecutorMSSQL2008());
	}
	
	public TaskExecutorBase(){
		//executeBuilderMap.put(IExecuteBuilder.EXECUTE_BUILDSTRULIB, new TslibBuilder());
	}

	
	/**
	 * @param dbms
	 * @return
	 */
	public static ITaskExecutor getTaskExecutor(String dbms){
		return taskExecutorMap.get(dbms.toUpperCase());
	}
	
	//protected static Map<String, IExecuteBuilder>
	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.task.executor.ITaskExecutor#execute(com.bln.framework.persistutil.task.ITaskConfig)
	 */
	public void execute(ITaskConfig taskConfig) throws Throwable{
		
		//1.�������ݿ�����
		Connection connection = getConnection(taskConfig.getDbConnection());
		
		try {
			
			//2.��ȡSession
			ISessionFactory sessionFactory = (ISessionFactory) persistFactory.getInstance("session.factory.Simple");
			ISession session = sessionFactory.openSession(connection);
			
			//3.��ñ�
			
			ITaskConfigEntity schemaEntity = taskConfig.getSchema();
			ITaskConfigEntity tableEntity = taskConfig.getTable();
			
			List<IRow> tables = this.tables(session, tableEntity, schemaEntity);
			
			//4.��������
			List<ITaskConfigEntity> executes = taskConfig.getExecutes();
			if (executes != null && !executes.isEmpty()) {
				for (ITaskConfigEntity entity : executes) {
					String executeName = entity.getAttr(ITaskConfig.ATTR_NAME);
					IExecuteBuilder executeBuilder = executeBuilderMap.get(executeName);
					if (executeBuilder != null) {
						executeBuilder.setSession(session);
						executeBuilder.setTables(tables);
						executeBuilder.setTaskConfig(taskConfig);
						executeBuilder.setExecuteEnity(entity);
						
						executeBuilder.build();
					}
				}
			}
		} catch (Throwable e) {
			throw e;
		} finally{
			connection.close();
		}
		
		
	}

	/**
	 * ��ñ�������ֶ�
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected abstract List<IRow> tables(ISession session, ITaskConfigEntity tableEntity, ITaskConfigEntity schemaEntity) throws SQLException, IOException;

	/**
	 * �־ò㹤��
	 */
	protected IBLNFactory persistFactory = null;
	
	/**
	 * ��ȡ����
	 * @param dbConfig
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	protected Connection getConnection(ITaskConfigEntity dbConfig) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		//1ע������
		String driverClass = dbConfig.getAttr(ITaskConfig.ATTR_DRIVERCLASS);
		
		Driver driver = (Driver) Class.forName(driverClass).newInstance();
		DriverManager.registerDriver(driver);
		
		//2��ȡ����
		String connectionUrl = dbConfig.getAttr(ITaskConfig.ATTR_CONNECTIONURL);
		String user = dbConfig.getAttr(ITaskConfig.ATTR_USER);
		String password = dbConfig.getAttr(ITaskConfig.ATTR_PASSWORD);
		
		Connection conn = DriverManager.getConnection(connectionUrl, user, password);
		
		//3.��������
		return conn;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.task.executor.ITaskExecutor#getPersistFactory()
	 */
	public IBLNFactory getPersistFactory() {
		return persistFactory;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.task.executor.ITaskExecutor#setPersistFactory(com.bln.framework.factory.ioc.IBLNFactory)
	 */
	public void setPersistFactory(IBLNFactory persistFactory) {
		this.persistFactory = persistFactory;
	}

}
