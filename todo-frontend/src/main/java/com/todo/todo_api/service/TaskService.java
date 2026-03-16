package com.todo.todo_api.service;

import com.todo.todo_api.model.Task;
import java.util.List;

public interface TaskService {

    List<Task> getRecentTasks();

    Task createTask(Task task);

    Task completeTask(Long id);
}