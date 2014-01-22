package com.bln.framework.web.servlet;

public class GatewayService4Xml extends GatewayServiceBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3204239482499823536L;

	/**
	 * Constructor of the object.
	 */
	public GatewayService4Xml() {
		super();
		this.ediRcverClass = "edi.edge.edircver.client.Xml";
		this.contentType = "text/xml";
	}
}
