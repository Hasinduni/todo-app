package com.todo.todo_api;

import com.todo.todo_api.controller.TaskController;
import com.todo.todo_api.model.Task;
import com.todo.todo_api.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Buy books");
        task.setDescription("Buy for school");
        task.setCompleted(false);
    }

    @Test
    void getTasks_ShouldReturnListOfTasks() {
        when(taskService.getRecentTasks()).thenReturn(List.of(task));

        var response = taskController.getTasks();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Buy books", response.getBody().get(0).getTitle());
    }

    @Test
    void getTasks_ShouldReturnEmptyList() {
        when(taskService.getRecentTasks()).thenReturn(List.of());

        var response = taskController.getTasks();

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void createTask_ShouldReturn201WithCreatedTask() {
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        var response = taskController.createTask(task);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Buy books", response.getBody().getTitle());
    }

    @Test
    void completeTask_ShouldReturn200WithCompletedTask() {
        task.setCompleted(true);
        when(taskService.completeTask(1L)).thenReturn(task);

        var response = taskController.completeTask(1L);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isCompleted());
    }

    @Test
    void completeTask_ShouldThrowException_WhenTaskNotFound() {
        when(taskService.completeTask(99L))
                .thenThrow(new RuntimeException("Task not found with id: 99"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskController.completeTask(99L));

        assertEquals("Task not found with id: 99", exception.getMessage());
    }
}