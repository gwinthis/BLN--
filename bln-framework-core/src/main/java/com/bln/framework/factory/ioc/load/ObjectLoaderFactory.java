/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.factory.ioc.load;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.base.cacheable.BaseObjCacheable;
import com.bln.framework.factory.exception.LoadInstanceErrorException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.bean.BeanLoader;
import com.bln.framework.factory.ioc.load.collections.ArrayLoader;
import com.bln.framework.factory.ioc.load.collections.CollectionLoader;
import com.bln.framework.factory.ioc.load.collections.MapLoader;
import com.bln.framework.factory.ioc.load.collections.PropertiesLoader;
import com.bln.framework.factory.ioc.load.creator.BuilderCreator;
import com.bln.framework.factory.ioc.load.creator.FactoryCenterCreator;
import com.bln.framework.factory.ioc.load.creator.FromFactoryCreator;
import com.bln.framework.factory.ioc.load.creator.FromOtherFactoryCreator;
import com.bln.framework.factory.ioc.load.creator.FromPackageCreator;
import com.bln.framework.factory.ioc.load.creator.FromThisFactoryCreator;
import com.bln.framework.factory.ioc.load.creator.ListObjectsFromFactoryCenterCreator;
import com.bln.framework.factory.ioc.load.creator.NewObjectCreator;
import com.bln.framework.factory.ioc.load.creator.NullObjectCreator;
import com.bln.framework.factory.ioc.load.creator.OutServiceCreator;
import com.bln.framework.factory.ioc.load.creator.TransValueToOthCreator;
import com.bln.framework.factory.ioc.load.desc.DescLoader;


/**
 * 
 * �������������
 */
public class ObjectLoaderFactory extends BaseObjCacheable{

	/**
	 * ����Bean������
	 */
	static IObjectLoader beanLoader = new BeanLoader();
	
	/**
	 * ������Ϣ������
	 */
	static IObjectLoader descLoader = new DescLoader();
	
	/**
	 * ͨ���������Ķ��������
	 */
	static IObjectLoader builderLoader = new BuilderCreator();
	
	/**
	 * ������������
	 */
	static IObjectLoader serviceCreator = new OutServiceCreator();

	/**
	 * �ӵ�ǰ������ȡ����ļ�����
	 */
	static IObjectLoader fromThisFactoryCreator = new FromThisFactoryCreator();

	/**
	 * ������������ȡ����ļ�����
	 */
	static IObjectLoader fromOtherFactoryCreator = new FromOtherFactoryCreator();

	/**
	 * ��ָ��������ȡ����ļ�����
	 */
	static IObjectLoader fromTheFactoryCreator = new FromFactoryCreator();

	/**
	 * �½�����ļ�����
	 */
	static IObjectLoader newObjectCreator = new NewObjectCreator();
	
	/**
	 * ����Valueֵת����ָ�����͵ļ�����
	 */
	static IObjectLoader transValueToOthCreator = new TransValueToOthCreator();

	/**
	 * �Ӱ��м�����Դ�ļ�����
	 */
	static IObjectLoader fromPackgeCreator = new FromPackageCreator();

	/**
	 * �ӹ��������л�ȡ��������
	 */
	static IObjectLoader factoryCenterCreator = new FactoryCenterCreator();

	/**
	 * �ӹ������Ļ�ȡָ��·���Ķ���
	 */
	static IObjectLoader listObjectsFromFactoryCenterCreater = new ListObjectsFromFactoryCenterCreator();
	
	/**
	 * ��ֵ���������
	 */
	static IObjectLoader nullObjectCreator = new NullObjectCreator();

	/**
	 * �������ͼ�����
	 */
	static IObjectLoader arrayLoader = new ArrayLoader();

	/**
	 * �������ͼ�����
	 */
	static IObjectLoader collectionLoader = new CollectionLoader();

	/**
	 * ӳ�����ͼ�����
	 */
	static IObjectLoader mapLoader = new MapLoader();

	/**
	 *  �������ͼ�����
	 */
	static IObjectLoader propertiesLoader = new PropertiesLoader();
	
	/**
	 * ��ö��������
	 * @param factory
	 * @param config
	 * @return ���������
	 */
	public static IObjectLoader getObjLoader(IBLNFactoryConfigEntity config) {
		
		
		IObjectLoader objectLoader = null;
		
		//����creator��collection_type��clazz�жϼ�����
		String creatorType = config.getAttr(IBLNFactoryConfig.ATTR_CREATOR);
		String collectionType = config.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONTYPE);
		String clazz = config.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		String value = config.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		if(!StringUtils.isEmpty(creatorType)){
				
			//Creator������
			if(IBLNFactoryConfig.VALUE_CREATOR_FROM_THIS_FACTORY.equals(creatorType)){
				objectLoader = fromThisFactoryCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FROM_OTHER_FACTORY.equals(creatorType)){
				objectLoader = fromOtherFactoryCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_TRANS_FROM_VALUE.equals(creatorType)){
				objectLoader = transValueToOthCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FROM_THE_FACTORY.equals(creatorType)){
				objectLoader = fromTheFactoryCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_OTS_LOCATER.equals(creatorType)){
				objectLoader = serviceCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_BUILDER.equals(creatorType)){
				objectLoader = builderLoader;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_NEW_OBJECT.equals(creatorType)){
				objectLoader = newObjectCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FACTORYCENTER.equals(creatorType)){
				objectLoader = factoryCenterCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_LISTOBJECTSFROMFACTORYCENTER.equals(creatorType)){
				objectLoader = listObjectsFromFactoryCenterCreater;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_FROMPACKAGE.equals(creatorType)){
				objectLoader = fromPackgeCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_NULLVALE.equals(creatorType)){
				objectLoader = nullObjectCreator;
			}else if(IBLNFactoryConfig.VALUE_CREATOR_STRING_VALUE.equals(creatorType)){
				objectLoader = descLoader;
			}
		}else if(!StringUtils.isEmpty(collectionType)){
			
			//�������ͼ�����
			if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_ARRAY.equals(collectionType)){
				objectLoader = arrayLoader;
			}else if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_COLLECTION.equals(collectionType)){
				objectLoader = collectionLoader;
			}else if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_MAP.equals(collectionType)){
				objectLoader = mapLoader;
			}else if(IBLNFactoryConfig.VALUE_COLLECTIONTYPE_PROPERTIES.equals(collectionType)){
				objectLoader = propertiesLoader;
			}
				
		}else if(!StringUtils.isEmpty(clazz)){
			
			//�������
			objectLoader = beanLoader;
			
		}else if(!StringUtils.isEmpty(value)){
			
			//��Ϣ����������
			objectLoader = descLoader;
		}
		
		//���δ�ҵ��������׳��쳣
		if(objectLoader == null){
			LoadInstanceErrorException liee = new LoadInstanceErrorException("not found object loader in the context!");
			liee.setContextValue("config", config);
			throw liee;
		}
		
		//���ؼ�����
		return objectLoader;
	}
}
