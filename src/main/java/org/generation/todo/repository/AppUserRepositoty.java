package org.generation.todo.repository;

import java.util.Optional;

import org.generation.todo.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepositoty extends JpaRepository<AppUser, Long>{
  boolean existsByEmail(String email);
  Optional<AppUser> findByEmail(String email);
}
