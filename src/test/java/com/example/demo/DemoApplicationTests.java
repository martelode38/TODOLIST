package com.example.demo;

import com.example.demo.entities.ToDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateTodoSuccess() {
        var todo = new ToDo("todo 1", "desc todo 1", false, 1);

        webTestClient.post()
                .uri("/todos")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo(todo.getName())
                .jsonPath("$[0].description").isEqualTo(todo.getDescription())
                .jsonPath("$[0].completed").isEqualTo(todo.isCompleted())
                .jsonPath("$[0].priority").isEqualTo(todo.getPriority());
    }

    @Test
    void testCreateTodoFailed() {
        var todo = new ToDo("", "", false, 1);

        webTestClient.post()
                .uri("/todos")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testListTodos() {
        webTestClient.get()
                .uri("/todos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void testUpdateTodoSuccess(){
        var todo = new ToDo("todo 1", "desc todo 1", false, 1);

        var response = webTestClient.post()
                .uri("/todos")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ToDo.class).returnResult();

        Long createId = response.getResponseBody().get(0).getId();

        var todoAtualizado = new ToDo("todo atualizado", "desc atualizada", true, 2);

        webTestClient.put()
                .uri("/todos/{id}", createId)
                .bodyValue(todoAtualizado)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo(todoAtualizado.getName())
                .jsonPath("$[0].description").isEqualTo(todoAtualizado.getDescription())
                .jsonPath("$[0].completed").isEqualTo(todoAtualizado.isCompleted())
                .jsonPath("$[0].priority").isEqualTo(todoAtualizado.getPriority());
    }

    @Test
    void testUpdateTodoFailed(){

        var todo = new ToDo("todo 1", "desc todo 1", false, 1);

        var response = webTestClient.post()
                .uri("/todos")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ToDo.class).returnResult();

        Long createId = response.getResponseBody().get(0).getId();

        var todoAtualizado = new ToDo("", "", false, 1);

        webTestClient.put()
                .uri("/todos/{id}", createId)
                .bodyValue(todoAtualizado)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testDeleteTodoSuccess(){
        var todo = new ToDo("nome", "descrição", true, 1);

        var response = webTestClient.post()
                .uri("/todos")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ToDo.class)
                .returnResult();

        Long createId = response.getResponseBody().get(0).getId();

        webTestClient.delete()
                .uri("/todos/{id}", createId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteTodoFailed(){

        var failedIdTest = 9999L;
        webTestClient.delete()
                .uri("/todos/{id}", failedIdTest)
                .exchange()
                .expectStatus().isNotFound();
    }


}


