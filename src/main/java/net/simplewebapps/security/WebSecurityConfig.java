package net.simplewebapps.security;

import net.simplewebapps.security.JwtAuthenticationFilter;
import net.simplewebapps.security.JwtLoginFilter;
import net.simplewebapps.security.TokenAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean("tokenAuthenticationService")
  TokenAuthenticationService tokenAuthenticationService() {
    return new TokenAuthenticationService();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    final TokenAuthenticationService tokenAuthenticationService = tokenAuthenticationService();
    http
      .csrf().disable()
      .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .anyRequest().authenticated()
      .and()
        // We filter the api/login requests
        .addFilterBefore(new JwtLoginFilter("/login", authenticationManager(), tokenAuthenticationService),
          UsernamePasswordAuthenticationFilter.class)
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JwtAuthenticationFilter(tokenAuthenticationService),
          UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // Create a default account
    auth.inMemoryAuthentication()
      .withUser("admin")
        .password("password")
        .roles("ADMIN");
  }
}