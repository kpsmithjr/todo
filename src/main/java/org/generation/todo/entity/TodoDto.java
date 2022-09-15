package org.generation.todo.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
  private Long id;
  private String description;
  private LocalDate targetDate;

  public TodoDto(String description, LocalDate targetDate) {
    this.description = description;
    this.targetDate = targetDate;
  }
}
