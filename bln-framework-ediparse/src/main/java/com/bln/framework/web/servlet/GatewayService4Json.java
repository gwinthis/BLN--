package com.bln.framework.web.servlet;

public class GatewayService4Json extends GatewayServiceBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GatewayService4Json() {
		super();
		this.ediRcverClass = "edi.edge.edircver.client.Json";
		this.contentType = "text/json";
	}
}
