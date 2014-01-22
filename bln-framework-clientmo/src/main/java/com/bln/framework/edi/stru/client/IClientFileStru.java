package com.bln.framework.edi.stru.client;

import com.bln.framework.filestru.IFileStru;
import com.bln.framework.mo.buildable.edi.IBuildMOByEdiable;

public interface IClientFileStru extends IFileStru, IBuildMOByEdiable, IClientFileBuildable{

	/**
	 * Message节点名称
	 */
	public static final String MSG_NM = "message";
	
	/**
	 * Header节点名称
	 */
	public static final String HEADER_NM = "header";

	/**
	 * ext节点名称
	 */
	public static final String EXT_NM = "ext";
	
	/**
	 * SERVICEID节点名称
	 */
	public static final String SERVICEID_NM = "service_id";

	/**
	 * REQUESTDATE节点名称
	 */
	public static final String REQUESTDATE_NM = "request_date";
	
	/**
	 * RETURNCODE节点名称
	 */
	public static final String RETURNCODE_NM = "return_code";
	
	/**
	 * ERRORCODE节点名称
	 */
	public static final String ERRORCODE_NM = "error_code";
	
	/**
	 * ERRORDESC节点名称
	 */
	public static final String ERRORDESC_NM = "error_desc";
	
	/**
	 * RESPONSEDATE节点名称
	 */
	public static final String RESPONSEDATE_NM = "response_date";
	
	/**
	 * SESSIONID节点名称
	 */
	public static final String SESSIONID_NM = "session_id";
			
	/**
	 * Body节点名称
	 */
	public static final String BODY_NM = "body";
	
	/**
	 * request节点名称
	 */
	public static final String REQUEST_NM = "request";
	
	/**
	 * response节点名称
	 */
	public static final String RESPONSE_NM = "response";
	
	/**
	 * parameter节点名称
	 */
	public static final String PARAMETER_NM = "parameter";

}