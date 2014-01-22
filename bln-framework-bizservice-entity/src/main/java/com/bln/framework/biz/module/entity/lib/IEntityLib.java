package com.bln.framework.biz.module.entity.lib;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;

public interface IEntityLib{
	
	/**
	 * 根据对象名称获得对象
	 * @param objNm 对象名称
	 * @return 返回实际的对象
	 */
	public IEntityStatelessModule getInstance(String objNm);

	/**
	 * 根据对象名称获得对象
	 * @param objNm 对象名称
	 * @return 返回实际的对象
	 */
	public IEntityStatelessModule getNotNullInstance(String objNm);

	
}