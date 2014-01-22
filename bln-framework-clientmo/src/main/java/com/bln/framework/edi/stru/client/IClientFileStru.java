package com.bln.framework.edi.stru.client;

import com.bln.framework.filestru.IFileStru;
import com.bln.framework.mo.buildable.edi.IBuildMOByEdiable;

public interface IClientFileStru extends IFileStru, IBuildMOByEdiable, IClientFileBuildable{

	/**
	 * Message�ڵ�����
	 */
	public static final String MSG_NM = "message";
	
	/**
	 * Header�ڵ�����
	 */
	public static final String HEADER_NM = "header";

	/**
	 * ext�ڵ�����
	 */
	public static final String EXT_NM = "ext";
	
	/**
	 * SERVICEID�ڵ�����
	 */
	public static final String SERVICEID_NM = "service_id";

	/**
	 * REQUESTDATE�ڵ�����
	 */
	public static final String REQUESTDATE_NM = "request_date";
	
	/**
	 * RETURNCODE�ڵ�����
	 */
	public static final String RETURNCODE_NM = "return_code";
	
	/**
	 * ERRORCODE�ڵ�����
	 */
	public static final String ERRORCODE_NM = "error_code";
	
	/**
	 * ERRORDESC�ڵ�����
	 */
	public static final String ERRORDESC_NM = "error_desc";
	
	/**
	 * RESPONSEDATE�ڵ�����
	 */
	public static final String RESPONSEDATE_NM = "response_date";
	
	/**
	 * SESSIONID�ڵ�����
	 */
	public static final String SESSIONID_NM = "session_id";
			
	/**
	 * Body�ڵ�����
	 */
	public static final String BODY_NM = "body";
	
	/**
	 * request�ڵ�����
	 */
	public static final String REQUEST_NM = "request";
	
	/**
	 * response�ڵ�����
	 */
	public static final String RESPONSE_NM = "response";
	
	/**
	 * parameter�ڵ�����
	 */
	public static final String PARAMETER_NM = "parameter";

}