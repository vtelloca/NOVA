package com.nova.dar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MyTask {
	
	//Autopopulate id
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(columnDefinition = "varchar(256)")
	private String description;

	private int state;
	
	public Long get_id() {
		return id;
	}

	public void set_id(Long id) {
		this.id = id;
	}

	public String get_description() {
		return description;
	}

	public void set_description(String description) {
		this.description = description;
	}

	public int get_state() {
		return state;
	}

	public void set_state(int state) {
		this.state = state;
	}
	
	public MyTask() {}
	
	/**
	 * @param description
	 * 
	 */
	public MyTask(String description) {
		super();
		this.description = description;
		this.state = 0;
	}
	
	/**
	 * @param description
	 * @param state
	 */
	public MyTask(String description, int state) {
		super();
		this.description = description;
		this.state = state;
	}
	
	/**
	 * @param id
	 * @param description
	 * @param state
	 */
	public MyTask(Long id, String description, int state) {
		super();
		this.id = id;
		this.description = description;
		this.state = state;
	}
	/*
	 * 0 new
	 * 1 in progress
	 * 2 Completed
	 * 
	 * */

}
