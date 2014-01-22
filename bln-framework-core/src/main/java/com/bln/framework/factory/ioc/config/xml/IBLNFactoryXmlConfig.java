package com.bln.framework.factory.ioc.config.xml;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;

public interface IBLNFactoryXmlConfig extends IBLNFactoryConfig{

	/*
	 * <objects>
		<catogry name = "T_SEND" tableNm = "T_SEND" activity = "NewEveryTimes">
		<!--
		NewEveryTimes
		ALWAYS
		creator = "Factory" 
		-->
		<object name = "DAO" class = "test.dao.T_SENDDao" />
		<object name = "SQLT" class = "test.dao.T_SENDSQLT"  />
		<object name = "DATAPRIVILEGE" class = "test.dao.commondp"  />
		<object name = "ENTITY" class = "test.entity.T_SENDEntity" />
		</catogry>
		
		<catogry name = "MOBUILDER"  moType = "rowtype">
		<object name = "CLIENTXMLEDI" class = "com.bln.framework.rowtypemo.builder.edi.client.BuildMOFromClientEdi" />
		</catogry>
		
		<catogry name = "MO">
		<object name = "ROWTYPE" class = "com.bln.framework.rowtypemo.MessageObject" />
		</catogry>
		
		</objects>
	 */
}