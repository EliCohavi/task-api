package com.elicohavi.task_api;

import org.springframework.data.jpa.repository.JpaRepository;

//Integer, the type of my @Id field
public interface TaskRepository extends JpaRepository<Task, Integer> {}
