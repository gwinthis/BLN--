package com.bln.framework.session;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public interface ISessionInfo {

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getSessionId()
	 */
	public abstract String getSessionid();

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setSessionId(java.lang.String)
	 */
	public abstract void setSessionid(String sessionId);

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getLastReqDate()
	 */
	public abstract Date getLastreqdate();

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setLastReqDate(java.util.Date)
	 */
	public abstract void setLastreqdate(Date lastReqDate);

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getAllSessionData()
	 */
	public abstract Map<String, Object> getAllSessionData();

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setAllSessionData(java.util.Map)
	 */
	public abstract void setAllSessionData(Map<String, Object> allSessionData);

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#getSessionData(java.lang.String)
	 */
	public abstract Object getSessionData(String key);

	/* (non-Javadoc)
	 * @see com.bln.framework.session.info.ISessionInfo#setSessionData(java.lang.String, java.lang.Object)
	 */
	public abstract void setSessionData(String key, Object value);

	/**
	 * @return the sessioninfo
	 * @throws IOException 
	 */
	public abstract byte[] getSessioninfo() throws IOException;

	/**
	 * @param sessioninfo the sessioninfo to set
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public abstract void setSessioninfo(byte[] sessioninfo) throws IOException,
			ClassNotFoundException;

}