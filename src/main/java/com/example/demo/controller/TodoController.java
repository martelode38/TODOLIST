package com.example.demo.controller;

import com.example.demo.entities.ToDo;
import com.example.demo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping
    List <ToDo> create(@RequestBody @Valid ToDo todo){
        return todoService.create(todo);
    }
    @GetMapping
    List <ToDo> list(){
        return todoService.list();
    }
    @PutMapping
    List <ToDo> update(@RequestBody ToDo todo){
        return todoService.update(todo);
    }
    @DeleteMapping("{id}")
    List <ToDo> delete(@PathVariable Long id){
        return  todoService.delete(id);
    }
}
