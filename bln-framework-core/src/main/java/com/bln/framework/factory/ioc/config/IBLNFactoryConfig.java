package com.bln.framework.factory.ioc.config;

import java.util.List;
import java.util.Map;

import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.filestru.xml.IXMLFileStru;

/**
 * �������ýӿ�
 */
public interface IBLNFactoryConfig extends IXMLFileStru{
	
	/**
	 * objects�ڵ�����
	 */
	public static final String NODE_OBJS_NM = "objects";
	
	/**
	 * category�ڵ�����
	 */
	public static final String NODE_CATEGORY_NM = "category";
	
	/**
	 * object�ڵ�����
	 */
	public static final String NODE_OBJECT_NM = "object";
	
	/**
	 * property�ڵ�����
	 * ������catogry�ڵ��object�ڵ�֮��
	 */
	public static final String NODE_PROPERTY_NM = "property";
	
	/**
	 * <p>prop�ڵ�����</p>
	 * ������property�ڵ��£���ʾproperty��Ԫ��
	 */
	public static final String NODE_PROP_NM = "prop";

	/**
	 * <p>builder�ڵ�����</p>
	 * ������object�ڵ��£���ʾ����Ĺ������ڵ�
	 */
	public static final String NODE_BUILDER_NM = "builder";

	/**
	 * <p>param�ڵ�����</p>
	 * ��ʾ������Ϣ
	 */
	public static final String NODE_PARM_NM = "param";
	
	/**
	 * name��������
	 * �������������
	 */
	public static final String ATTR_NAME = "name";

	/**
	 * extends��������
	 * �����ļ����̳еĸ����ö���·��
	 */
	public static final String ATTR_EXTENDS = "extends";

	/**
	 * mixin��������
	 * ����������ļ����ö���·��
	 */
	public static final String ATTR_MIXIN = "mixin";
	
	/**
	 * <p>class��������</p>
	 * �������·��
	 */
	public static final String ATTR_CLASS = "class";
	
	/**
	 * <p>activity��������</p>
	 * <p>����Ļ��Χ����</p>
	 * Ĭ��Ϊ<code>ACTIVITY_ALWAYS_NEW<code/>
	 */
	public static final String ATTR_ACTIVITY = "activity";
	
	/**
	 * <p>lazyLoading��������</p>
	 * ���ڶ�����ӳټ��أ�ֻ�û����ΪALWAYS_ONE������²���Ч��
	 * Ĭ��Ϊ<code>LAZYLOADING_FALSE<code/>
	 */
	public static final String ATTR_LAZYLOADING = "lazyLoading";

	/**
	 * <p>property�ڵ��е�key��������</p>
	 * ��ʾ���Ե�key����
	 */
	public static final String ATTR_KEY = "key";
	
	/**
	 * <p>property�ڵ��е�value��������</p>
	 * ��ʾ���Ե�ֵ�����������ֵ
	 */
	public static final String ATTR_VALUE = "value";
	
	/**
	 * <p>property�ڵ��е�creator��������</p>
	 * ���ڰ����ַ�ʽ�������Զ���Ĭ��Ϊ<code>ATTR_CREATOR_DONOT</code>
	 */
	public static final String ATTR_CREATOR = "creator";
	
	/**
	 * <p>property�ڵ��е�component_creator��������</p>
	 * ���ڰ����ַ�ʽ�������Ե����Զ���Ĭ��Ϊ<code>ATTR_CREATOR_DONOT</code>
	 */
	public static final String ATTR_COMPONENT_CREATOR = "component_creator";

	/**
	 * <p>property�ڵ��е�collection_type��������</p>
	 * <p>��������</p>
	 * <p> ���磺array</p>
	 */
	public static final String ATTR_COLLECTIONTYPE = "collection_type";

	/**
	 * <p>property�ڵ��е�collection_class��������</p>
	 * <p>������������</p>
	 * <p>���磺java.util.HashMap</p>
	 */
	public static final String ATTR_COLLECTIONCLASS = "collection_class";
	
	/**
	 * <p>property�ڵ��е�component_class��������</p>
	 * �����г�Ա��������
	 */
	public static final String ATTR_COMPONENT_CLASS = "component_class";

	/**
	 * <p>����ڵ��е�timertask_trigger��������</p>
	 * ��������Բ�Ϊ�գ���ʾ�ö��������Ϊ��ʱ����ִ�У��ö�����Ҫ�̳���Job�ӿ�
	 */
	public static final String ATTR_TIMERTASK_TRIGGER = "timertask_trigger";
	
	/**
	 * ��ֵ
	 */
	public static final String VAL_TRUE = "true";
	
	/**
	 * ��ֵ
	 */
	public static final String VAL_FALSE = "false";
	
	/**
	 * ��ǰ������ʶ��
	 */
	public static final String VAL_CURRENT_FACTORY_IDENTIFIER = "this";
	
	/**
	 * <p>ʼ����һ��ʵ���Ķ�������</p>
	 * <p>���������������ԣ���ʾÿ������ö������ƶ�����ͬһ��ʵ��</p>
	 * �������֮��ʼ�մ��ڣ�ֱ�������ر�
	 */
	public static final String VALUE_ACTIVITY_ALWAYS_ONE = "always_one";

	/**
	 * <p>ÿ�ζ�����ʵ���Ķ�������</p>
	 * �������������ԣ���ʾÿ�����������µĶ���
	 */
	public static final String VALUE_ACTIVITY_ALWAYS_NEW = "always_new";
	
	/**
	 * <p>�����ӳټ��ص�lazyLoading����</p>
	 * ��ʾ��������ӳټ���
	 */
	public static final String VALUE_LAZYLOADING_TRUE = VAL_TRUE;
	
