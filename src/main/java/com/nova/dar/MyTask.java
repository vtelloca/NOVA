package com.nova.dar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MyTask {
	
	//Autopopulate id
	private @Id @GeneratedValue Long id;
	private String summary;
	private int state;
	
	public Long get_id() {
		return id;
	}

	public void set_id(Long id) {
		this.id = id;
	}

	public String get_summary() {
		return summary;
	}

	public void set_summary(String summary) {
		this.summary = summary;
	}

	public int get_state() {
		return state;
	}

	public void set_state(int state) {
		this.state = state;
	}
	
	public MyTask() {}
	
	/**
	 * @param summary
	 * 
	 */
	public MyTask(String summary) {
		super();
		this.summary = summary;
		this.state = 0;
	}
	
	/**
	 * @param summary
	 * @param state
	 */
	public MyTask(String summary, int state) {
		super();
		this.summary = summary;
		this.state = state;
	}
	/*
	 * 0 new
	 * 1 in progress
	 * 2 Completed
	 * 
	 * */

}
