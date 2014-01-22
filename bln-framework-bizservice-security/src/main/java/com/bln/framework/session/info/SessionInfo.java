/**
 * @author gengw
 * Created on Apr 5, 2012
 */
package com.bln.framework.session.info;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bln.framework.base.BaseObj;
import com.bln.framework.session.ISessionInfo;

/**
 * 存储Session信息的对象
 */
public class SessionInfo extends BaseObj implements ISessionInfo{
	
	/**
	 * SessionId
	 */
	protected String sessionid = null;
	
	/**
	 * 最后一次请求的时间
	 */
	protected Date lastreqdate = null;
	
	/**
	 * session存储的数据
	 */
	protected Map<String, Object> allSessionData = new HashMap<String, Object>();
	
	/**
	 * 构造函数
	 */
	public SessionInfo(){
		
	}
	
	/**
	 * 构造函数
	 * @param sessionId
	 * @param lastReqDate
	 */
	public SessionInfo(String sessionId, Date lastReqDate){
		this.sessionid = sessionId;
		this.lastreqdate = lastReqDate;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getSessionid()
	 */
	public String getSessionid() {
		return sessionid;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setSessionid(java.lang.String)
	 */
	public void setSessionid(String sessionId) {
		this.sessionid = sessionId;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getLastreqdate()
	 */
	public Date getLastreqdate() {
		return lastreqdate;
	}


	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setLastreqdate(java.util.Date)
	 */
	public void setLastreqdate(Date lastReqDate) {
		this.lastreqdate = lastReqDate;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getAllSessionData()
	 */
	public Map<String, Object> getAllSessionData() {
		return allSessionData;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setAllSessionData(java.util.Map)
	 */
	public void setAllSessionData(Map<String, Object> allSessionData) {
		this.allSessionData = allSessionData;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getSessionData(java.lang.String)
	 */
	public Object getSessionData(String key){
		return allSessionData.get(key);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setSessionData(java.lang.String, java.lang.Object)
	 */
	public void setSessionData(String key, Object value) {
		this.allSessionData.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getSessioninfo()
	 */
	public byte[] getSessioninfo() throws IOException {
		byte[] data = this.serial(this.allSessionData);
		return data;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setSessioninfo(byte[])
	 */
	@SuppressWarnings("unchecked")
	public void setSessioninfo(byte[] sessioninfo) throws IOException, ClassNotFoundException {
		this.allSessionData = (Map<String, Object>)this.revertSerail(sessioninfo);
	}
	
	/**
	 * 转换成对象
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected Object revertSerail(byte[] bytes) throws IOException, ClassNotFoundException{
		
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream in = new ObjectInputStream(bais);
		
		Object obj = in.readObject();
		in.close();
		
		return obj;
		
	}
	
	/**
	 * 序列化对象
	 * @param sessionInfo
	 * @return
	 * @throws IOException
	 */
	protected byte[] serial(Object obj) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		
		out.writeObject(obj);
		out.close();
		
		byte[] bytes = baos.toByteArray();
		baos.close();
		
		return bytes;
	}

}
