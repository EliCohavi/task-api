package com.elicohavi.task_api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
public class TaskController {

    private ArrayList<Task> tasks = new ArrayList<>();

    private AtomicInteger idCounter = new AtomicInteger(1);

    public TaskController() {
        Task task1 = new Task(1, "Dishes",
                "Unload Dishwasher and load sink dishes.", false);
        tasks.add(task1);
        Task task2 = new Task(2, "Laundry",
                "Switch loads and hang up clean clothes.", false);
        tasks.add(task2);
        Task task3 = new Task(3, "Gas",
                "Fill up gas tank and handheld can.", false);
        tasks.add(task3);
        Task task4 = new Task(4, "Doctor",
                "Schedule Doctor appointment", false);
        tasks.add(task4);
    }
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        task.setId(idCounter.getAndIncrement());
        tasks.add(task);
        return task;
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable int id) {

        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

}
