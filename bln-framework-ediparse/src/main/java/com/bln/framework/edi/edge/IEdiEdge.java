package com.bln.framework.edi.edge;

import com.bln.framework.edi.output.IEdiOutput;
import com.bln.framework.filestru.builder.bymo.IFileBuilderByMo;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.IBuildMOable;
import com.bln.framework.mo.builder.IMoBuilder;

public interface IEdiEdge {

	/**
	 * @return the ediOutputs
	 */
	public abstract IEdiOutput[] getEdiOutputs();

	/**
	 * @return
	 */
	public IMoBuilder<IMessageObject, IBuildMOable> getMoBuilder();

	/**
	 * @return the fileBuildByMoType
	 */
	public IFileBuilderByMo getFileBuilderByMo();

	/**
	 * @return the encoding
	 */
	public String getEncoding();


}