package org.generation.todo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.generation.todo.entity.AppUser;
import org.generation.todo.entity.Todo;
import org.generation.todo.repository.AppUserRepositoty;
import org.generation.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepository todoRepository;
  private final AppUserRepositoty appUserRepositoty;

  public List<Todo> getAll(String authorizationToken) {
    String username = getUsernameFromJWT(authorizationToken);

    AppUser appUser = appUserRepositoty.findByEmail(username).get();

    if (appUser == null) {
      throw new IllegalStateException("Invalid token");
    }

    Long appUserId = appUser.getId();

    return todoRepository.findAllByAppUserId(appUserId);
  }

  public Todo getById(Long id, String authorizationToken) {
    String username = getUsernameFromJWT(authorizationToken);

    AppUser appUser = appUserRepositoty.findByEmail(username).get();

    if (appUser == null) {
      throw new IllegalStateException("Invalid token");
    }

    Optional<Todo> optionalTodo = todoRepository.findById(id);

    if(optionalTodo.isEmpty()) {
      throw new IllegalStateException("No to do item with id " + id);
    }

    Todo todo = optionalTodo.get();

    if (todo.getAppUserId() != appUser.getId()) {
      throw new IllegalStateException("Not athourized");
    }

    return todo;
  }

  public void add(Todo todo, String authorizationToken) {
    String username = getUsernameFromJWT(authorizationToken);

    AppUser appUser = appUserRepositoty.findByEmail(username).get();

    if (appUser == null) {
      throw new IllegalStateException("Invalid token");
    }

    todo.setAppUserId(appUser.getId());
    
    todoRepository.save(todo);
  }

  public void delete(Long id, String authorizationToken) {
    String username = getUsernameFromJWT(authorizationToken);

    AppUser appUser = appUserRepositoty.findByEmail(username).get();

    if (appUser == null) {
      throw new IllegalStateException("Invalid token");
    }

    Optional<Todo> optionalTodo = todoRepository.findById(id);

    if (optionalTodo.isEmpty()) {
      throw new IllegalStateException("No to do item with id " + id + " exists");
    }

    Todo todo = optionalTodo.get();

    if (todo.getAppUserId() != appUser.getId()) {
      throw new IllegalStateException("Not Authorized");
    }
 
    todoRepository.deleteById(id);
  }

  @Transactional
  public void update(Todo newTodo, String authorizationToken) {
    Todo todo = todoRepository.findById(newTodo.getId()).get();

    if (todo == null ) {
      throw new IllegalStateException("Trying to update To Do item with id " + newTodo.getId() + ", but not such id found");
    }

    String username = getUsernameFromJWT(authorizationToken);

    AppUser appUser = appUserRepositoty.findByEmail(username).get();

    if (appUser == null) {
      throw new IllegalStateException("Invalid token");
    }

    if (todo.getAppUserId() != appUser.getId()) {
      throw new IllegalStateException("Not Authorized");
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

  private String getUsernameFromJWT(String authorizationToken) {
    Algorithm algorithm = Algorithm.HMAC256("IAmBatman!!".getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();  
    String token = authorizationToken.substring("Bearer ".length());
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT.getSubject();
  }
  
}
