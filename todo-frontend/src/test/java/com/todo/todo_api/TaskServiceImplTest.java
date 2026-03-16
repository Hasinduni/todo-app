package com.todo.todo_api;

import com.todo.todo_api.model.Task;
import com.todo.todo_api.repository.TaskRepository;
import com.todo.todo_api.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

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
    void getRecentTasks_ShouldReturnListOfTasks() {
        when(taskRepository.findTop5ByCompletedFalseOrderByCreatedAtDesc())
                .thenReturn(List.of(task));

        List<Task> result = taskService.getRecentTasks();

        assertEquals(1, result.size());
        assertEquals("Buy books", result.get(0).getTitle());
        verify(taskRepository, times(1))
                .findTop5ByCompletedFalseOrderByCreatedAtDesc();
    }

    @Test
    void getRecentTasks_ShouldReturnEmptyList_WhenNoTasks() {
        when(taskRepository.findTop5ByCompletedFalseOrderByCreatedAtDesc())
                .thenReturn(List.of());

        List<Task> result = taskService.getRecentTasks();

        assertTrue(result.isEmpty());
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertNotNull(result);
        assertEquals("Buy books", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void completeTask_ShouldMarkTaskAsCompleted() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.completeTask(1L);

        assertTrue(result.isCompleted());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void completeTask_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.completeTask(99L));

        assertEquals("Task not found with id: 99", exception.getMessage());
    }
}