/**
 * @author Gengw
 * Created at 2008-05-12
 */
package com.bln.framework.persist.jdbc.wrapper.log;

import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.base.BaseObj;

/**
 * ��¼SQL���
 *
 */
public class LogSql extends BaseObj{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(LogSql.class);

	/**
	 * ������
	 */
	protected final String PARM_SIGN = "?";
	
	/**
	 * Ԥ����SQL���
	 */
	protected String preparedSql = null;
	
	/**
	 * ������ֵSQL���
	 */
	protected String sql = null;
	
	/**
	 * ����SQL
	 */
	protected List<String> sqls = new ArrayList<String>();
	
	/**
	 * ��ǰ������λ��
	 */
	protected int loc = -1;
	
	/**
	 * ����SQL
	 * @param sql
	 */
	public void initSQL(String sql){
		this.preparedSql = sql;
		this.sql = sql;
	}
	
	/**
	 * �������SQL
	 * @param sql
	 */
	public void addSQL(String sql){
		sqls.add(sql);
	}
	
	/**
	 * �������SQL
	 */
	public void logSQLS(){
		for ( int i = 0, n = sqls.size(); i < n; i ++){
			String sql = (String)sqls.get(i);
			log.debug(sql);
			//ListenerCenter.singleton().excuteEvts(EventEnum.LOGSQL_LOG_EVENTS, sql);
		}
	}
	
	/**
	 * �����־
	 */
	public void logSQL(){
		log.debug(sql);
		//ListenerCenter.singleton().excuteEvts(EventEnum.LOGSQL_LOG_EVENTS, sql);
		
		sql = null;
		preparedSql = null;
	}

	/**
	 * �����־
	 */
	public void logSQL(String sql){
		log.debug(sql);
		//ListenerCenter.singleton().excuteEvts(EventEnum.LOGSQL_LOG_EVENTS, sql);
	}
	
	/**
	 * ���SQL
	 */
	public void clearSQL(){
		sql = null;
		preparedSql = null;
		sqls = null;
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(Object obj){
		StringBuilder sbVal = new StringBuilder();
		if(obj instanceof String){
			sbVal.append("'");
			sbVal.append(obj);
			sbVal.append("'");
		}else if(obj instanceof Timestamp){
			Timestamp ts = (Timestamp)obj;
			sbVal.append("TO_DATE('");
			sbVal.append(DateFormatUtils.format(ts.getTime(), "yyyy-MM-dd HH:mm:ss.SSS"));
			
			sbVal.append("', 'yyyy-MM-dd hh24:mi:ss.SSS')");
		}else{
			sbVal.append(String.valueOf(obj));
		}
		sql = replaceParam(sbVal.toString());
	}
		
	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(boolean obj){
		sql = replaceParam(String.valueOf(obj));
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(byte obj){
		sql = replaceParam(String.valueOf(obj));
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(double obj){
		sql = replaceParam(String.valueOf(obj));
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(float obj){
		sql = replaceParam(String.valueOf(obj));
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(long obj){
		sql = replaceParam(String.valueOf(obj));
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(int obj){
		sql = replaceParam(String.valueOf(obj));
	}
	
	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(Array objs){
		StringBuilder sbParam = new StringBuilder("(Array ");
		sbParam.append(String.valueOf(objs));
		sbParam.append(")");
		sql = replaceParam(sbParam.toString());

	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(byte[] objs){
		StringBuilder sbParam = new StringBuilder("(byte[] ");
		sbParam.append(String.valueOf(objs));
		sbParam.append(")");
		sql = replaceParam(sbParam.toString());
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(InputStream objs){
		StringBuilder sbParam = new StringBuilder("(InputStream ");
		sbParam.append(String.valueOf(objs));
		sbParam.append(")");
		sql = replaceParam(sbParam.toString());
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(Reader objs){
		StringBuilder sbParam = new StringBuilder("(Reader ");
		sbParam.append(String.valueOf(objs));
		sbParam.append(")");
		sql = replaceParam(sbParam.toString());
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(Blob objs){
		StringBuilder sbParam = new StringBuilder("(Blob ");
		sbParam.append(String.valueOf(objs));
		sbParam.append(")");
		sql = replaceParam(sbParam.toString());
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(Clob objs){
		StringBuilder sbParam = new StringBuilder("(Clob ");
		sbParam.append(String.valueOf(objs));
		sbParam.append(")");
		sql = replaceParam(sbParam.toString());
	}

	/**
	 * ���ò���ֵ
	 * @param instance
	 */
	public void setParam(URL objs){
		StringBuilder sbParam = new StringBuilder("(URL ");
		sbParam.append(String.valueOf(objs));
		sbParam.append(")");
		sql = replaceParam(sbParam.toString());
	}

	/**
	 * �滻����
	 * @param str
	 * @return
	 */
	public String replaceParam(String param){
		StringBuilder sbNewStr = new StringBuilder();
		int paramPos = sql.indexOf(PARM_SIGN);
		if(paramPos >= 0){
			sbNewStr.append(sql.substring(0, paramPos));
			sbNewStr.append(param);
			sbNewStr.append(sql.substring(paramPos + 1, sql.length()));
		}
		return sbNewStr.toString();
		//return sql.replaceFirst(PARM_SIGN, str);
	}

	/**
	 * @return the preparedSql
	 */
	public String getPreparedSql() {
		return preparedSql;
	}

	/**
	 * @param preparedSql the preparedSql to set
	 */
	public void setPreparedSql(String preparedSql) {
		this.preparedSql = preparedSql;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
