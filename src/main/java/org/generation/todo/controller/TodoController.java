package org.generation.todo.controller;

import java.util.List;

import org.generation.todo.entity.Todo;
import org.generation.todo.service.TodoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {
  private final TodoService todoService;

  @GetMapping("/all")
  public List<Todo> getAll() {
    return todoService.getAll();
  }

  @GetMapping("/{id}")
  public Todo getById(@PathVariable Long id) {
    return todoService.getById(id);
  }

  @PostMapping
  public void add(@RequestBody Todo todo) {
    todoService.add(todo);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    todoService.delete(id);
  }

  @PutMapping
  public void update(@RequestBody Todo todo) {
    todoService.update(todo);
  }
}
