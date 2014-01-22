package com.bln.framework.server.simple;

public interface ISimpleServer {

	/**
	 * Ö´ÐÐ¹¤×÷
	 */
	public abstract void work();

	/**
	 * @return the workerNumbers
	 */
	public abstract int getWorkerNumbers();

	/**
	 * @param workerNumbers the workerNumbers to set
	 */
	public abstract void setWorkerNumbers(int workerNumbers);

	/**
	 * @return the worker
	 */
	public abstract Runnable getWorker();

	/**
	 * @param worker the worker to set
	 */
	public abstract void setWorker(Runnable worker);

}