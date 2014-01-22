/**
 * @author Gengw
 * Created at 2008-11-06
 */
package com.bln.framework.persist.jdbc.template.storeprocedure;

import java.util.ArrayList;
import java.util.List;

import com.bln.framework.base.BaseObj;

/**
 * 存储过程参数类
 */
public class StoreProcedureParameters extends BaseObj implements IStoreProcedureParameters {

	/**
	 * 参数存储器
	 */
	protected final List<SPParameter> parameters = new ArrayList<SPParameter>();

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.template.storeprocedure.ISPParameters#addParameter(java.lang.Object, int, int)
	 */
	public void addParameter(Object value, int inout, int paramType) {
		SPParameter parameter = new SPParameter(value, inout, paramType);
		parameters.add(parameter);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.template.storeprocedure.ISPParameters#getParameters()
	 */
	public List<SPParameter> getParameters() {
		return parameters;
	}

	/**
	 * 存储过程参数Bean
	 */
	public class SPParameter extends BaseObj{

		private Object value = null;

		private int intout;

		private int paramType;

		public SPParameter(Object value, int inout, int paramType) {

			this.value = value;
			this.intout = inout;
			this.paramType = paramType;
		}

		/**
		 * @return the intout
		 */
		public int getIntout() {
			return intout;
		}

		/**
		 * @param intout
		 *            the intout to set
		 */
		public void setIntout(int intout) {
			this.intout = intout;
		}

		/**
		 * @return the paramType
		 */
		public int getParamType() {
			return paramType;
		}

		/**
		 * @param paramType
		 *            the paramType to set
		 */
		public void setParamType(int paramType) {
			this.paramType = paramType;
		}

		/**
		 * @return the value
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(Object value) {
			this.value = value;
		}

	}

}
