/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.param;

/**
 * 参数Bean
 */
public class Param implements IParam, Comparable<IParam> {

	/**
	 * 参数名称
	 */
	String name = null;
	
	/**
	 * 参数值
	 */
	String value = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.IParam#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.IParam#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.IParam#getValue()
	 */
	public String getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.IParam#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder("Param ");
		
		//tableName
		info.append(name);
		
		//value
		info.append(", value = ").append(value).append("\r\n");

		//返回结果
		return info.toString();		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Param other = (Param) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(IParam o) {
		return this.getName().compareTo(o.getName());
	}
}
