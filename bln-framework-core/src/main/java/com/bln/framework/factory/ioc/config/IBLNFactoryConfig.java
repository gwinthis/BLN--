package com.bln.framework.factory.ioc.config;

import java.util.List;
import java.util.Map;

import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.filestru.xml.IXMLFileStru;

/**
 * 工厂配置接口
 */
public interface IBLNFactoryConfig extends IXMLFileStru{
	
	/**
	 * objects节点名称
	 */
	public static final String NODE_OBJS_NM = "objects";
	
	/**
	 * category节点名称
	 */
	public static final String NODE_CATEGORY_NM = "category";
	
	/**
	 * object节点名称
	 */
	public static final String NODE_OBJECT_NM = "object";
	
	/**
	 * property节点名称
	 * 所属于catogry节点或object节点之下
	 */
	public static final String NODE_PROPERTY_NM = "property";
	
	/**
	 * <p>prop节点名称</p>
	 * 所属于property节点下，表示property的元素
	 */
	public static final String NODE_PROP_NM = "prop";

	/**
	 * <p>builder节点名称</p>
	 * 所属于object节点下，表示对象的构造器节点
	 */
	public static final String NODE_BUILDER_NM = "builder";

	/**
	 * <p>param节点名称</p>
	 * 表示参数信息
	 */
	public static final String NODE_PARM_NM = "param";
	
	/**
	 * name属性名称
	 * 对象或类别的名称
	 */
	public static final String ATTR_NAME = "name";

	/**
	 * extends属性名称
	 * 配置文件所继承的父配置对象路径
	 */
	public static final String ATTR_EXTENDS = "extends";

	/**
	 * mixin属性名称
	 * 混入该配置文件配置对象路径
	 */
	public static final String ATTR_MIXIN = "mixin";
	
	/**
	 * <p>class属性名称</p>
	 * 对象的类路径
	 */
	public static final String ATTR_CLASS = "class";
	
	/**
	 * <p>activity属性名称</p>
	 * <p>对象的活动范围周期</p>
	 * 默认为<code>ACTIVITY_ALWAYS_NEW<code/>
	 */
	public static final String ATTR_ACTIVITY = "activity";
	
	/**
	 * <p>lazyLoading属性名称</p>
	 * 用于对象的延迟加载，只用活动周期为ALWAYS_ONE的情况下才生效。
	 * 默认为<code>LAZYLOADING_FALSE<code/>
	 */
	public static final String ATTR_LAZYLOADING = "lazyLoading";

	/**
	 * <p>property节点中的key属性名称</p>
	 * 表示特性的key属性
	 */
	public static final String ATTR_KEY = "key";
	
	/**
	 * <p>property节点中的value属性名称</p>
	 * 表示特性的值或其他对象的值
	 */
	public static final String ATTR_VALUE = "value";
	
	/**
	 * <p>property节点中的creator属性名称</p>
	 * 用于按何种方式生成特性对象。默认为<code>ATTR_CREATOR_DONOT</code>
	 */
	public static final String ATTR_CREATOR = "creator";
	
	/**
	 * <p>property节点中的component_creator属性名称</p>
	 * 用于按何种方式生成特性的属性对象。默认为<code>ATTR_CREATOR_DONOT</code>
	 */
	public static final String ATTR_COMPONENT_CREATOR = "component_creator";

	/**
	 * <p>property节点中的collection_type属性名称</p>
	 * <p>容器类型</p>
	 * <p> 比如：array</p>
	 */
	public static final String ATTR_COLLECTIONTYPE = "collection_type";

	/**
	 * <p>property节点中的collection_class属性名称</p>
	 * <p>容器的类名称</p>
	 * <p>比如：java.util.HashMap</p>
	 */
	public static final String ATTR_COLLECTIONCLASS = "collection_class";
	
	/**
	 * <p>property节点中的component_class属性名称</p>
	 * 容器中成员的类名称
	 */
	public static final String ATTR_COMPONENT_CLASS = "component_class";

	/**
	 * <p>对象节点中的timertask_trigger属性名称</p>
	 * 如果该属性不为空，表示该对象可以作为定时任务执行，该对象需要继承于Job接口
	 */
	public static final String ATTR_TIMERTASK_TRIGGER = "timertask_trigger";
	
	/**
	 * 真值
	 */
	public static final String VAL_TRUE = "true";
	
	/**
	 * 假值
	 */
	public static final String VAL_FALSE = "false";
	
	/**
	 * 当前工厂标识符
	 */
	public static final String VAL_CURRENT_FACTORY_IDENTIFIER = "this";
	
	/**
	 * <p>始终是一个实例的对象活动周期</p>
	 * <p>对象定义了这种属性，表示每次请求该对象名称都返回同一个实例</p>
	 * 对象加载之后始终存在，直到工厂关闭
	 */
	public static final String VALUE_ACTIVITY_ALWAYS_ONE = "always_one";

	/**
	 * <p>每次都是新实例的对象活动周期</p>
	 * 定义了这种属性，表示每次请求都生成新的对象
	 */
	public static final String VALUE_ACTIVITY_ALWAYS_NEW = "always_new";
	
	/**
	 * <p>采用延迟加载的lazyLoading属性</p>
	 * 表示对象采用延迟加载
	 */
	public static final String VALUE_LAZYLOADING_TRUE = VAL_TRUE;
	
