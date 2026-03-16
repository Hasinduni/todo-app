package com.todo.todo_api;

import com.todo.todo_api.model.Task;
import com.todo.todo_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TaskRepository taskRepository;

    private RestClient restClient;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/tasks";
    }

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        restClient = RestClient.create();
    }

    @Test
    void createTask_ShouldSaveToDatabase() {
        Task task = new Task();
        task.setTitle("Buy books");
        task.setDescription("Buy for school");

        ResponseEntity<Task> response = restClient.post()
                .uri(baseUrl())
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(task)
                .retrieve()
                .toEntity(Task.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Buy books", response.getBody().getTitle());
        assertFalse(response.getBody().isCompleted());
    }

    @Test
    void getTasks_ShouldReturnSavedTasks() {
        Task task = new Task();
        task.setTitle("Clean home");
        task.setDescription("Clean the bedroom");

        restClient.post()
                .uri(baseUrl())
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(task)
                .retrieve()
                .toEntity(Task.class);

        ResponseEntity<Task[]> response = restClient.get()
                .uri(baseUrl())
                .retrieve()
                .toEntity(Task[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Clean home", response.getBody()[0].getTitle());
    }

    @Test
    void getTasks_ShouldReturnOnly5MostRecentTasks() {
        for (int i = 1; i <= 6; i++) {
            Task task = new Task();
            task.setTitle("Task " + i);
            task.setDescription("Description " + i);
            restClient.post()
                    .uri(baseUrl())
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(task)
                    .retrieve()
                    .toEntity(Task.class);
        }

        ResponseEntity<Task[]> response = restClient.get()
                .uri(baseUrl())
                .retrieve()
                .toEntity(Task[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().length);
    }

    @Test
    void completeTask_ShouldMarkTaskAsDone() {
        Task task = new Task();
        task.setTitle("Play cricket");
        task.setDescription("Sunday match");

        ResponseEntity<Task> created = restClient.post()
                .uri(baseUrl())
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(task)
                .retrieve()
                .toEntity(Task.class);

        Long id = created.getBody().getId();

        restClient.patch()
                .uri(baseUrl() + "/" + id + "/complete")
                .retrieve()
                .toBodilessEntity();

        ResponseEntity<Task[]> response = restClient.get()
                .uri(baseUrl())
                .retrieve()
                .toEntity(Task[].class);

        assertEquals(0, response.getBody().length);
    }

    @Test
    void createTask_ShouldFail_WhenTitleIsEmpty() {
        Task task = new Task();
        task.setTitle("");
        task.setDescription("Some description");

        try {
            restClient.post()
                    .uri(baseUrl())
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(task)
                    .retrieve()
                    .toEntity(Task.class);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("400") ||
                    e.getMessage().contains("Bad Request"));
        }
    }
}