package org.generation.todo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false, updatable = false)
  private Long appUserId;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private LocalDate targetDate;
}
