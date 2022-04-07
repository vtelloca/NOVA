package com.nova.dar;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyTaskController {
	
	@Autowired
	private final MyTaskRepository task_repository;

	MyTaskController(MyTaskRepository task_repository) {
		this.task_repository = task_repository;

	}

	@GetMapping("/tasks")
	List<MyTask> allTask() {
		return task_repository.findAll();
	}
	
	@PostMapping("/tasks")
	public MyTask createTask(@RequestBody final MyTask task) {
		return task_repository.save(task);
	}

	@PutMapping("/tasks")
	public MyTask updateTask(@RequestBody final MyTask task) {
		return task_repository.save(task);
	}
	
	@DeleteMapping("/tasks")
	public void deleteAll() {
		task_repository.deleteAll();
	}
		
	@GetMapping("/completedTask")
	List<MyTask> completedTask() {
		return task_repository.findByState(2);
	}
	
	@GetMapping("/pendingTask")
	List<MyTask> pendingTask() {
		return task_repository.findByState(1);
	}

	@GetMapping("/getTaskById")
	public MyTask getTaskById(@RequestParam final Long id) {
		return task_repository.findById(id).orElseThrow(() -> new MyTaskNotFound(id));
	}	

	@DeleteMapping("/deleteTaskById")
	public void deleteTaskById(@RequestParam final Long id) {
		task_repository.deleteById(id);
	}

}
