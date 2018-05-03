package com.env.pro.exception;

/**
 * Created by tim on 07/07/2017.
 */
public class ServiceException extends RuntimeException {
	/**  */
	private static final long serialVersionUID = 8964789935379338173L;
	private int errcode;

    public ServiceException() {
        super();
    }

    public ServiceException(int errcode, String message) {
    	super(message);
    	this.errcode = errcode;
    }
    

	public int getErrcode() {
		return errcode;
	}

	
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
}