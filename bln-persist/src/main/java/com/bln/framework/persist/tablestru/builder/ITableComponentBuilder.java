package com.bln.framework.persist.tablestru.builder;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;

public interface ITableComponentBuilder<T> extends IBuilder1Step<T, ITableStruConfig>{

	/**
	 * @return the componentName
	 */
	public String getComponentName();

}