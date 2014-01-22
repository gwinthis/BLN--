/**
 * @author gengw
 * craeted on 2012-03-27
 */
package com.bln.framework.edi.edge;

import java.io.IOException;

import com.bln.framework.edi.edge.filter.IEdiFilter;
import com.bln.framework.edi.output.IEdiOutput;
import com.bln.framework.filestru.builder.bymo.IFileBuilderByMo;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.IBuildMOable;
import com.bln.framework.mo.builder.IMoBuilder;

/**
 * EDI�߽���
 */
public abstract class EdiEdgeBase implements IEdiEdge {
	
	/**
	 * EDI��ʹ�õ��ַ�������
	 */
	protected String encoding = null;
	
	/**
	 * ����EDI���ݹ�����
	 */
	protected IEdiFilter[] requestEdiFilters = null;

	/**
	 * ��ӦEDI���ݹ�����
	 */
	protected IEdiFilter[] responseEdiFilters = null;

	
	/**
	 * �Ƿ����������
	 */
	protected boolean outputRequestEdi = false;

	
	/**
	 * �Ƿ������Ӧ����
	 */
	protected boolean outputResponseEdi = false;
	
	/**
	 * EDI�����������
	 */
	protected IEdiOutput[] ediOutputs = null;

	/**
	 * IMessageObject������������
	 */
	protected IMoBuilder<IMessageObject, IBuildMOable> moBuilder = null;
	
	/**
	 * ����������-ͨ��MO����
	 */
	protected IFileBuilderByMo fileBuilderByMo = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.IEdiEdge#getEdiOutputs()
	 */
	public IEdiOutput[] getEdiOutputs() {
		return ediOutputs;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.IEdiEdge#setEdiOutputs(com.bln.framework.edi.output.IEdiOutput[])
	 */
	public void setEdiOutputs(IEdiOutput[] ediOutputs) {
		this.ediOutputs = ediOutputs;
	}
	
	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.IEdiEdge#getMoBuilder()
	 */
	public IMoBuilder<IMessageObject, IBuildMOable> getMoBuilder() {
		return moBuilder;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.IEdiEdge#setMoBuilder(com.bln.framework.mo.builder.IMoBuilder)
	 */
	public void setMoBuilder(IMoBuilder<IMessageObject, IBuildMOable> moBuilder) {
		this.moBuilder = moBuilder;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.IEdiEdge#getFileBuildByMo()
	 */
	public IFileBuilderByMo getFileBuilderByMo() {
		return fileBuilderByMo;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.IEdiEdge#setFileBuildByMo(com.bln.framework.filestru.builder.bymo.IFileBuildByMo)
	 */
	public void setFileBuilderByMo(IFileBuilderByMo fileBuilderByMo) {
		this.fileBuilderByMo = fileBuilderByMo;
	}
		
	/**
	 * �����Ĺ���
	 * @param ediData EDI����
	 * @throws IOException 
	 */
	protected byte[] requestEdiFilter(byte[] ediData) throws IOException{
		
		if(requestEdiFilters != null && requestEdiFilters.length > 0){
			for(IEdiFilter ediFilter : requestEdiFilters){
				ediData = ediFilter.filter(ediData);
			}
		}
		return ediData;
	}

	/**
	 * ��Ӧ���Ĺ���
	 * @param ediData EDI����
	 * @throws IOException 
	 */
	protected byte[] responseEdiFilter(byte[] ediData) throws IOException{
		
		if(responseEdiFilters != null && responseEdiFilters.length > 0){
			for(IEdiFilter ediFilter : responseEdiFilters){
				ediData = ediFilter.filter(ediData);
			}
		}
		return ediData;
	}
	
	/**
	 * EDI���
	 * @param ediData EDI����
	 * @param encoding EDI���ַ���
	 * @throws IOException 
	 */
	protected void ediOutput(byte[] ediData, String encoding) throws IOException{
		
		if(ediOutputs != null && ediOutputs.length > 0){
			String ediStr = new String(ediData, encoding);
			for(IEdiOutput ediOutput : ediOutputs){
				ediOutput.output(ediStr);
			}
		}
	}

	/**
	 * @return the outputRequestEdi
	 */
	public boolean isOutputRequestEdi() {
		return outputRequestEdi;
	}

	/**
	 * @param outputRequestEdi the outputRequestEdi to set
	 */
	public void setOutputRequestEdi(boolean outputRequestEdi) {
		this.outputRequestEdi = outputRequestEdi;
	}

	/**
	 * @return the outputResponseEdi
	 */
	public boolean isOutputResponseEdi() {
		return outputResponseEdi;
	}

	/**
	 * @param outputResponseEdi the outputResponseEdi to set
	 */
	public void setOutputResponseEdi(boolean outputResponseEdi) {
		this.outputResponseEdi = outputResponseEdi;
	}

	/**
	 * @return the requestEdiFilters
	 */
	public IEdiFilter[] getRequestEdiFilters() {
		return requestEdiFilters;
	}

	/**
	 * @param requestEdiFilters the requestEdiFilters to set
	 */
	public void setRequestEdiFilters(IEdiFilter[] requestEdiFilters) {
		this.requestEdiFilters = requestEdiFilters;
	}

	/**
	 * @return the responseEdiFilters
	 */
	public IEdiFilter[] getResponseEdiFilters() {
		return responseEdiFilters;
	}

	/**
	 * @param responseEdiFilters the responseEdiFilters to set
	 */
	public void setResponseEdiFilters(IEdiFilter[] responseEdiFilters) {
		this.responseEdiFilters = responseEdiFilters;
	}


}
