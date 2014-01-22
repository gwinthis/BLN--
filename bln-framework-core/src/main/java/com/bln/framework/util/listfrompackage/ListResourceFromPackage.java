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
 * �Ӱ��л�ȡ����
 */
public class ListResourceFromPackage {
	
	/**
	 * �Ӹð��ж�ȡ�����ļ�
	 */
	protected String packageName = null;
			
	/**
	 * ���캯��
	 * @param packageName
	 */
	public ListResourceFromPackage(String packageName) {
		super();
		this.packageName = packageName.replace('.', '/');
	}

	/**
	 * �г����µ�������Դ
	 * @param fileType �г������͵��ļ��������ֵΪ�գ����г��ð��µ������ļ�
	 * @return ��ǰ���·�����������Դ
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
	 * ���ļ�ϵͳ�л�ȡ������Ϣ
	 * @param packagePath
	 * @return List<String> �г����а��µ������ļ�
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
	 * ��JAR���л�ȡ������Ϣ
	 * @param packagePath
	 * @return List<String> �г����а��µ������ļ�
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
