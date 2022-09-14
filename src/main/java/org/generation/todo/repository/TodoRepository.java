package org.generation.todo.repository;

import java.util.List;
import java.util.Optional;

import org.generation.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
  List<Todo> findAllByAppUserId(Long appUserId);
}
