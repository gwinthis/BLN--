package com.bln.framework.ejb.sjsb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.bln.framework.biz.service.IService;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.transaction.db.ITransaction;
import com.bln.framework.transaction.db.TransactionBase;
import com.bln.framework.transaction.exception.TransException;

/**
 * XDoclet-based session bean.  The class must be declared
 * public according to the EJB specification.
 *
 * To generate the EJB related files to this EJB:
 *		- Add Standard EJB module to XDoclet project properties
 *		- Customize XDoclet configuration for your appserver
 *		- Run XDoclet
 *
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="TransHandler"
 *           display-name="TransHandler"
 *           description="TransHandler"
 *           jndi-name="ejb/TransHandler"
 *           type="Stateless"
 *           view-type="both"
 *           
 */
public class TransHandlerBean extends TransactionBase implements SessionBean, ITransaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 882459268197323565L;

	/** The session instance */
	private SessionContext context = null;

	public TransHandlerBean() {
		// TODO Auto-generated constructor stub
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Set the associated session instance. The container calls this method 
	 * after the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the instance 
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction instance. 
	 * 
	 * @throws EJBException Thrown if method fails due to system-level error.
	 */
	public void setSessionContext(SessionContext newContext)throws EJBException {
		context = newContext;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.transaction.db.TransactionBase#transaction(com.bln.framework.biz.service.IServiceProcessor, com.bln.framework.mo.IMessageObject)
	 */
	protected IMessageObject transaction(IService serviceObj, IMessageObject reqMo) throws TransException {
		
		IMessageObject respMo = null;
		try {
			respMo = super.transaction(serviceObj, reqMo);
		} catch (TransException e) {
			context.setRollbackOnly();
			throw e;
		}
		return respMo;
	}
}
