/**
 * @author gengw
 * Created at 2012-03-15
 */
package com.bln.framework.factory.ioc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.bln.framework.factory.FactoryBase;
import com.bln.framework.factory.exception.DependencyCircleException;
import com.bln.framework.factory.exception.LoadInstanceErrorException;
import com.bln.framework.factory.exception.NewInstanceErrorException;
import com.bln.framework.factory.exception.InitFactoryException;
import com.bln.framework.factory.exception.NotFoundObjConfigException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.listener.IObjectListener;
import com.bln.framework.factory.ioc.load.IObjectLoader;
import com.bln.framework.factory.ioc.load.ObjectLoaderFactory;
import com.bln.framework.timertask.ITimerTaskMan;
import com.bln.framework.timertask.TimerTaskMan;
import com.bln.framework.timertask.trigger.ITaskTriggerBuilder;
import com.bln.framework.util.asserter.Assert;

/**
 * <p>���Ʒ�ת������</p>
 */
public class BLNFactory extends FactoryBase<Object> implements IBLNFactory, IBLNFactoryObjectConfig{
	
	/**
	 * ������
	 */
	private static final Object loadLock = new Object();
	
	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(BLNFactory.class);
	
	/**
	 * ���Ͷ���ķָ���
	 */
	public final static String SPLIT_STR = ".";
		
	/**
	 * ����������
	 */
	protected String parentConfigUrl = null;
	
//	/**
//	 * ���ü���·��
//	 */
//	protected String configLoadPath = null;
	
	/**
	 * Factory�����ļ�
	 */
	protected IBLNFactoryConfig configFile = null;
	
	/**
	 * ��ʱ����������
	 */
	protected ITimerTaskMan timerTaskMan = new TimerTaskMan();
			
	/**
	 * ������������Ӧ��������Ϣ
	 * ������Ϊ "���.����"������: T_TEST.DAO
	 */
	protected final Map<String, IBLNFactoryConfigEntity> objConfigMap = Collections.synchronizedMap(new HashMap<String, IBLNFactoryConfigEntity>());
	
	/**
	 * �־ö����ʵ����
	 */
	protected final Map<String, Object> objInstanceMap = Collections.synchronizedMap(new HashMap<String, Object>());
	
