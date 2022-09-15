package org.generation.todo.service;

import java.util.List;

// import java.util.List;
// import java.util.Optional;

// import javax.transaction.Transactional;

import org.generation.todo.entity.AppUser;
import org.generation.todo.entity.AppUserRole;
import org.generation.todo.repository.AppUserRepositoty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
  private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

  private final AppUserRepositoty appUserRepositoty;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return appUserRepositoty.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
  }

  public List<AppUser> getAll() {
    return appUserRepositoty.findAll();
  }

  // public AppUser getById(Long id) {
  //   Optional<AppUser> appUser =  appUserRepositoty.findById(id);

  //   if (!appUser.isEmpty()){
  //     throw new IllegalStateException("No app use with id " + id);
  //   }

  //   return appUser.get();
  // }

  public void add(AppUser appUser) {
    // Make sure email is unique
    boolean emailExists = appUserRepositoty.existsByEmail(appUser.getEmail());

    if (emailExists) {
      throw new IllegalStateException("email " + appUser.getEmail() + " is already taken");
    }

      // Encode password and save to user
      String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
      appUser.setPassword(encodedPassword);
  
      // Update role
      appUser.setRole(AppUserRole.USER);

    appUserRepositoty.save(appUser);
  }

  // public void delete(Long id) {
  //   boolean idExists = appUserRepositoty.existsById(id);

  //   if (!idExists) {
  //     throw new IllegalStateException("Trying to delete app user with id " + id + ", but no such id found");
  //   }
  //   appUserRepositoty.deleteById(id);
  // }

  // @Transactional
  // public void update(AppUser newAppUser) {
  //   AppUser appUser = appUserRepositoty.findById(newAppUser.getId()).get();

  //   if (appUser == null) {
  //     throw new IllegalStateException("Trying to update app use with id " + ", but no such id found");
  //   }

  //   if (newAppUser.getEmail() != null &&
  //     newAppUser.getEmail().length() > 0 &&
  //     !newAppUser.getEmail().equals(appUser.getEmail())) {
      
  //     boolean emailExists = appUserRepositoty.existsByEmail(newAppUser.getEmail());

  //     if (emailExists) {
  //       throw new IllegalStateException("email " + appUser.getEmail() + " is already taken");
  //     }

  //     appUser.setEmail(newAppUser.getEmail());
  //   }

  //   if (newAppUser.getPassword() != null &&
  //       newAppUser.getPassword().length() > 0 && 
  //       !newAppUser.getPassword().equals(appUser.getPassword())) {
  //     appUser.setPassword(newAppUser.getPassword());
  //   }
  // }
}
