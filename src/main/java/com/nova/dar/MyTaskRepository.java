package com.nova.dar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//Mytask (tipo)
//Long (id)
public interface MyTaskRepository extends JpaRepository<MyTask, Long> {
	List<MyTask> findByState(int state);
}
