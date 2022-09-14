package org.generation.todo.entity;

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
public class AppUser {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)  
  private String email;
  @Column(nullable = false)
  private String password;
}