	/**
	 * <p>不采用延迟加载的lazyLoading属性名称</p>
	 * 表示对象不采用延迟加载
	 */
	public static final String VALUE_LAZYLOADING_FALSE = VAL_FALSE;
	
	/**
	 * <p>从value属性中获取值，当作字符串处理</p>
	 */
	public static final String VALUE_CREATOR_STRING_VALUE = "string_value";
	
	/**
	 * <p>表示每次赋值特性的时候可以从Value转换成指定的类的对象。</p>
	 * 特性值为要转换的值
	 * 类路径为要转换成的类型
	 */
	public static final String VALUE_CREATOR_TRANS_FROM_VALUE = "transFromValue";

	/**
	 * <p>新生成对象</p>
	 * 
	 */
	public static final String VALUE_CREATOR_NEW_OBJECT = "new_object";
	
	/**
	 * <p>表示每次赋值特性的时候需要生成，实例从当前工厂中获取。</p>
	 * 类路径为对象名称
	 */
	public static final String VALUE_CREATOR_FROM_THIS_FACTORY = "from_this_factory";

	/**
	 * <p>表示每次赋值特性的时候需要生成，实例从当前工厂中获取。</p>
	 * 类路径为对象名称
	 */
	public static final String VALUE_CREATOR_FROM_OTHER_FACTORY = "from_other_factory";

	/**
	 * <p>表示实例从指定的工厂中获取。</p>
	 * class为工厂名称,  value为对象名称
	 */
	public static final String VALUE_CREATOR_FROM_THE_FACTORY = "from_the_factory";

	/**
	 * <p>表示每次赋值特性的时候需要生成，实例为外部服务，需要按照定义外部服务的方式定义。</p>
	 * 特性值为服务对象信息
	 */
	public static final String VALUE_CREATOR_OTS_LOCATER = "ots_locater";

	/**
	 * <p>表示每次赋值特性的时候需要生成，实例从builder中生成，需要按照定义构造器的方式定义。</p>
	 * 类路径为构造器路径，特性值为建造对象的参数
	 */
	public static final String VALUE_CREATOR_BUILDER = "builder";

	/**
	 * <p>从工厂中心中获取工厂对象。</p>
	 * 类路径为工厂名称
	 */
	public static final String VALUE_CREATOR_FACTORYCENTER = "factorycenter";

	/**
	 * <p>从工厂中心中获取指定路径的对象。</p>
	 * 类路径为对象名称
	 */
	public static final String VALUE_CREATOR_LISTOBJECTSFROMFACTORYCENTER = "list_objects_from_factorycenter";
	
	/**
	 * <p>表示每次赋值特性为空值。</p>
	 */
	public static final String VALUE_CREATOR_NULLVALE = "null_value";

	/**
	 * <p>从包中获取资源。</p>
	 */
	public static final String VALUE_CREATOR_FROMPACKAGE = "from_package";

	/**
	 * <p>表示容器为数组类型</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_ARRAY = "array";

	/**
	 * <p>表示容器为Java的容器类型</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_COLLECTION = "collection";

	/**
	 * <p>表示容器为map的映射类型</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_MAP = "map";

	/**
	 * <p>表示容器为properties的映射类型</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_PROPERTIES = "properties";

	
	/**
	 * 获得所有catogry节点
	 * @return 获取objects节点下的所有catogry节点, 按List < ConfigEntity > 返回
	 */
	public abstract Map<String, List<IBLNFactoryConfigEntity>> getAllConfigEntityOfObjs();
	
	/**
	 * 获得objs的属性信息
	 * @return objs的配置对象
	 */
	public IBLNFactoryConfigEntity getPropertyOfObjects();

	/**
	 * 获取工厂名称
	 * @return
	 */
	public String getFactoryName();

	/**
	 * @param theConfig 要扩展的节点
	 * @param expandType 扩展方式，具体参见ExpandType枚举
	 */
	public void expand(IBLNFactoryConfig theConfig, ExpandType expandType);

	/**
	 * 获取所有的object节点的配置
	 * @return 对象名和所对应的配置
	 */
	public Map<String, List<IBLNFactoryConfigEntity>> getAllEntityOfObject();

	/**
	 * 是否存在父配置
	 * @return true: 存在, false: 不存在
	 */
	public boolean hasParent();

	/**
	 * 获取父类配置的URL
	 * @return parentConfigUrl
	 */
	public String getParentConfigUrl();

	/**
	 * 获取父类配置的URL
	 * @return parentConfigUrl
	 */
	public String[] getMixinConfigUrls();

	/**
	 * @return the ancestorNames
	 */
	public String[] getAncestorNames();

	/**
	 * @param ancestorNames the ancestorNames to set
	 */
	public void setAncestorNames(String[] ancestorNames);

	/**
	 * 是否是指定工厂的类型
	 * @param factoryName
	 * @return
	 */
	public boolean isTypeOf(String factoryName);
	
	/**
	 * 扩展方式
	 */
	enum ExpandType{
		
		/**
		 * 全部覆盖
		 */
		OVERRIDE_TYPE_ALL, 
		
		/**
		 * 只覆盖下面的节点
		 */
		OVERRIDE_TYPE_ONLYSUB
	};
}