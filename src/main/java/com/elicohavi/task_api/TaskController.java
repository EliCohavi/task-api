package com.elicohavi.task_api;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository repository;

    //Constructor based seeding of initial tasks
    public TaskController() {}

    //Use @PostConstruct to run after dependency injection
    @PostConstruct
    public void init() {
        if (repository.count() == 0) { // Only seed if no tasks are in DB
            repository.save(new Task("Dishes", "Unload Dishwasher and load sink dishes.", false));
            repository.save(new Task("Laundry", "Switch loads and hang up clean clothes.", false));
            repository.save(new Task("Gas", "Fill up gas tank and handheld can.", false));
            repository.save(new Task("Doctor", "Schedule Doctor appointment", false));
        }
    }

        @GetMapping("/tasks") // Return all tasks
    public List<Task> getTasks() {
        return repository.findAll();
    }

    @PostMapping("/tasks") // Add Tasks to Repo
    public Task createTask(@RequestBody Task task) {
        return repository.save(task);
    }

    @GetMapping("/tasks/{id}") // Return task by ID
    public Task getTask(@PathVariable int id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    @PutMapping("/tasks/{id}") //Update Individual Task
    public Task updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

                task.setTitle(updatedTask.getTitle());
                task.setDescription(updatedTask.getDescription());
                task.setCompleted(updatedTask.getCompleted());

                return repository.save(task);
    }

    @PatchMapping("/tasks/{id}") //Partially Update Individual Task
    public Task patchTask(@PathVariable int id, @RequestBody Task partialUpdate) {

        Task task = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

                if (partialUpdate.getTitle() != null) {
                    task.setTitle(partialUpdate.getTitle());
                }
                if (partialUpdate.getDescription() != null) {
                    task.setDescription(partialUpdate.getDescription());
                }
                if (partialUpdate.getCompleted()!= null) {

                    task.setCompleted(partialUpdate.getCompleted());
                }

                return repository.save(task);
    }

    @DeleteMapping("/tasks/{id}") // Delete Task by ID
    public void deleteTask(@PathVariable int id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        repository.delete(task);
    }

}
