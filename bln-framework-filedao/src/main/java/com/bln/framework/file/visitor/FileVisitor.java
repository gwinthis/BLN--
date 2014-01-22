/**
 * @author gengw
 * Created at 2013-03-06
 */
package com.bln.framework.file.visitor;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件访问器
 */
public class FileVisitor {
	
	/**
	 * 路径分隔符
	 */
	protected String pathSeperator = ".";
	
	/**
	 * 文件处理器
	 */
	protected IFileHandler fileHandler = null;

	/**
	 * 文件过滤器
	 */
	protected IFileFilter fileFilter = null;
	
	/**
	 * 
	 */
	protected IFilesSorter filesSorter = null;
	
	/**
	 * 访问根目录下的文件
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
					
					//过滤器
					if(fileFilter != null && !fileFilter.dirFilter(file)){
						continue;
					}
					
					if(!StringUtils.isEmpty(parentPath)){
						fileName = parentPath + pathSeperator + fileName;
					}
					
					visitFiles(file, fileName);
					
				}else{
					
					//是否访问根目录下的文件
					if(!visitRootFile && StringUtils.isEmpty(parentPath)){
						continue;
					}
					
					//过滤器
					if(fileFilter != null && !fileFilter.fileFilter(file)){
						continue;
					}
					
					//处理文件
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
