package com.nova.dar;

import org.springframework.data.jpa.repository.JpaRepository;

//Mytask (tipo)
//Long (id)
public interface MyTaskRepository extends JpaRepository<MyTask, Long> {

}