	/**
	 * <p>�������ӳټ��ص�lazyLoading��������</p>
	 * ��ʾ���󲻲����ӳټ���
	 */
	public static final String VALUE_LAZYLOADING_FALSE = VAL_FALSE;
	
	/**
	 * <p>��value�����л�ȡֵ�������ַ�������</p>
	 */
	public static final String VALUE_CREATOR_STRING_VALUE = "string_value";
	
	/**
	 * <p>��ʾÿ�θ�ֵ���Ե�ʱ����Դ�Valueת����ָ������Ķ���</p>
	 * ����ֵΪҪת����ֵ
	 * ��·��ΪҪת���ɵ�����
	 */
	public static final String VALUE_CREATOR_TRANS_FROM_VALUE = "transFromValue";

	/**
	 * <p>�����ɶ���</p>
	 * 
	 */
	public static final String VALUE_CREATOR_NEW_OBJECT = "new_object";
	
	/**
	 * <p>��ʾÿ�θ�ֵ���Ե�ʱ����Ҫ���ɣ�ʵ���ӵ�ǰ�����л�ȡ��</p>
	 * ��·��Ϊ��������
	 */
	public static final String VALUE_CREATOR_FROM_THIS_FACTORY = "from_this_factory";

	/**
	 * <p>��ʾÿ�θ�ֵ���Ե�ʱ����Ҫ���ɣ�ʵ���ӵ�ǰ�����л�ȡ��</p>
	 * ��·��Ϊ��������
	 */
	public static final String VALUE_CREATOR_FROM_OTHER_FACTORY = "from_other_factory";

	/**
	 * <p>��ʾʵ����ָ���Ĺ����л�ȡ��</p>
	 * classΪ��������,  valueΪ��������
	 */
	public static final String VALUE_CREATOR_FROM_THE_FACTORY = "from_the_factory";

	/**
	 * <p>��ʾÿ�θ�ֵ���Ե�ʱ����Ҫ���ɣ�ʵ��Ϊ�ⲿ������Ҫ���ն����ⲿ����ķ�ʽ���塣</p>
	 * ����ֵΪ���������Ϣ
	 */
	public static final String VALUE_CREATOR_OTS_LOCATER = "ots_locater";

	/**
	 * <p>��ʾÿ�θ�ֵ���Ե�ʱ����Ҫ���ɣ�ʵ����builder�����ɣ���Ҫ���ն��幹�����ķ�ʽ���塣</p>
	 * ��·��Ϊ������·��������ֵΪ�������Ĳ���
	 */
	public static final String VALUE_CREATOR_BUILDER = "builder";

	/**
	 * <p>�ӹ��������л�ȡ��������</p>
	 * ��·��Ϊ��������
	 */
	public static final String VALUE_CREATOR_FACTORYCENTER = "factorycenter";

	/**
	 * <p>�ӹ��������л�ȡָ��·���Ķ���</p>
	 * ��·��Ϊ��������
	 */
	public static final String VALUE_CREATOR_LISTOBJECTSFROMFACTORYCENTER = "list_objects_from_factorycenter";
	
	/**
	 * <p>��ʾÿ�θ�ֵ����Ϊ��ֵ��</p>
	 */
	public static final String VALUE_CREATOR_NULLVALE = "null_value";

	/**
	 * <p>�Ӱ��л�ȡ��Դ��</p>
	 */
	public static final String VALUE_CREATOR_FROMPACKAGE = "from_package";

	/**
	 * <p>��ʾ����Ϊ��������</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_ARRAY = "array";

	/**
	 * <p>��ʾ����ΪJava����������</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_COLLECTION = "collection";

	/**
	 * <p>��ʾ����Ϊmap��ӳ������</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_MAP = "map";

	/**
	 * <p>��ʾ����Ϊproperties��ӳ������</p>
	 */
	public static final String VALUE_COLLECTIONTYPE_PROPERTIES = "properties";

	
	/**
	 * �������catogry�ڵ�
	 * @return ��ȡobjects�ڵ��µ�����catogry�ڵ�, ��List < ConfigEntity > ����
	 */
	public abstract Map<String, List<IBLNFactoryConfigEntity>> getAllConfigEntityOfObjs();
	
	/**
	 * ���objs��������Ϣ
	 * @return objs�����ö���
	 */
	public IBLNFactoryConfigEntity getPropertyOfObjects();

	/**
	 * ��ȡ��������
	 * @return
	 */
	public String getFactoryName();

	/**
	 * @param theConfig Ҫ��չ�Ľڵ�
	 * @param expandType ��չ��ʽ������μ�ExpandTypeö��
	 */
	public void expand(IBLNFactoryConfig theConfig, ExpandType expandType);

	/**
	 * ��ȡ���е�object�ڵ������
	 * @return ������������Ӧ������
	 */
	public Map<String, List<IBLNFactoryConfigEntity>> getAllEntityOfObject();

	/**
	 * �Ƿ���ڸ�����
	 * @return true: ����, false: ������
	 */
	public boolean hasParent();

	/**
	 * ��ȡ�������õ�URL
	 * @return parentConfigUrl
	 */
	public String getParentConfigUrl();

	/**
	 * ��ȡ�������õ�URL
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
	 * �Ƿ���ָ������������
	 * @param factoryName
	 * @return
	 */
	public boolean isTypeOf(String factoryName);
	
	/**
	 * ��չ��ʽ
	 */
	enum ExpandType{
		
		/**
		 * ȫ������
		 */
		OVERRIDE_TYPE_ALL, 
		
		/**
		 * ֻ��������Ľڵ�
		 */
		OVERRIDE_TYPE_ONLYSUB
	};
}