/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.valgenerator;

import java.util.Arrays;

import com.bln.framework.persist.jdbc.template.IJdbcTemplate;
import com.bln.framework.persist.tablestru.param.IParam;
import com.bln.framework.persist.valgenerator.exception.GeneratorException;

/**
 * 生成器基类
 */
public abstract class ColumnValueGeneratorBase implements IColumnValueGenerator{
	
	/**
	 * 是否支持数据库函数
	 */
	protected boolean surportColValWithDbFun = false;
	
	/**
	 * JDBC访问模板
	 */
	public IJdbcTemplate<?> jdbcTemplate = null;
	
	/**
	 * 获得指定参数名的参数索引值
	 * @param params
	 * @param paramName
	 * @return
	 */
	protected int indexOf(IParam[] params, String paramName){
		if(params == null || params.length <= 0){
			return -1;
		}
		
		int n = params.length;
		int i = 0;
		for (; i < n; i++){
			if(paramName.equals(params[i].getName())){
				break;
			}
		}
		
		return i == n ? -1 : i;
	}

	/**
	 * 获得参数
	 * @param params
	 * @param paramName
	 * @return
	 */
	protected IParam getColumnParam(IParam[] params, String paramName){
		
		IParam param = null;
		
		int idx = this.indexOf(params, paramName);
		if(idx < 0){
			throw new GeneratorException(" not found param " + paramName + " in the params " + Arrays.toString(params));
		}else{
			param = params[idx];
		}
		
		return param;
	}
	

	/**
	 * @return the surportColValWithDbFun
	 */
	public boolean isSurportColValWithDbFun() {
		return surportColValWithDbFun;
	}

	/**
	 * @param surportColValWithDbFun the surportColValWithDbFun to set
	 */
	public void setSurportColValWithDbFun(boolean surportColValWithDbFun) {
		this.surportColValWithDbFun = surportColValWithDbFun;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public IJdbcTemplate<?> getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(IJdbcTemplate<?> jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
