/**
 * @author gengw
 * Created on Jun 11, 2012
 */
package com.bln.framework.util.listfrompackage;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 从包中获取数据
 */
public class ListResourceFromPackage {
	
	/**
	 * 从该包中读取配置文件
	 */
	protected String packageName = null;
			
	/**
	 * 构造函数
	 * @param packageName
	 */
	public ListResourceFromPackage(String packageName) {
		super();
		this.packageName = packageName.replace('.', '/');
	}

	/**
	 * 列出包下的所有资源
	 * @param fileType 列出该类型的文件，如果该值为空，将列出该包下的所有文件
	 * @return 当前包下符合条件的资源
	 * @throws IOException
	 */
	public List<String> list(String fileType) throws IOException{
		
		if(fileType != null && !fileType.startsWith(".")){
			fileType = "." + fileType.toLowerCase();
		}
		
		List<String> configUrls = new ArrayList<String>();
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName);
		
		for (URL url = null; urls.hasMoreElements(); ){
			
			List<String> configparams = null;
			
			url = urls.nextElement();
			String protocol = url.getProtocol();
			if("file".equals(protocol)){
				String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
				configparams = listFilesInFileSystem(filePath, fileType);
				
			}else if("jar".equals(protocol)){
				JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
				configparams = listFilesInJarFile(jarFile, fileType);
			}
			
			if(configparams != null && configparams.size() > 0){
				configUrls.addAll(configparams);
			}
		}
		//System.out.println(configUrls);
		
		return configUrls;
		
	}
	
	/**
	 * 从文件系统中获取配置信息
	 * @param packagePath
	 * @return List<String> 列出所有包下的配置文件
	 * @throws IOException 
	 */
	protected List<String> listFilesInFileSystem(String packagePath, String fileType) throws IOException{
		
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return null;  
        }
		
		List<String> resources = new ArrayList<String>();
		File[] allFiles = dir.listFiles();
		for ( File resource : allFiles){
			
			String fileName = resource.getName();
			
			if (fileType == null || fileName.toLowerCase().endsWith(fileType)){
				fileName  = "/" + packageName + "/" + fileName;
				resources.add(fileName);
			}
		}
		
		return resources;
	}
	
	/**
	 * 从JAR包中获取配置信息
	 * @param packagePath
	 * @return List<String> 列出所有包下的配置文件
	 * @throws IOException 
	 */
	protected List<String> listFilesInJarFile(JarFile jarFile, String fileType) throws IOException{

		List<String> resources = new ArrayList<String>();
		Enumeration<JarEntry> entries = jarFile.entries();
		
		for ( JarEntry jarEntry = null; entries.hasMoreElements();){
			jarEntry = entries.nextElement();
			String entryName = jarEntry.getName();
			
			if((entryName.startsWith(packageName) && !entryName.endsWith("/")) && (fileType == null || entryName.toLowerCase().endsWith(fileType))){
				resources.add("/" + entryName);
			}
		}
		
		return resources;
	}

}
