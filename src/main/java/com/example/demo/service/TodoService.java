package com.example.demo.service;

import com.example.demo.entities.ToDo;
import com.example.demo.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public List<ToDo> update(ToDo todo){
        todoRepository.save(todo);
        return list();
    }

    public List<ToDo> delete(Long id){
        todoRepository.deleteById(id);
        return list();
    }
}
