package com.bln.framework.persist.jdbc.template;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.jdbc.converter.exception.ConvertException;
import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.jdbc.template.exception.ExecuteJdbcException;
import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.tablestru.component.column.Column;
import com.bln.framework.persist.tablestru.component.column.IColumn;


/**
 * JDBC模板基类
 * @param <T>
 */
public abstract class JdbcTemplateBase<T> extends BaseObj implements IJdbcTemplate<T> {

	/**
	 * 转换器工具
	 */
	protected IConverterUtil converterUtil = null;
	
	/**
	 * @param converterUtil the converterUtil to set
	 */
	public void setConverterUtil(IConverterUtil converterUtil) {
		this.converterUtil = converterUtil;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbctemplate.IJdbcTemplate#query(java.lang.String, java.lang.Object[], java.lang.String[], int[])
	 */
	public List<T> query(Connection conn, String sql, List<Object> params, IColumn[] columns) throws SQLException, IOException{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			//预编译SQL语句
			pstmt = conn.prepareStatement(sql);
			
			//赋值参数
			if(params != null){
				for(int i = 0, n = params.size(); i < n; i ++){
					pstmt.setObject(i + 1, params.get(i));
				}				
			}
			
			//执行SQL语句
			rs = pstmt.executeQuery();
			return resultSet2List(rs, columns);
			
		}catch(Throwable e){
			
			ExecuteJdbcException eje = new ExecuteJdbcException(" when execute jdbc, occurs error!");
			eje.initCause(e);
			
			eje.addContextValue("conn", conn);
			eje.addContextValue("sql", sql);
			eje.addContextValue("params", params);
			eje.addContextValue("columns", columns);
			
			throw eje;
			
		}finally{
			
			if(rs != null){
				rs.close();
				rs = null;
			}
			
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}

		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.core.IJdbcTemplate#queryForObj(java.lang.String, java.lang.Object[])
	 */
	public String queryForString(Connection conn, String sql, List<Object> params) throws SQLException{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
				
			//预编译SQL语句
			pstmt = conn.prepareStatement(sql);
			
			//赋值参数
			if(params != null){
				for(int i = 0, n = params.size(); i < n; i ++){
					pstmt.setObject(i + 1, params.get(i));
				}				
			}
			
			//执行SQL语句
			rs = pstmt.executeQuery();
			
			//获取查询结构
			IColumn[] columns = this.fetchQueryColumnMeta(rs);
			
			//获取值
			String info = null;
			if(rs.next()){
				info = this.columnValueToString(rs, 1, columns[0].getColType());
			}
			
			return info;
			
		}catch(Throwable e){
			
			ExecuteJdbcException eje = new ExecuteJdbcException(" when execute jdbc, occurs error!");
			eje.initCause(e);
			eje.addContextValue("sql", sql);
			eje.addContextValue("params", params);
			
			throw eje;	
		}finally{
			
			if(rs != null){
				rs.close();
				rs = null;
			}
			
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}

		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.core.IJdbcTemplate#update(java.lang.String, java.lang.Object[])
	 */
	public int update(Connection conn, String sql, List<Object> params) throws SQLException{
		return update(conn, sql, params, null, null);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.core.IJdbcTemplate#update(java.lang.String, java.lang.Object[], boolean)
	 */
	public int update(Connection conn, String sql, List<Object> params, int[] generatedCols, Object[] generatedVals) throws SQLException{
				
		PreparedStatement pstmt = null;
		
		int idx = 0;
		try{
			
			if(generatedCols == null || generatedCols.length <=0 ){
				pstmt = conn.prepareStatement(sql);
			}else{
				pstmt = conn.prepareStatement(sql, generatedCols);
			}
			
			//赋值参数
			if(params != null){
				for(int n = params.size(); idx < n; idx ++){
					pstmt.setObject(idx + 1, params.get(idx));
				}
			}

			//执行SQL语句	
			int succCount = pstmt.executeUpdate();
			
			//如果需要生成数据字段，获取数据库生成的字段
			if(generatedCols!= null && generatedCols.length > 0){
				//获取数据库生成的值
				ResultSet rs = null;
				try{
					rs = pstmt.getGeneratedKeys();
					if(rs.next()){				
						for ( int i = 0; i < generatedCols.length; i ++){
							generatedVals[i] = rs.getObject(i + 1);
						}
					}
				}finally{
					if(rs != null){
						rs.close();
						rs = null;
					}
				}				
			}
			
			
			return succCount;
			
		}catch(Throwable e){
			
			ExecuteJdbcException eje = new ExecuteJdbcException(" when execute jdbc update operation, occurs error!");
			eje.initCause(e);
			eje.addContextValue("sql", sql);
			eje.addContextValue("params", params);
			eje.addContextValue("index of param", idx);
			eje.addContextValue("generatedCols", Arrays.toString(generatedCols));
			eje.addContextValue("generatedVals", generatedVals);
			
			throw eje;
			
		}finally{
						
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}

		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.core.IJdbcTemplate#updateBatch(java.lang.String[])
	 */
	public int[] updateBatch(Connection conn, String[] sqls) throws SQLException{

		Statement stmt = null;
		
		try{
						
			//生成语句
			stmt = conn.createStatement();
			
			for ( int i = 0, n = sqls.length; i < n; i ++){
				stmt.addBatch(sqls[i]);
			}			
			//执行SQL语句			
			return stmt.executeBatch();
			
		}catch(Throwable e){
			
			ExecuteJdbcException eje = new ExecuteJdbcException(" when execute jdbc updateBatch operation, occurs error!");
			eje.initCause(e);
			eje.addContextValue("sqls", Arrays.toString(sqls));
			
			throw eje;
			
		}finally{
						
			if(stmt != null){
				stmt.close();
				stmt = null;
			}

		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbctemplate.IJdbcTemplate#updateBatch(java.sql.Connection, java.lang.String, java.util.List)
	 */
	public int[] updateBatch(Connection conn, String sql, List<List<Object>> paramsList) throws SQLException{
		
		PreparedStatement pstmt = null;
		
		try{
						
			//预编译SQL语句
			pstmt = conn.prepareStatement(sql);
			
			//赋值参数			
			if(paramsList != null){
				for ( int i = 0, n = paramsList.size(); i < n; i ++){
					List<Object> params = paramsList.get(i);
					if(params != null){
						for(int j = 0, m = params.size(); j < m; j ++){
							pstmt.setObject(j + 1, params.get(j));
						}				
					}
					
					pstmt.addBatch();
					pstmt.clearParameters();
				}
			}
			
			//批量执行SQL语句			
			return pstmt.executeBatch();
			
		}catch(Throwable e){
			
			ExecuteJdbcException eje = new ExecuteJdbcException(" when execute jdbc updateBatch operation, occurs error!");
			eje.initCause(e);
			eje.addContextValue("sqls", sql);
			eje.addContextValue("paramsList", paramsList);
			
			throw eje;
			
		}finally{
						
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}

		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.template.IJdbcTemplate#executeStoreProcedureSimple(java.sql.Connection, java.lang.String, com.bln.framework.persist.jdbc.template.storeprocedure.ISPParameters)
	 */
	public StoreProcedureResult<T> executeStoreProcedureSimple(Connection conn, String spCommand, IStoreProcedureParameters spParameters) throws SQLException, IOException{
		
		StringBuilder paramSigns = new StringBuilder();
		if(spParameters != null && spParameters.getParameters() != null){
			int length = spParameters.getParameters().size();
			for ( int i = 0; i < length; i++ ){
				if(i != 0 ){
					paramSigns.append(",");
				}
				paramSigns.append("?");
			}
		}
		
		spCommand = "{call " + spCommand + "(" + paramSigns + ")}";
		return this.executeStoreProcedure(conn, spCommand, spParameters);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbctemplate.IJdbcTemplate#excuteSP(java.lang.String, com.bln.framework.persist.core.SPParameters)
	 */
	public StoreProcedureResult<T> executeStoreProcedure(Connection conn, String spCommand, IStoreProcedureParameters spParameters) throws SQLException, IOException{
		
		CallableStatement cstmt = null;

		try{
						
			//1.预编译存储过程命令
			cstmt = conn.prepareCall(spCommand);
			
			//2.设置参数
			List<StoreProcedureParameters.SPParameter> parameters = null;
			if(spParameters != null){
				parameters = spParameters.getParameters();
			}
			
			if(parameters != null && parameters.size() > 0){
				for ( int i = 0, n = parameters.size(); i < n; i++){
					StoreProcedureParameters.SPParameter parameter = parameters.get(i);
					if(IStoreProcedureParameters.PARAM_IN == parameter.getIntout()){
						cstmt.setObject(i + 1, parameter.getValue());
					}else{
						cstmt.registerOutParameter(i + 1, parameter.getParamType());
					}
				}					
			}
			
			//4.执行存储过程
			boolean hasResult = cstmt.execute();
			
			//5.处理返回结果
			StoreProcedureResult<T> spResult = new StoreProcedureResult<T>();
			
			//5.1处理返回值
			if(!hasResult){
				spResult.setUpdateCount(cstmt.getUpdateCount());
			}else{
				ResultSet rs = cstmt.getResultSet();
				try{
					List<T> rows = resultSet2List(rs, null);
					spResult.setResultSetRows(rows);
				}finally{
					if(rs != null){
						rs.close();
						rs = null;
					}
				}
			}
			
			//5.2处理输出值
			if(parameters != null && parameters.size() > 0){
				
				List<Object> outParams = new ArrayList<Object>(parameters.size());
				for ( int i = 0, n = parameters.size(); i < n; i ++){
					
					StoreProcedureParameters.SPParameter parameter = parameters.get(i);
					if(IStoreProcedureParameters.PARAM_OUT != parameter.getIntout()){
						continue;
					}
					
					Object param = cstmt.getObject(i + 1);
					if(param instanceof ResultSet){
						ResultSet rs = (ResultSet)param;
						try{
							param = resultSet2List(rs, null);
						}finally{
							if(rs != null){
								rs.close();
								rs = null;
							}
						}
					}
					
					outParams.add(param);
				}
				
				spResult.setOutParams(outParams);
			}
			
			//6.返回结果
			return spResult;

		}catch(Throwable e){
			
			ExecuteJdbcException eje = new ExecuteJdbcException(" when execute jdbc excuteSP operation, occurs error!");
			eje.initCause(e);
			eje.addContextValue("spCommand", spCommand);
			eje.addContextValue("spParameters", spParameters);
			
			throw eje;
			
		}finally{
			
			if(cstmt != null){
				cstmt.close();
				cstmt = null;
			}
		}
	}

	/**
	 * ResultSet转换成List
	 * @param rs 结果集
	 * @param columns 字段数组，如果为空从rs中取
	 * @return List
	 * @throws SQLException
	 * @throws IOException 
	 */
	protected List<T> resultSet2List(ResultSet rs, IColumn[] columns) throws SQLException, IOException{
		
		//如果没有字段名称，从元数据中获取
		if(columns == null || columns.length <= 0){
			columns = fetchQueryColumnMeta(rs);	
		}
		
		//填充到行集中
		List<T> entitys = new ArrayList<T>();
		
		while(rs.next()){
			
			T entity = newEntity();
			for ( IColumn column : columns){
				this.setEntityProperty(entity, rs, column);
			}
			entitys.add(entity);
		}
		
		//返回行集
		return entitys;
	}
		
	/**
	 * 获取查询的字段结构
	 * @param rs 结果集
	 * @return 查询结果的结构
	 * @throws SQLException 
	 */
	protected IColumn[] fetchQueryColumnMeta(ResultSet rs) throws SQLException{
		
		//获取元数据
		ResultSetMetaData meta = rs.getMetaData();
		
		//创建结果集结构
		int colCount = meta.getColumnCount();
		IColumn[] columns = new IColumn[colCount];
		for ( int i = 0, n = colCount; i < n; i ++){
			
			IColumn column = new Column();
			column.setColName(meta.getColumnName(i + 1));
			column.setColType(meta.getColumnType(i + 1));
			
			columns[i] = column;
		}
		return columns;
	}
	
	/**
	 * 字段值转成字符串
	 * @param rs 结果集
	 * @param colName 字段名称
	 * @param colType 字段类型
	 * @return 字段值按字符类型返回
	 * @throws SQLException
	 * @throws ParseException 
	 */
	protected String columnValueToString(ResultSet rs, String colName, int colType){
		
		String value = null;
		try {
			value = converterUtil.toString(rs, colName, colType);

		} catch (Throwable e) {
			ConvertException ce = new ConvertException();
			ce.initCause(e);
			
			ce.addContextValue("colName", colName);
			ce.addContextValue("colType", colType);
			throw ce;
		}
		return value;
	}
	
	/**
	 * 字段值转成字符串
	 * @param rs 结果集
	 * @param colIdx 字段索引值
	 * @param colType 字段类型
	 * @return 字段值按字符类型返回
	 * @throws SQLException
	 * @throws ParseException 
	 */
	protected String columnValueToString(ResultSet rs, int colIdx, int colType){
		
		String value = null;
		try {
			value = converterUtil.toString(rs, colIdx, colType);
		} catch (Throwable e) {
			ConvertException ce = new ConvertException();
			ce.initCause(e);
			
			ce.addContextValue("colIdx", colIdx);
			ce.addContextValue("colType", colType);
			throw ce;
		}
		return value;
	}
	
	/**
	 * 生成实体
	 * @return
	 */
	protected abstract T newEntity()throws SQLException, IOException;
	
	/**
	 * 设置实体的属性
	 * @param entity 实体
	 * @param rs 结果集
	 * @param column 字段实体
	 * @return 设置后的实体
	 */
	protected abstract T setEntityProperty(T entity, ResultSet rs, IColumn column)throws SQLException, IOException;

	
}