package com.bln.framework.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.xml.DOMConfigurator;

import com.bln.framework.app.context.AppContext;
import com.bln.framework.edi.edge.rcver.IEdiRcver;
import com.bln.framework.edi.ipextractor.HTTPIPExtractor;
import com.bln.framework.edi.ipextractor.IExtractor4IP;
import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.util.asserter.Assert;

public abstract class GatewayServiceBase extends HttpServlet {
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(GatewayServiceBase.class);
	
	/**
	 * 应用的FRAMEWORK工厂参数
	 */
	protected static final String PARM_APP_FACTORY = "appFactory";

	/**
	 * 应用框架工厂
	 */
	protected IBLNFactory appFactory = null;
	
	/**
	 * EDI接收器的类名
	 */
	protected String ediRcverClass = null;
	
	/**
	 * IP提取器
	 */
	protected IExtractor4IP<HttpServletRequest> extractor = new HTTPIPExtractor();
	
	/**
	 * 响应的内容类型
	 */
	protected String contentType = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3204239482499823536L;

	/**
	 * Constructor of the object.
	 */
	public GatewayServiceBase() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); 
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.web.GatewayServiceBase#init()
	 */
	public void init() throws ServletException {
		loadFactories();
	}
	
	/**
	 * 加载工厂对象
	 */
	protected void loadFactories(){

		log.debug("GatewayService init....");
		
		//加载工厂
		//BLNFactoryCenter.singleton().loadAllFactory();
		
		//获取应用框架工厂
		String factoryName = this.getInitParameter(PARM_APP_FACTORY);
		Assert.paramIsNotNull(factoryName, "factoryName");
		
		appFactory = BLNFactoryCenter.singleton().getFactory(factoryName);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		String ip = extractor.extract(request);
		AppContext.singleton().setValue("CLIENT_IP", ip);
		
		if(log.isDebugEnabled()){
			log.debug("client " + ip + " request....");
		}
		
		try {
			
			//1.获取请求数据
			byte[] reqEdiRawData = IOUtils.toByteArray(request.getInputStream());
			
			//2.处理请求数据
			IEdiRcver ediRcver = (IEdiRcver)this.appFactory.getInstance(ediRcverClass);
		
			//接收并处理请求数据
			byte[] respEdi = ediRcver.receive(reqEdiRawData, request);
			
			//3.返回响应数据
			response.setContentType(this.contentType);
			
			OutputStream os = response.getOutputStream();
			os.write(respEdi);
			os.flush();
			os.close();

		} catch (Throwable e) {
			log.error("process post request error!", e);
		}finally{
			if(log.isDebugEnabled()){
				log.debug("client " + ip + " request finish....");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
