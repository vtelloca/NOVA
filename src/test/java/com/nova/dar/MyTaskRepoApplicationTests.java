package com.nova.dar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class MyTaskRepoApplicationTests {
	
	@Autowired
	private MyTaskController con;
	@MockBean
	private MyTaskRepository task_repository;
	
	private MyTask task;
	
	@BeforeEach
	public void setUp() {
		this.task = new MyTask("test", 0);
	}
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void getAllTaskTest() {
		List<MyTask> tasks = new ArrayList<MyTask>();
		tasks.add(this.task);
		assertEquals(tasks, task_repository.findAll());
	}	
	
	@Test
	public void createTaskTest() {
		MyTask task = con.createTask(this.task);
		assertEquals(task, task_repository.save(task));
	}
	

	
	
}
