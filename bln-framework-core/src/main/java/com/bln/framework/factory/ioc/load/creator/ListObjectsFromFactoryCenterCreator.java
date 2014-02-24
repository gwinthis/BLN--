/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * <p>
 * ��BLNFactoryCenter�в���ָ��������Map<String, Object>��ʽ���ء�keyΪ�������ƣ�valueΪҪ���ҵĶ���
 * </p>
 * <p>
 * ����class���ԣ���BLNFactoryCenter�б������й�������ȡָ������·���Ķ���
 * </p>
 */
public class ListObjectsFromFactoryCenterCreator extends ObjectLoaderBase {

	/**
	 * ��̬����
	 */
	static Map<String, Map<String, Object>> cache = new HashMap<String, Map<String, Object>>();
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {

		// ����Ҫ���صĽ��
		Map<String, Object> result = null;

		// ���Ҫ���Ҷ���Ĺ���·��
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//��ȡ������
		if(cache.containsKey(clazz)){
			result = (Map<String, Object>)cache.get(clazz);
		}else{
			result = this.findObjects(clazz);
			cache.put(clazz, result);
		}

		return result;
	}

	/**
	 * ���Ҷ���
	 * @param clazz
	 * @return
	 */
	protected Map<String, Object> findObjects(String clazz) {

		// ����Ҫ���صĶ���
		Map<String, Object> result = new HashMap<String, Object>();

		// �ӹ������Ļ�ȡָ������
		Map<String, IBLNFactory> factoryMap = BLNFactoryCenter.singleton().getAllFactories();
		for (Map.Entry<String, IBLNFactory> factoryEntry : factoryMap.entrySet()) {
			IBLNFactory blnFactory = factoryEntry.getValue();

			try {
				Object obj = blnFactory.getInstance(clazz);
				if (obj != null) {
					result.put(blnFactory.getFactoryName(), obj);
				}
			} catch (Throwable e) {

			}

		}

		return result;
	}
}