	/**
	 * ��ȡ���ж���·����
	 * @return ���ж���·����
	 */
	public List<String> getObjectNames(){
		
		List<String> objectNames = new ArrayList<String>(objConfigMap.size());
		for (Map.Entry<String, IBLNFactoryConfigEntity> entry : objConfigMap.entrySet()){
			objectNames.add(entry.getKey());
		}

		return objectNames;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.IFactory#getInstance(java.lang.String)
	 */
	public Object getInstance(String objNm) throws NewInstanceErrorException{
		
		//�������Ʋ���Ϊ��
		Assert.paramIsNotNull(objNm, "objNm");

		//���ݶ������ƻ�ö���
		Object obj = null;
		
		//�����ʵ�����д��ڣ�ֱ�Ӵӳ��л�ȡ������������ʵ��
		if(objInstanceMap.containsKey(objNm)){
			
			obj = objInstanceMap.get(objNm);
			
		}else{
			
			//��ö��������
			IBLNFactoryConfigEntity config = objConfigMap.get(objNm);
			if(config == null){
				throw new NewInstanceErrorException(objNm + " is not in " + this.factoryName + " factory!");
			}
			
			//��ö���
			try{
				obj = this.loadObj(objNm, config);
			}catch(Throwable e){
				NewInstanceErrorException goeException = new NewInstanceErrorException();
				goeException.initCause(e);
				throw goeException;
			}

			//�������Ļ����Ϊʼ�մ��ڲ�����Ҫ�ӳټ��أ�����֮����ӵ�����ʵ������
			if(IBLNFactoryConfig.VALUE_ACTIVITY_ALWAYS_ONE.equals(config.getAttr(IBLNFactoryConfig.ATTR_ACTIVITY)) 
			&& IBLNFactoryConfig.VALUE_LAZYLOADING_TRUE.equals(config.getAttr(IBLNFactoryConfig.ATTR_LAZYLOADING))){
				
				//��ӵ�ʵ������
				objInstanceMap.put(objNm, obj);
			}
		}
		
		//����ʵ��
		return obj;
	}

	/**
	 * ���캯��
	 */
	public BLNFactory(IBLNFactoryConfig configFile)throws InitFactoryException{
		this.configFile = configFile;
		init();
	}
	
	/**
	 * ��ʼ������
	 */
	protected void init() throws InitFactoryException{
		
		try {
			
			//1.��ʼ�����������ļ�
			//configFile = initConifg();
			Assert.paramIsNotNull(configFile, "configFile","Please Initialize Config File!");
			
			//1.2���ù���������Ϣ
			IBLNFactoryConfigEntity configEntity = configFile.getPropertyOfObjects();
			
			//���ù�������
			this.factoryName = configEntity.getAttr(IBLNFactoryConfig.ATTR_NAME);
			if(log.isDebugEnabled()){
				log.debug("parsing IocFactory " + factoryName);
			}
			
//			//���ø���������
//			parentConfigUrl = configEntity.getAttr(IBLNFactoryConfig.ATTR_EXTENDS);
//			
//			//2.��������������Ϣ
//			configLoadPath = parseParentConfigs(parentConfigUrl);
			
			//3.������е�������Ϣ
			Map<String, List<IBLNFactoryConfigEntity>> configMap = configFile.getAllConfigEntityOfObjs();
			if (configMap == null || configMap.isEmpty()) {
				return;
			}
			
			//3.��ʼ��������Ϣ
			parseConfigs(configMap);
			
			//4.�����Ҫ�������ɵ������ڹ����������Ƿ����
			checkProperty();
			
			//5.У���Ƿ����ѭ������
			checkDependenceCircle();
			
			//6.��ʼ��Ԥװ�صĶ���
			preLoadObjects();
			
			//7.��ʼ����ʱ������
			loadTimerTasks();
			
		} catch (Throwable e) {
			InitFactoryException initFactoryException = new InitFactoryException("factory " + this.factoryName + " init error!");
			initFactoryException.initCause(e);
			throw initFactoryException;
		}
	}
			
	/**
	 * ����objs�µ�����������Ϣ
	 * @param configMap
	 */
	protected void parseConfigs(Map <String, List<IBLNFactoryConfigEntity>> configMap){
		for ( Map.Entry<String, List<IBLNFactoryConfigEntity>> configEntry : configMap.entrySet()){
			List<IBLNFactoryConfigEntity> configEntitys = configEntry.getValue();
			for ( IBLNFactoryConfigEntity configEntity : configEntitys){
				parseEntityConfig(null, configEntity);
			}
		}
	}

	/**
	 * ����IocFactoryConfigEntity��������Ϣ
	 * @param parentPropertyMap
	 * @param CatogryConfigs
	 */
	protected void parseEntityConfig(IBLNFactoryConfigEntity pIocFactoryConfigEntity, IBLNFactoryConfigEntity entityConfig){
		
		//���ʵ������
		String entityNm = entityConfig.getAttr(IBLNFactoryConfig.ATTR_NAME);

		//1.������ڸ��࣬��Ӹ������Ϣ
		if(pIocFactoryConfigEntity != null){
			
			//1.1��Ӹ�������
			String pEntityNm = pIocFactoryConfigEntity.getAttr(IBLNFactoryConfig.ATTR_NAME);
			entityNm = new StringBuilder(pEntityNm).append(SPLIT_STR).append(entityNm).toString();
			
			entityConfig.setAttr(IBLNFactoryConfig.ATTR_NAME, entityNm);

			//1.2�������ԣ����ֻ�ڸ�����ڵ�����
			entityConfig.setAttrMap(unionMapByKey(entityConfig.getAttrMap(), pIocFactoryConfigEntity.getAttrMap()));

			//1.3�������ԣ����ֻ�ڸ�����ڵ�����
			entityConfig.setPropertyMap(unionMapByKey(entityConfig.getPropertyMap(), pIocFactoryConfigEntity.getPropertyMap()));
		}

		//2.������������Ϣ
		List<IBLNFactoryConfigEntity> subEntityConfigs = entityConfig.getSubEntitys();
		if(subEntityConfigs != null && !subEntityConfigs.isEmpty()){
			for ( IBLNFactoryConfigEntity subEntityConfig : subEntityConfigs){
				parseEntityConfig(entityConfig, subEntityConfig);		
			}
		}
		
		//3.���������Ϊ�������ͣ���ӵ��������ó���
		String configType = entityConfig.getType();
		if(IBLNFactoryConfig.NODE_OBJECT_NM.equals(configType)){
			this.objConfigMap.put(entityNm, entityConfig);
		}
	}
	
	/**
	 * �����Ҫ�ӹ����������������Ƿ���ȷ
	 * @return
	 * @throws InitFactoryException
	 */
	protected void checkProperty() throws NotFoundObjConfigException{
		
		for(Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : this.objConfigMap.entrySet()){
			
			IBLNFactoryConfigEntity objConfig = objConfigEntry.getValue();
			
			Map<String, IBLNFactoryConfigEntity> propertyMap = objConfig.getPropertyMap();
			if(propertyMap != null && !propertyMap.isEmpty()){
				
				for (Map.Entry<String, IBLNFactoryConfigEntity> propertyEntry : propertyMap.entrySet()){
					
					//�ж���Ҫ�������ɵ������Ƿ��ڹ������ÿ��д���
					IBLNFactoryConfigEntity propertyConfig = propertyEntry.getValue();
					
					//�����Ҫ�ӹ�������
					if(IBLNFactoryConfig.VALUE_CREATOR_FROM_THIS_FACTORY.equals(propertyConfig.getAttr(IBLNFactoryConfig.ATTR_CREATOR))){
						
						//�����������
						String collectionType = propertyConfig.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONTYPE);
						if( null == collectionType || "".equals(collectionType)){
							isValidInstance(propertyConfig.getAttr(IBLNFactoryConfig.ATTR_CLASS));
						}else{
							List<IBLNFactoryConfigEntity> subProps = propertyConfig.getSubEntitys();
							for ( IBLNFactoryConfigEntity subProp : subProps ){
								isValidInstance(subProp.getAttr(IBLNFactoryConfig.ATTR_CLASS));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * У������ʵ���Ƿ��������д���
	 * @param objNm
	 * @throws NotFoundObjConfigException
	 */
	protected void isValidInstance(String objNm) throws NotFoundObjConfigException{
		if(!objConfigMap.containsKey(objNm)){
			StringBuilder errorInfo = new StringBuilder("not found ").append(objNm).append(" in the " + this.factoryName + " factory !");
			throw new NotFoundObjConfigException(errorInfo.toString());
		}
	}
	
	/**
	 * ������ж���֮���Ƿ����ѭ������
	 * @throws DependencyCircleException
	 */
	protected void checkDependenceCircle() throws DependencyCircleException{
		
		//��������·������
		//StringBuilder propertyVisitInfo = null;
		
		//��������·������
		//StringBuilder objectVisitInfo = null;
		
		for(Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : this.objConfigMap.entrySet()){
			
			//��ȡ��������
			String objNm = objConfigEntry.getKey();
			
			//У�����������������������׳��쳣
			List<String> objectParents = new ArrayList<String>();
			objectParents.add(objNm);
			
			if(isDependenceCircle(objConfigEntry.getValue(), objectParents)){
				String visitInfo = this.buildVisitInfo(objectParents);
				throw new DependencyCircleException("it found exsits object dependcy circle : " + visitInfo);				
			}
		}
	}
	
	/**
	 * ��������·��
	 * @param visitSet
	 * @return
	 */
	protected String buildVisitInfo(List<String> visitSet){
		
		boolean first = true;
		StringBuilder visitInfo = new StringBuilder();
		for ( String name : visitSet){
			if(!first){
				visitInfo.append("--->");
			}
			visitInfo.append(name);
			if(first){
				first = false;
			}
		}
		return visitInfo.toString();
	}
	
	/**
	 * ���һ��������Ƿ�ͬ��������ѭ������
	 * @param objConfig ����������Ϣ
	 * @param objectParents ����·��
	 * @return true : ���ڣ� false : ������
	 */
	protected boolean isDependenceCircle(IBLNFactoryConfigEntity objConfig, List<String> objectParents){
		
		boolean foundIt = false;
		
		//��������������͵����ԣ�ֱ���ж����Ե�ֵ�������ж����Ե��ӽڵ�ֵ
		String collectionType = objConfig.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONTYPE);
		if(IBLNFactoryConfig.VALUE_CREATOR_FROM_THIS_FACTORY.equals(objConfig.getAttr(IBLNFactoryConfig.ATTR_CREATOR))){
			
			String objNm = objConfig.getAttr(IBLNFactoryConfig.ATTR_CLASS);
			foundIt = this.dependenceCircleRule(objNm, objectParents);
			if(foundIt){
				return true;
			}
			
		}else if( null != collectionType && !"".equals(collectionType)){
			
			List<IBLNFactoryConfigEntity> subProps = objConfig.getSubEntitys();
			for (IBLNFactoryConfigEntity subProp : subProps){
				foundIt = this.isDependenceCircle(subProp, objectParents);
				if(foundIt){
					return true;
				}						
			}
		}else{
			
			//��������б�
			Map<String, IBLNFactoryConfigEntity> propertyMap = objConfig.getPropertyMap();
			for ( Map.Entry<String, IBLNFactoryConfigEntity> propertyEntry : propertyMap.entrySet()){
				IBLNFactoryConfigEntity property = propertyEntry.getValue();
				foundIt = this.isDependenceCircle(property, objectParents);
				if(foundIt){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * ����ѭ���Ĺ���
	 * @param objNm
	 * @param propertyParents
	 * @return
	 */
	protected boolean dependenceCircleRule(String objNm, List<String> propertyParents){
		
		boolean foundIt = false;
				
		//�������ֱ�ӷ��أ�������һ���������
		if(propertyParents.contains(objNm)){
			foundIt = true;
			propertyParents.add(objNm);
		}else{
			List<String> propertyParentsNew = new ArrayList<String>(propertyParents.size());
			propertyParentsNew.addAll(propertyParents);
			
			propertyParentsNew.add(objNm);
			foundIt = isDependenceCircle(this.objConfigMap.get(objNm), propertyParentsNew);
			if(foundIt){
				propertyParents.clear();
				propertyParents.addAll(propertyParentsNew);
			}
		}
		
		return foundIt;
	}
	
	/**
	 * ��ʼ����ҪԤװ�صĶ���
	 * @throws Throwable
	 */
	protected void preLoadObjects() throws Throwable{
		
		//����������������Ϊ�գ�ֱ�ӷ���
		if(objConfigMap == null || objConfigMap.isEmpty()){
			return;
		}
		
		//���������Config��װ����Ҫ���Գ�ʼ���Ķ���
		for ( Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : objConfigMap.entrySet()){
			this.preLoadObject(objConfigEntry.getKey(), objConfigEntry.getValue());
		}
	}
	
	/**
	 * ���ݶ����������ϢԤװ�ض���
	 * @param objectName ��������
	 * @param config ������Ϣ
	 * @throws Throwable
	 */
	protected void preLoadObject(String objectName, IBLNFactoryConfigEntity config) throws Throwable{
		
		//��ö���Ļ����
		String activity = config.getAttr(IBLNFactoryConfig.ATTR_ACTIVITY);
		if(IBLNFactoryConfig.VALUE_ACTIVITY_ALWAYS_ONE.equals(activity)){
			String lazyLoading = config.getAttr(IBLNFactoryConfig.ATTR_LAZYLOADING);
			
			//��������ӳټ��أ����ص�����ʵ������
			if(!IBLNFactoryConfig.VALUE_LAZYLOADING_TRUE.equals(lazyLoading)){
				
				this.loadObj(objectName, config);
			}
		}
	}
	
	/**
	 * ͨ���������Լ��ض��󣬲���������
	 * @param config �����������Ϣ
	 * @return �¼��صĶ���
	 * @throws Throwable 
	 */
	protected Object loadObj(String objectName, IBLNFactoryConfigEntity config){
		
		//����������Ϣ��ö��������
		IObjectLoader objLoader = ObjectLoaderFactory.getObjLoader(config);
		
		//���ɶ���
		Object object = null;
		
		try {
			
			String activity = config.getAttr(IBLNFactoryConfig.ATTR_ACTIVITY);
			
			//������ǵ�������ֱ�Ӵ���
			if(!IBLNFactoryConfig.VALUE_ACTIVITY_ALWAYS_ONE.equals(activity)){
				
				object = objLoader.load(this, config);
				if(object instanceof IObjectListener){
					IObjectListener listener = (IObjectListener)object;
					listener.afterLoad(this);
				}
			}else{
				
				
				synchronized (loadLock) {
					
					if (objInstanceMap.containsKey(objectName)) {
						object = objInstanceMap.get(objectName);

					} else {

						//���ض��󣬲���ӵ�ʵ������
						object = objLoader.load(this, config);
						if(object instanceof IObjectListener){
							IObjectListener listener = (IObjectListener)object;
							listener.afterLoad(this);
						}
						
						objInstanceMap.put(objectName, object);
						if (log.isDebugEnabled()) {
							log.debug("cache object " + objectName
									+ " in factory " + factoryName);
						}
					}
				}
			}
		} catch (Throwable e) {
			LoadInstanceErrorException lie = new LoadInstanceErrorException("when load object " + config.getAttr(IBLNFactoryConfig.ATTR_NAME) + " occurs error, in facotry " + this.factoryName);
			lie.initCause(e);
			
			throw lie;
		}
		
		if(object instanceof IObjectListener){
			IObjectListener listener = (IObjectListener)object;
			listener.actived(this);
		}
		
		//���ض���
		return object;
	}
	
	/**
	 * ���ض�ʱ����
	 * @throws ClassNotFoundException 
	 * @throws SchedulerException 
	 */
	@SuppressWarnings("unchecked")
	protected void loadTimerTasks() throws SchedulerException, ClassNotFoundException{
		
		//����������������Ϊ�գ�ֱ�ӷ���
		if(objConfigMap == null || objConfigMap.isEmpty()){
			return;
		}
		
		
		//���������Config��װ�ض�ʱ����
		for ( Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : objConfigMap.entrySet()){
			
			//��ö��������
			IBLNFactoryConfigEntity config = objConfigEntry.getValue();
			
			//��ö���Ĵ������������Ϊ�ռ�������
			String triggerClazz = config.getAttr(IBLNFactoryConfig.ATTR_TIMERTASK_TRIGGER);
			if(!StringUtils.isEmpty(triggerClazz)){
								
				//��������ʵ�ֶ���
				Job job = (Job)this.loadObj(objConfigEntry.getKey(), config);
				
				//��ô�����
				ITaskTriggerBuilder<Trigger> triggerBuilder = (ITaskTriggerBuilder<Trigger>)this.getInstance(triggerClazz);
				Trigger trigger = triggerBuilder.buildTrigger();
				
				//�������
				timerTaskMan.addJob(job, trigger);
			}
		}
		
		//�������ж�ʱ����
		timerTaskMan.startAllTask();
	}
		
	/**
	 * ��MAP2�������MAP1��û�е�KEY��Ϣ
	 * @param <K> Key������
	 * @param <V> Value������
	 * @param map1 Ҫ���Key��Map
	 * @param map2 �Ӹò����в���map1û�е�KEY
	 */
	protected <K, V> Map<K, V> unionMapByKey(Map<K, V> map1, Map<K, V> map2){
		
		if(map2 == null || map2.isEmpty()){
			return map1;
		}
		
		if(map1 == null){
			map1 = new HashMap<K, V>();
		}
		
		for(Map.Entry<K, V> map2Entry : map2.entrySet()){
			K key = map2Entry.getKey();
			if(!map1.containsKey(key)){
				map1.put(key, map2Entry.getValue());
			}
		}
		
		return map1;
	}	
}
