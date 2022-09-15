package org.generation.todo.controller;

import java.util.List;

import org.generation.todo.entity.Todo;
import org.generation.todo.entity.TodoDto;
import org.generation.todo.service.TodoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {
  private final TodoService todoService;

  @GetMapping("/all")
  public List<Todo> getAll(@RequestHeader("Authorization") String authorizationToken) {
    return todoService.getAll(authorizationToken);
  }

  @GetMapping("/{id}")
  public Todo getById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationToken) {
    return todoService.getById(id, authorizationToken);
  }

  @PostMapping
  public void add(@RequestBody TodoDto todoDto, @RequestHeader("Authorization") String authorizationToken) {
    Todo todo = new Todo();
    todo.setDescription(todoDto.getDescription());
    todo.setTargetDate(todoDto.getTargetDate());

    todoService.add(todo, authorizationToken);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationToken) {
    todoService.delete(id, authorizationToken);
  }

  @PutMapping
  public void update(@RequestBody TodoDto todoDto, @RequestHeader("Authorization") String authorizationToken) {
    Todo todo = new Todo();
    todo.setId(todoDto.getId());
    todo.setDescription(todoDto.getDescription());
    todo.setTargetDate(todoDto.getTargetDate());

    todoService.update(todo, authorizationToken);
  }
}
