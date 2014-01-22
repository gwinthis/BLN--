/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.jdbc.converter.ConverterBase;
import com.bln.framework.persist.jdbc.converter.IConverter;
import com.bln.framework.persist.jdbc.converter.exception.ConvertException;
import com.bln.framework.persist.jdbc.converter.util.BlobImpl;

/**
 * 字符类型转换器
 */
public class BlobConverter extends ConverterBase implements IConverter{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#convert(java.lang.String)
	 */
	public Object convert(String val) {
		
		if (StringUtils.isEmpty(val)){
			return null;
		}
		
		Object obj = null;
		try {

			byte[] bytes = Base64.decodeBase64(val);
			Blob blob = new BlobImpl(bytes);
			//blob.setBytes(0, bytes);

			obj = blob;
			
		} catch (Throwable e) {
			ConvertException ce = new ConvertException();
			ce.initCause(e);
			throw ce;
		}
		
		return obj;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.lang.Object)
	 */
	public String toString(Object obj) {
		byte[] bytes;
		try {
			bytes = getBlobAsBytes((Blob)obj);
		} catch (Throwable e) {
			throw new ConvertException(" expect dateFormats is not null in class " + this.getClass().getName());
		}
		String info = Base64.encodeBase64String(bytes);
		return info;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, java.lang.String)
	 */
	public String toString(ResultSet rs, String colName) throws SQLException {
		return toString(rs.getBlob(colName));
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, int)
	 */
	public String toString(ResultSet rs, int colIdx) throws SQLException {
		return toString(rs.getBlob(colIdx));
	}
	
	
	/**
	 * 获取blob中的字节内容
	 * @return byte[]
	 * @throws SQLException
	 * @throws IOException
	 */
	protected byte[] getBlobAsBytes(Blob blob) throws SQLException, IOException{
		
		if ( blob == null ){
			return null;
		}

		BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		byte[] buffer = new byte[4096];

		for (int n = 0; -1 != (n = in.read(buffer));) {
			output.write(buffer, 0, n);
		}
		output.flush();

		byte[] bytes = output.toByteArray();
						
		in.close();
		in = null;
		
		output.close();
		output = null;
		
		return bytes;
	}


}
