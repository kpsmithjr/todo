package org.generation.todo.controller;

import java.util.List;

import org.generation.todo.entity.AppUser;
import org.generation.todo.service.AppUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appuser")
public class AppUserController {
  private final AppUserService appUserService;

  @GetMapping("/all")
  public List<AppUser> getAll() {
    return appUserService.getAll();
  }
  
  @PostMapping
  public void add(@RequestBody AppUser appUser) {
    appUserService.add(appUser);
  }
  
}
