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
 * <p>控制反转工厂类</p>
 */
public class BLNFactory extends FactoryBase<Object> implements IBLNFactory, IBLNFactoryObjectConfig{
	
	/**
	 * 加载锁
	 */
	private static final Object loadLock = new Object();
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(BLNFactory.class);
	
	/**
	 * 类别和对象的分隔符
	 */
	public final static String SPLIT_STR = ".";
		
	/**
	 * 父配置名称
	 */
	protected String parentConfigUrl = null;
	
//	/**
//	 * 配置加载路径
//	 */
//	protected String configLoadPath = null;
	
	/**
	 * Factory配置文件
	 */
	protected IBLNFactoryConfig configFile = null;
	
	/**
	 * 定时任务管理对象
	 */
	protected ITimerTaskMan timerTaskMan = new TimerTaskMan();
			
	/**
	 * 对象名称所对应的配置信息
	 * 对象名为 "类别.对象"。比如: T_TEST.DAO
	 */
	protected final Map<String, IBLNFactoryConfigEntity> objConfigMap = Collections.synchronizedMap(new HashMap<String, IBLNFactoryConfigEntity>());
	
	/**
	 * 持久对象的实例池
	 */
	protected final Map<String, Object> objInstanceMap = Collections.synchronizedMap(new HashMap<String, Object>());
	
	/**
	 * 获取所有对象路径名
	 * @return 所有对象路径名
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
		
		//对象名称不能为空
		Assert.paramIsNotNull(objNm, "objNm");

		//根据对象名称获得对象
		Object obj = null;
		
		//如果在实例池中存在，直接从池中获取，否则生成新实例
		if(objInstanceMap.containsKey(objNm)){
			
			obj = objInstanceMap.get(objNm);
			
		}else{
			
			//获得对象的配置
			IBLNFactoryConfigEntity config = objConfigMap.get(objNm);
			if(config == null){
				throw new NewInstanceErrorException(objNm + " is not in " + this.factoryName + " factory!");
			}
			
			//获得对象
			try{
				obj = this.loadObj(objNm, config);
			}catch(Throwable e){
				NewInstanceErrorException goeException = new NewInstanceErrorException();
				goeException.initCause(e);
				throw goeException;
			}

			//如果对象的活动周期为始终存在并且需要延迟加载，加载之后添加到对象实例池中
			if(IBLNFactoryConfig.VALUE_ACTIVITY_ALWAYS_ONE.equals(config.getAttr(IBLNFactoryConfig.ATTR_ACTIVITY)) 
			&& IBLNFactoryConfig.VALUE_LAZYLOADING_TRUE.equals(config.getAttr(IBLNFactoryConfig.ATTR_LAZYLOADING))){
				
				//添加到实例池中
				objInstanceMap.put(objNm, obj);
			}
		}
		
		//返回实例
		return obj;
	}

	/**
	 * 构造函数
	 */
	public BLNFactory(IBLNFactoryConfig configFile)throws InitFactoryException{
		this.configFile = configFile;
		init();
	}
	
	/**
	 * 初始化容器
	 */
	protected void init() throws InitFactoryException{
		
		try {
			
			//1.初始化工厂配置文件
			//configFile = initConifg();
			Assert.paramIsNotNull(configFile, "configFile","Please Initialize Config File!");
			
			//1.2设置工厂描述信息
			IBLNFactoryConfigEntity configEntity = configFile.getPropertyOfObjects();
			
			//设置工厂名称
			this.factoryName = configEntity.getAttr(IBLNFactoryConfig.ATTR_NAME);
			if(log.isDebugEnabled()){
				log.debug("parsing IocFactory " + factoryName);
			}
			
//			//设置父配置名称
//			parentConfigUrl = configEntity.getAttr(IBLNFactoryConfig.ATTR_EXTENDS);
//			
//			//2.解析父类配置信息
//			configLoadPath = parseParentConfigs(parentConfigUrl);
			
			//3.获得所有的配置信息
			Map<String, List<IBLNFactoryConfigEntity>> configMap = configFile.getAllConfigEntityOfObjs();
			if (configMap == null || configMap.isEmpty()) {
				return;
			}
			
			//3.初始化配置信息
			parseConfigs(configMap);
			
			//4.检查需要工厂生成的特性在工厂配置中是否存在
			checkProperty();
			
			//5.校验是否存在循环依赖
			checkDependenceCircle();
			
			//6.初始化预装载的对象
			preLoadObjects();
			
			//7.初始化定时器任务
			loadTimerTasks();
			
		} catch (Throwable e) {
			InitFactoryException initFactoryException = new InitFactoryException("factory " + this.factoryName + " init error!");
			initFactoryException.initCause(e);
			throw initFactoryException;
		}
	}
			
