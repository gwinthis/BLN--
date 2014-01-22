package com.bln.framework.persistutil.task.executor.oracle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.util.factory.PersistUtils;
import com.bln.framework.persistutil.executebuilder.IExecuteBuilder;
import com.bln.framework.persistutil.executebuilder.stru.xml.builder.oracle.TableStruXmlBuilderOracle;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;
import com.bln.framework.persistutil.task.executor.TaskExecutorBase;

public class TaskExecutorOracle extends TaskExecutorBase{

	public TaskExecutorOracle(){
		super();
		executeBuilderMap.put(IExecuteBuilder.EXECUTE_BUILDSTRU, new TableStruXmlBuilderOracle());
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.task.executor.TaskExecutorBase#tables(com.bln.framework.persist.session.ISession, com.bln.framework.persistutil.task.entity.ITaskConfigEntity)
	 */
	@Override
	protected List<IRow> tables(ISession session, ITaskConfigEntity tableEntity, ITaskConfigEntity schemaEntity) throws SQLException, IOException {
		
		String sql = "select TABLE_NAME, TABLESPACE_NAME, TEMPORARY FROM USER_TABLES";

		String tableName = tableEntity.getAttr(ITaskConfig.ATTR_NAME);
		if(!StringUtils.isEmpty(tableName) && !"*".equals(tableName)){
			tableName = tableName.replace(",", "','");
			tableName = "'" + tableName + "'";
			sql += " WHERE TABLE_NAME in (" + tableName + ")";
		}
		
		
		sql += " ORDER BY TABLE_NAME";
		
		List<IRow> tables = PersistUtils.querySimple(session, sql);
		
		return tables;
	}

}
