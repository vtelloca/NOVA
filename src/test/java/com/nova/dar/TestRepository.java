package com.nova.dar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TestRepository {

	@Autowired
	private MyTaskRepository task_repository;

	private MyTask task;
	private MyTask task2;
	private MyTask task3;
	private final long ID = 1;

	@BeforeEach
	public void setUp() {
		this.task = new MyTask(ID, "Task 1", 0);
		this.task2 = new MyTask(ID + 1, "Task 2", 2);
		this.task3 = new MyTask(ID + 2, "Task 3", 1);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void getAllTaskTest() {
		List<MyTask> tasks = new ArrayList<MyTask>();
		List<MyTask> finalTasks = new ArrayList<MyTask>();
		tasks.add(this.task);
		tasks.add(this.task2);
		tasks.add(this.task3);

		finalTasks = task_repository.findAll();

		for (int i = 0; i < finalTasks.size(); i++) {

//			System.out.println('a' + tasks.get(i).get_description() + tasks.get(i).get_id() + tasks.get(i).get_state());
//			System.out.println('b' + finalTasks.get(i).get_description() + finalTasks.get(i).get_id() + finalTasks.get(i).get_state());

			assertThat(tasks.get(i).get_state()).isEqualTo(finalTasks.get(i).get_state());
			assertThat(tasks.get(i).get_description()).isEqualTo(finalTasks.get(i).get_description());
			assertThat(tasks.get(i).get_id()).isEqualTo(finalTasks.get(i).get_id());
		}
	}

	@Test
	public void completedTask() {
		List<MyTask> completedTasks = new ArrayList<MyTask>();
		completedTasks = task_repository.findByState(2);
		for (MyTask tc : completedTasks) {
			assertThat(tc.get_state()).isEqualTo(2);
		}
	}

	@Test
	public void pendingTask() {
		List<MyTask> pendingTask = new ArrayList<MyTask>();
		pendingTask = task_repository.findByState(1);
		for (MyTask tc : pendingTask) {
			assertThat(tc.get_state()).isEqualTo(1);
		}
	}

	@Test
	public void getTaskByIdTest() {

		MyTask testtask = task_repository.findById(ID).orElseThrow(() -> new MyTaskNotFound(ID));

		assertEquals(this.task.get_description(), testtask.get_description());
		assertThat(this.task.get_state()).isEqualTo(testtask.get_state());
		assertThat(testtask.get_id()).isEqualTo(ID);
	}

	@Test
	public void createTaskTest() {
		MyTask testtask = new MyTask(ID + 3, "Task 3", 0);
		MyTask newTask = task_repository.save(testtask);

		assertEquals(newTask.get_description(), testtask.get_description());
		assertThat(testtask.get_state()).isEqualTo(newTask.get_state());
		assertThat(testtask.get_id()).isEqualTo(newTask.get_id());

	}

	@Test
	public void updateTaskTest() {
		String sum = "New";
		int state = 1; // In progress

		this.task.set_state(state);
		this.task.set_description(sum);

		MyTask testtask = task_repository.save(this.task);

		assertEquals(state, testtask.get_state());
		assertThat(testtask.get_state()).isEqualTo(this.task.get_state());
		assertThat(testtask.get_id()).isEqualTo(ID);

	}

	@Test
	public void deleteTaskByIdTest() {
		this.task_repository.deleteById(ID);
		assertThat(this.task_repository.existsById(ID)).isEqualTo(false);
	}

	@Test
	public void deleteAllTest() {
		this.task_repository.deleteAll();
		assertThat(this.task_repository.findAll()).isEmpty();
	}

}