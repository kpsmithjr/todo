package org.generation.todo.security;

import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.generation.todo.filter.CustomAuthenticationFilter;
import org.generation.todo.filter.CustomAuthorizationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/v1/appuser").permitAll();
    http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/v1/todo/*").hasAnyAuthority("USER");
    http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/v1/todo/*").hasAnyAuthority("USER");
    http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE, "/api/v1/todo/*").hasAnyAuthority("USER");
    http.authorizeHttpRequests().antMatchers(HttpMethod.PUT, "/api/v1/todo/*").hasAnyAuthority("USER");
    http.authorizeHttpRequests().anyRequest().authenticated();
    // http.authorizeHttpRequests().anyRequest().permitAll();
    http.addFilter(new CustomAuthenticationFilter (authenticationManagerBean()));
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    // http.authorizeHttpRequests().antMatchers("*").permitAll();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
