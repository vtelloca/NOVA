package com.nova.dar;

public class MyTaskNotFound extends RuntimeException {
 
	public MyTaskNotFound(Long id) {
		super("Could not find task " + id);
	}
	
}
