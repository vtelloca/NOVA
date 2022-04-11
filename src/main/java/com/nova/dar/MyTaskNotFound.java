package com.nova.dar;

public class MyTaskNotFound extends RuntimeException {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8563320446876718948L;

	public MyTaskNotFound(Long id) {
		super("Could not find task " + id);
	}
	
}
