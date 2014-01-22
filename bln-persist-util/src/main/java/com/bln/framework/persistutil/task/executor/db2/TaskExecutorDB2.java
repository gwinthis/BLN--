package com.bln.framework.persistutil.task.executor.db2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.util.factory.PersistUtils;
import com.bln.framework.persistutil.executebuilder.IExecuteBuilder;
import com.bln.framework.persistutil.executebuilder.stru.xml.builder.db2.TableStruXmlBuilderDB2;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;
import com.bln.framework.persistutil.task.executor.TaskExecutorBase;

public class TaskExecutorDB2 extends TaskExecutorBase{

	public TaskExecutorDB2(){
		super();
		executeBuilderMap.put(IExecuteBuilder.EXECUTE_BUILDSTRU, new TableStruXmlBuilderDB2());
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.task.executor.TaskExecutorBase#tables(com.bln.framework.persist.session.ISession, com.bln.framework.persistutil.task.entity.ITaskConfigEntity)
	 */
	@Override
	protected List<IRow> tables(ISession session, ITaskConfigEntity tableEntity, ITaskConfigEntity schemaEntity) throws SQLException, IOException {
		
		String sql = " SELECT \"TABNAME\" AS \"TABLE_NAME\", \"OWNER\", \"REMARKS\" from \"SYSCAT\".\"TABLES\" WHERE 1 = 1";
		
		//schema
		String schemaName = schemaEntity.getAttr(ITaskConfig.ATTR_NAME);
		if(!StringUtils.isEmpty(schemaName)){
			sql += " AND OWNER = '" + schemaName + "'";
		}
		
		//tablenames
		String tableName = tableEntity.getAttr(ITaskConfig.ATTR_NAME);
		if(!StringUtils.isEmpty(tableName) && !"*".equals(tableName)){
			tableName = tableName.replace(",", "','");
			tableName = "'" + tableName + "'";
			sql += " AND TABNAME in (" + tableName + ")";
		}
		
		sql += " ORDER BY TABLE_NAME";
		
		List<IRow> tables = PersistUtils.querySimple(session, sql);
		
		return tables;
	}

}
