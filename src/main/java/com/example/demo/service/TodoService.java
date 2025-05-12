package com.example.demo.service;

import com.example.demo.entities.ToDo;
import com.example.demo.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    public List<ToDo> create(ToDo todo){
        todoRepository.save(todo);
        return list();
    }

    public List<ToDo> list(){
        Sort sort = Sort.by("priority").descending().and(
                Sort.by("name").ascending());
        return todoRepository.findAll(sort);
    }

    public List<ToDo> update(Long id,ToDo todo){

        ToDo existingTodo = todoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("ToDo with ID " + id + " not found.")
        );

        existingTodo.setName(todo.getName());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setCompleted(todo.isCompleted());
        existingTodo.setPriority(todo.getPriority());

        todoRepository.save(existingTodo);

        return list();
    }

    public List<ToDo> delete(Long id){
        ToDo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found"));

        todoRepository.deleteById(id);
        return list();
    }
}
