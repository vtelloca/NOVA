package com.nova.dar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MyTask {
	
	//Autopopulate id
	private @Id @GeneratedValue Long _id;
	private String _summary;
	private int _state;
	
	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String get_summary() {
		return _summary;
	}

	public void set_summary(String _summary) {
		this._summary = _summary;
	}

	public int get_state() {
		return _state;
	}

	public void set_state(int _state) {
		this._state = _state;
	}
	
	/**
	 * @param _id
	 * @param _summary
	 * @param _state
	 */
	public MyTask(String _summary, int _state) {
		super();
		this._summary = _summary;
		this._state = _state;
	}

}