	/**
	 * 解析objs下的所有配置信息
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
	 * 解析IocFactoryConfigEntity的配置信息
	 * @param parentPropertyMap
	 * @param CatogryConfigs
	 */
	protected void parseEntityConfig(IBLNFactoryConfigEntity pIocFactoryConfigEntity, IBLNFactoryConfigEntity entityConfig){
		
		//获得实体名称
		String entityNm = entityConfig.getAttr(IBLNFactoryConfig.ATTR_NAME);

		//1.如果存在父类，添加父类的信息
		if(pIocFactoryConfigEntity != null){
			
			//1.1添加父类名称
			String pEntityNm = pIocFactoryConfigEntity.getAttr(IBLNFactoryConfig.ATTR_NAME);
			entityNm = new StringBuilder(pEntityNm).append(SPLIT_STR).append(entityNm).toString();
			
			entityConfig.setAttr(IBLNFactoryConfig.ATTR_NAME, entityNm);

			//1.2处理属性，添加只在父类存在的属性
			entityConfig.setAttrMap(unionMapByKey(entityConfig.getAttrMap(), pIocFactoryConfigEntity.getAttrMap()));

			//1.3处理特性，添加只在父类存在的特性
			entityConfig.setPropertyMap(unionMapByKey(entityConfig.getPropertyMap(), pIocFactoryConfigEntity.getPropertyMap()));
		}

		//2.解析子配置信息
		List<IBLNFactoryConfigEntity> subEntityConfigs = entityConfig.getSubEntitys();
		if(subEntityConfigs != null && !subEntityConfigs.isEmpty()){
			for ( IBLNFactoryConfigEntity subEntityConfig : subEntityConfigs){
				parseEntityConfig(entityConfig, subEntityConfig);		
			}
		}
		
		//3.如果该配置为对象类型，添加到对象配置池中
		String configType = entityConfig.getType();
		if(IBLNFactoryConfig.NODE_OBJECT_NM.equals(configType)){
			this.objConfigMap.put(entityNm, entityConfig);
		}
	}
	
