package org.generation.todo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.generation.todo.entity.Todo;
import org.generation.todo.repository.AppUserRepositoty;
import org.generation.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepository todoRepository;
  private final AppUserRepositoty appUserRepositoty;

  public List<Todo> getAll(Long appUserId) {
    boolean userExits = appUserRepositoty.existsById(appUserId);

    if (!userExits) {
      throw new IllegalStateException("Invalid appUserId " + appUserId);
    }

    return todoRepository.findAllByAppUserId(appUserId);
  }

  public Todo getById(Long id, Long appUserId) {
    boolean userExits = appUserRepositoty.existsById(appUserId);

    if (!userExits) {
      throw new IllegalStateException("Invalid appUserId " + appUserId);
    }

    Optional<Todo> optionalTodo = todoRepository.findById(id);

    if(optionalTodo.isEmpty()) {
      throw new IllegalStateException("No to do item with id " + id);
    }

    Todo todo = optionalTodo.get();

    if (todo.getAppUserId() != appUserId) {
      throw new IllegalStateException("Not athourized");
    }

    return todo;
  }

  public void add(Todo todo) {
    boolean userExits = appUserRepositoty.existsById(todo.getAppUserId());

    if (!userExits) {
      throw new IllegalStateException("Invalid appUserId " + todo.getAppUserId());
    }

    todoRepository.save(todo);
  }

  public void delete(Long id) {
    boolean idExists = todoRepository.existsById(id);

    if (!idExists) {
      throw new IllegalStateException("No do fo item with id " + id + " exists");
    }
 
    todoRepository.deleteById(id);
  }

  @Transactional
  public void update(Todo newTodo) {
    Todo todo = todoRepository.findById(newTodo.getId()).get();

    if (todo == null ) {
      throw new IllegalStateException("Trying to update To Do item with id " + newTodo.getId() + ", but not such id found");
    }

    if (newTodo.getDescription() != null &&
        newTodo.getDescription().length() > 0 && 
        !newTodo.getDescription().equals(todo.getDescription())) {
      todo.setDescription(newTodo.getDescription());
    }

    if (!newTodo.getTargetDate().isBefore(LocalDate.now()) &&
      !newTodo.getTargetDate().equals(todo.getTargetDate())) {
      todo.setTargetDate(newTodo.getTargetDate());
    }
  }
}
