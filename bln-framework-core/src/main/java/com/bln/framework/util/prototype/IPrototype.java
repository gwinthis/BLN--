package com.bln.framework.util.prototype;

public interface IPrototype<P, T>{

	/**
	 * @return the property
	 */
	public abstract P getProperty();

	/**
	 * @param property the property to set
	 */
	public abstract void setProperty(P property);

	/**
	 * »ñµÃÊµÀý
	 * @return
	 * @throws Throwable
	 */
	public abstract T getInstance();

}