	/**
	 * 检查需要从工厂生成属性名称是否正确
	 * @return
	 * @throws InitFactoryException
	 */
	protected void checkProperty() throws NotFoundObjConfigException{
		
		for(Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : this.objConfigMap.entrySet()){
			
			IBLNFactoryConfigEntity objConfig = objConfigEntry.getValue();
			
			Map<String, IBLNFactoryConfigEntity> propertyMap = objConfig.getPropertyMap();
			if(propertyMap != null && !propertyMap.isEmpty()){
				
				for (Map.Entry<String, IBLNFactoryConfigEntity> propertyEntry : propertyMap.entrySet()){
					
					//判断需要工厂生成的属性是否在工厂配置库中存在
					IBLNFactoryConfigEntity propertyConfig = propertyEntry.getValue();
					
					//如果需要从工厂生成
					if(IBLNFactoryConfig.VALUE_CREATOR_FROM_THIS_FACTORY.equals(propertyConfig.getAttr(IBLNFactoryConfig.ATTR_CREATOR))){
						
						//获得容器类型
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
	 * 校验特性实例是否在配置中存在
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
	 * 检查所有对象之间是否存在循环依赖
	 * @throws DependencyCircleException
	 */
	protected void checkDependenceCircle() throws DependencyCircleException{
		
		//特性依赖路径变量
		//StringBuilder propertyVisitInfo = null;
		
		//对象依赖路径变量
		//StringBuilder objectVisitInfo = null;
		
		for(Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : this.objConfigMap.entrySet()){
			
			//获取对象名称
			String objNm = objConfigEntry.getKey();
			
			//校验对象依赖，如果发现依赖抛出异常
			List<String> objectParents = new ArrayList<String>();
			objectParents.add(objNm);
			
			if(isDependenceCircle(objConfigEntry.getValue(), objectParents)){
				String visitInfo = this.buildVisitInfo(objectParents);
				throw new DependencyCircleException("it found exsits object dependcy circle : " + visitInfo);				
			}
		}
	}
	
	/**
	 * 创建访问路径
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
	 * 检查一个对象的是否同其对象存在循环依赖
	 * @param objConfig 对象配置信息
	 * @param objectParents 依赖路径
	 * @return true : 存在， false : 不存在
	 */
	protected boolean isDependenceCircle(IBLNFactoryConfigEntity objConfig, List<String> objectParents){
		
		boolean foundIt = false;
		
		//如果不是容器类型的特性，直接判断特性的值，否则判断特性的子节点值
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
			
			//获得特性列表
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
	 * 依赖循环的规则
	 * @param objNm
	 * @param propertyParents
	 * @return
	 */
	protected boolean dependenceCircleRule(String objNm, List<String> propertyParents){
		
		boolean foundIt = false;
				
		//如果发现直接返回，否则到下一层继续查找
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
	 * 初始化需要预装载的对象
	 * @throws Throwable
	 */
	protected void preLoadObjects() throws Throwable{
		
		//如果对象的配置属性为空，直接返回
		if(objConfigMap == null || objConfigMap.isEmpty()){
			return;
		}
		
		//遍历对象的Config，装载需要可以初始化的对象
		for ( Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : objConfigMap.entrySet()){
			this.preLoadObject(objConfigEntry.getKey(), objConfigEntry.getValue());
		}
	}
	
	/**
	 * 根据对象的配置信息预装载对象
	 * @param objectName 对象名称
	 * @param config 配置信息
	 * @throws Throwable
	 */
	protected void preLoadObject(String objectName, IBLNFactoryConfigEntity config) throws Throwable{
		
		//获得对象的活动周期
		String activity = config.getAttr(IBLNFactoryConfig.ATTR_ACTIVITY);
		if(IBLNFactoryConfig.VALUE_ACTIVITY_ALWAYS_ONE.equals(activity)){
			String lazyLoading = config.getAttr(IBLNFactoryConfig.ATTR_LAZYLOADING);
			
			//如果不是延迟加载，加载到对象实例池中
			if(!IBLNFactoryConfig.VALUE_LAZYLOADING_TRUE.equals(lazyLoading)){
				
				this.loadObj(objectName, config);
			}
		}
	}
	
	/**
	 * 通过配置属性加载对象，并设置特性
	 * @param config 对象的配置信息
	 * @return 新加载的对象
	 * @throws Throwable 
	 */
	protected Object loadObj(String objectName, IBLNFactoryConfigEntity config){
		
		//根据配置信息获得对象加载器
		IObjectLoader objLoader = ObjectLoaderFactory.getObjLoader(config);
		
		//生成对象
		Object object = null;
		
		try {
			
			String activity = config.getAttr(IBLNFactoryConfig.ATTR_ACTIVITY);
			
			//如果不是单例对象，直接创建
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

						//加载对象，并添加到实例池中
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
		
		//返回对象
		return object;
	}
	
	/**
	 * 加载定时任务
	 * @throws ClassNotFoundException 
	 * @throws SchedulerException 
	 */
	@SuppressWarnings("unchecked")
	protected void loadTimerTasks() throws SchedulerException, ClassNotFoundException{
		
		//如果对象的配置属性为空，直接返回
		if(objConfigMap == null || objConfigMap.isEmpty()){
			return;
		}
		
		
		//遍历对象的Config，装载定时任务
		for ( Map.Entry<String, IBLNFactoryConfigEntity> objConfigEntry : objConfigMap.entrySet()){
			
			//获得对象的配置
			IBLNFactoryConfigEntity config = objConfigEntry.getValue();
			
			//获得对象的触发器，如果不为空加载任务
			String triggerClazz = config.getAttr(IBLNFactoryConfig.ATTR_TIMERTASK_TRIGGER);
			if(!StringUtils.isEmpty(triggerClazz)){
								
				//创建任务实现对象
				Job job = (Job)this.loadObj(objConfigEntry.getKey(), config);
				
				//获得触发器
				ITaskTriggerBuilder<Trigger> triggerBuilder = (ITaskTriggerBuilder<Trigger>)this.getInstance(triggerClazz);
				Trigger trigger = triggerBuilder.buildTrigger();
				
				//添加任务
				timerTaskMan.addJob(job, trigger);
			}
		}
		
		//启动所有定时任务
		timerTaskMan.startAllTask();
	}
		
	/**
	 * 从MAP2中添加在MAP1中没有的KEY信息
	 * @param <K> Key的类型
	 * @param <V> Value的类型
	 * @param map1 要添加Key的Map
	 * @param map2 从该参数中查找map1没有的KEY
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
