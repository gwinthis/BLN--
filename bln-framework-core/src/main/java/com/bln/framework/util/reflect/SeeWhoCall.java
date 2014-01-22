/**
 * @author gengw
 * Created at 2013-7-27
 */
package com.bln.framework.util.reflect;

/**
 * 查看类的调用者
 */
public class SeeWhoCall {

	/**
	 * 获得最后的调用者
	 * @return
	 */
	public static Caller getLastCaller() {
		
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		Caller caller = null;
		if(stackTraceElements.length > 3){
			caller = new Caller(stackTraceElements[2]);
		}

		return caller;
	}

	/**
	 * 获取所有请求者
	 * @return SeeWhoCall.Call[] 请求者对象数组 
	 */
	public static Caller[] getAllCallers() {

		Caller[] callers = null;
		
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if (stackTraceElements != null && stackTraceElements.length > 3) {
			callers = new Caller[stackTraceElements.length - 3];
			for ( int j = 0, i = 3, n = stackTraceElements.length; i < n; i++){
				StackTraceElement stackTraceElement = stackTraceElements[i];
				callers[j++] = new Caller(stackTraceElement);
			}
		}
		
		return callers;
	}

	/**
	 * 调用者bean
	 */
	public static class Caller{
		
		String className = null;
		String fileName = null;
		String methodName = null;
		int lineNumber = 0;
		String info = "";
		String classAndMethod = null;
		
		Caller(StackTraceElement stackTraceElement){
			
			this.className = stackTraceElement.getClassName();
			this.fileName = stackTraceElement.getFileName();
			this.methodName = stackTraceElement.getMethodName();
			this.lineNumber = stackTraceElement.getLineNumber();
			this.info = stackTraceElement.toString();
			this.classAndMethod = className.substring(className.lastIndexOf(".") + 1) + "." + methodName + "()";
		}

		/**
		 * @return the className
		 */
		public String getClassName() {
			return className;
		}

		/**
		 * @param className the className to set
		 */
		public void setClassName(String className) {
			this.className = className;
		}

		/**
		 * @return the fileName
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * @param fileName the fileName to set
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * @return the methodName
		 */
		public String getMethodName() {
			return methodName;
		}

		/**
		 * @param methodName the methodName to set
		 */
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		/**
		 * @return the lineNumber
		 */
		public int getLineNumber() {
			return lineNumber;
		}

		/**
		 * @param lineNumber the lineNumber to set
		 */
		public void setLineNumber(int lineNumber) {
			this.lineNumber = lineNumber;
		}
		
		public String toString(){
			return info;
		}

		/**
		 * @return the classAndMethod
		 */
		public String getClassAndMethod() {
			return classAndMethod;
		}

		/**
		 * @param classAndMethod the classAndMethod to set
		 */
		public void setClassAndMethod(String classAndMethod) {
			this.classAndMethod = classAndMethod;
		}
	}
}
