package net.simplewebapps.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

  private TokenAuthenticationService tokenAuthenticationService;

  public JwtLoginFilter(String pattern, AuthenticationManager authenticationManager, @Autowired TokenAuthenticationService tokenAuthenticationService) {
    super(new AntPathRequestMatcher(pattern));
    setAuthenticationManager(authenticationManager);
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    AccountCredentials creds = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
    return getAuthenticationManager().authenticate(
      new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), emptyList())
    );
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    tokenAuthenticationService.addAuthentication(response, authResult.getName());
  }
}
