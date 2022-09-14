package org.generation.todo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.generation.todo.entity.Todo;
import org.generation.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepository todoRepository;

  public List<Todo> getAll() {
    return todoRepository.findAll();
  }

  public Todo getById(Long id) {
    Optional<Todo> todo = todoRepository.findById(id);

    if(!todo.isEmpty()) {
      throw new IllegalStateException("No to do item with id " + id);
    }

    return todo.get();
  }

  public void add(Todo todo) {
    todoRepository.save(todo);
  }

  public void delete(Long id) {
    boolean idExists = todoRepository.existsById(id);

    if (!idExists) {
      throw new IllegalStateException("No To Do item with id " + id + " exists");
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
