/**
 * @author gengw
 * Created on Jan 24, 2013
 */
package com.bln.framework.session.store.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.jdbc.template.bean.JdbcTemplate4JavaBean;
import com.bln.framework.persist.session.executor.material.condition.ConditionClause;
import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.info.SessionInfo;

/**
 * 
 */
public class SessionDao {
	
	/**
	 * 数据库连接
	 */
	protected Connection conn = null;
	
	/**
	 * 表名
	 */
	protected String tableName = "T_FW_SESSIONINFO";

//	/**
//	 * buildTableSql
//	 */
//	protected final String buildTableSql = "CREATE TABLE " + tableName + "(SESSIONID VARCHAR(100) not null, SESSIONINFO BLOB, LASTREQDATE DATE)";

	/**
	 * buildTableSql
	 */
	protected final String buildTableSql = "CREATE TABLE " + tableName + "(SESSIONID VARCHAR(100) not null, SESSIONINFO VARBINARY(MAX), LASTREQDATE DATETIME)";

	/**
	 * buildPkSql
	 */
	protected final String buildPkSql = "ALTER TABLE " + tableName + " add constraint " + tableName + "_PK primary key (SESSIONID)";
	
	/**
	 * selectSql
	 */
	protected final String selectSql = "SELECT * FROM " + tableName;

	/**
	 * insertSql
	 */
	protected final String insertSql = "INSERT INTO " + tableName + " (SESSIONID, LASTREQDATE, SESSIONINFO) VALUES(?, ?, ?)";

	/**
	 * deleteSql
	 */
	protected final String deleteSql = "DELETE FROM " + tableName + " WHERE SESSIONID = ?";

	/**
	 * updateSql
	 */
	protected final String updateSql = "UPDATE " + tableName + " SET LASTREQDATE = ?, SESSIONINFO = ? WHERE SESSIONID = ?";

	/**
	 * updateLastReqDateSql
	 */
	protected final String updateLastReqDateSql = "UPDATE " + tableName + " SET LASTREQDATE = ? WHERE SESSIONID = ?";

	
	
	@SuppressWarnings("unchecked")
	public void buildTable() throws SQLException{
		
		JdbcTemplate4JavaBean jt = this.getJT();

		//buildTable
		jt.update(conn, this.buildTableSql, null);
		
		//buildPK
		jt.update(conn, this.buildPkSql, null);
		
	}
	
	/**
	 * 查询记录
	 * @param conClause
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public List<SessionInfo> query(ConditionClause conClause) throws SQLException, IOException{
		
		StringBuilder sql = new StringBuilder(selectSql);
		List<Object> params = null;
		
		new ArrayList<Object>();
		if(conClause != null){
			String con = conClause.getCondtion();
			if(!StringUtils.isEmpty(con)){
				sql.append(" WHERE ").append(con);
			}
			params = conClause.getValues();
		}
		
		JdbcTemplate4JavaBean jt = this.getJT();
		List<SessionInfo> infos = jt.query(conn, sql.toString(), params, null);
		
		return infos;
	}
	
	/**
	 * 根据SessionId查询Session
	 * @param sessionid
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public ISessionInfo queryById(String sessionid) throws SQLException, IOException{
		
		ISessionInfo info = null;
		
		ConditionClause conClause = new ConditionClause();
		conClause.addConValue("SESSIONID = ?", sessionid);
		
		List<SessionInfo> infos = this.query(conClause);
		if(infos != null && infos.size() > 0){
			info = infos.get(0);
		}
		return info;
	}

	/**
	 * 新建记录
	 * @param sessionInfo
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public SessionInfo insert(SessionInfo sessionInfo) throws SQLException, IOException{
		
		StringBuilder sql = new StringBuilder(insertSql);
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(sessionInfo.getSessionid());
		params.add(new Timestamp(sessionInfo.getLastreqdate().getTime()));
		params.add(sessionInfo.getSessioninfo());
		
		JdbcTemplate4JavaBean jt = this.getJT();
		jt.update(conn, sql.toString(), params, null, null);
		
		return sessionInfo;
	}
	
	/**
	 * 更新记录
	 * @param sessionInfo
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ISessionInfo update(ISessionInfo sessionInfo) throws SQLException, IOException{
		
		StringBuilder sql = new StringBuilder(this.updateSql);
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(new Timestamp(sessionInfo.getLastreqdate().getTime()));
		params.add(sessionInfo.getSessioninfo());
		params.add(sessionInfo.getSessionid());
		
		JdbcTemplate4JavaBean jt = this.getJT();
		jt.update(conn, sql.toString(), params, null, null);
		
		return sessionInfo;
	}

	/**
	 * 更新最后访问时间
	 * @param requestDate 访问时间
	 * @return ISessionInfo
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ISessionInfo updateLastRequestDate(ISessionInfo sessionInfo) throws SQLException, IOException{

		StringBuilder sql = new StringBuilder(this.updateLastReqDateSql);
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(new Timestamp(sessionInfo.getLastreqdate().getTime()));
		params.add(sessionInfo.getSessionid());
		
		JdbcTemplate4JavaBean jt = this.getJT();
		jt.update(conn, sql.toString(), params, null, null);
		
		return sessionInfo;
	
	}
	
	/**
	 * 删除记录
	 * @param sessionInfo
	 * @throws SQLException
	 * @throws IOException
	 */
	public void delete(ISessionInfo sessionInfo) throws SQLException, IOException{
		
		this.delete(sessionInfo.getSessionid());
	}

	/**
	 * 删除记录
	 * @param sessionInfo
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void delete(String sessionId) throws SQLException, IOException{
		
		StringBuilder sql = new StringBuilder(deleteSql);
		
		List<Object> params = new ArrayList<Object>();
		params.add(sessionId);
		
		JdbcTemplate4JavaBean jt = this.getJT();
		jt.update(conn, sql.toString(), params, null, null);
	}
	
	/**
	 * 获得jdbcTemplate
	 * @return
	 */
	protected JdbcTemplate4JavaBean getJT(){
		return new JdbcTemplate4JavaBean("com.bln.framework.session.info.SessionInfo");
	}
	
	/**
	 * @param conn
	 * @param tableName
	 */
	public SessionDao(Connection conn, String tableName) {
		super();
		this.conn = conn;
		this.tableName = tableName;
	}

	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
