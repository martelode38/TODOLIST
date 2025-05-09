package com.example.demo.repository;

import com.example.demo.entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<ToDo, Long> {

}
