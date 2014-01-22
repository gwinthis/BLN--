package com.bln.framework.filestru.xml.strategy;

import org.dom4j.io.OutputFormat;

public interface IWriteFormatStrategy {

	public abstract OutputFormat createFormat(String encoding);

}