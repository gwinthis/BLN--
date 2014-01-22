package com.bln.framework.factory.simple;

import java.util.Map;

import com.bln.framework.factory.IFactory;

public interface ISimpleFactory<T> extends IFactory<T>{

	/**
	 *  @return the objInstanceMap
	 */
	public Map<String, T> getAllInstance();

}