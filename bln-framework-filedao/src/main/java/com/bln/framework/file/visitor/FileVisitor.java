/**
 * @author gengw
 * Created at 2013-03-06
 */
package com.bln.framework.file.visitor;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

/**
 * �ļ�������
 */
public class FileVisitor {
	
	/**
	 * ·���ָ���
	 */
	protected String pathSeperator = ".";
	
	/**
	 * �ļ�������
	 */
	protected IFileHandler fileHandler = null;

	/**
	 * �ļ�������
	 */
	protected IFileFilter fileFilter = null;
	
	/**
	 * 
	 */
	protected IFilesSorter filesSorter = null;
	
	/**
	 * ���ʸ�Ŀ¼�µ��ļ�
	 */
	protected boolean visitRootFile = true;
	
	
	/**
	 * @param path
	 * @throws IOException 
	 */
	public void visitFiles(String path) throws IOException{
		
		File root = new File(path);
		this.visitFiles(root, null);
	}
	
	/**
	 * @param root
	 * @param parentPath
	 * @throws IOException 
	 */
	protected void visitFiles(File root, String parentPath) throws IOException{

		File[] files = root.listFiles();
		String fileName = null;
		
		if(files != null && files.length > 0){
			
			if(this.filesSorter != null){
				files = filesSorter.sort(files);
			}
			for ( File file : files){
				
				fileName = file.getName();
				
				if(file.isDirectory()){
					
					//������
					if(fileFilter != null && !fileFilter.dirFilter(file)){
						continue;
					}
					
					if(!StringUtils.isEmpty(parentPath)){
						fileName = parentPath + pathSeperator + fileName;
					}
					
					visitFiles(file, fileName);
					
				}else{
					
					//�Ƿ���ʸ�Ŀ¼�µ��ļ�
					if(!visitRootFile && StringUtils.isEmpty(parentPath)){
						continue;
					}
					
					//������
					if(fileFilter != null && !fileFilter.fileFilter(file)){
						continue;
					}
					
					//�����ļ�
					fileHandler.handle(file, parentPath);
				}
			}
		}
		
	}


	public IFileHandler getFileHandler() {
		return fileHandler;
	}


	public void setFileHandler(IFileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}


	public IFileFilter getFileFilter() {
		return fileFilter;
	}


	public void setFileFilter(IFileFilter fileFilter) {
		this.fileFilter = fileFilter;
	}

	public boolean isVisitRootFile() {
		return visitRootFile;
	}

	public void setVisitRootFile(boolean visitRootFile) {
		this.visitRootFile = visitRootFile;
	}

	public String getPathSeperator() {
		return pathSeperator;
	}

	public void setPathSeperator(String pathSeperator) {
		this.pathSeperator = pathSeperator;
	}

	public IFilesSorter getFilesSorter() {
		return filesSorter;
	}

	public void setFilesSorter(IFilesSorter filesSorter) {
		this.filesSorter = filesSorter;
	}
}
