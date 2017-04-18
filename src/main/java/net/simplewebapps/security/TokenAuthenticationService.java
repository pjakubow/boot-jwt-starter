package net.simplewebapps.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

@Service
public class TokenAuthenticationService {

  static final String TOKEN_HEADER_NAME   = "Authorization";
  static final String TOKEN_HEADER_PREFIX = "Bearer";

  @Value("${jwt.secret:abc}")
  private String secret;

  @Value("${jwt.expiration.time:864000000}")
  private Long expirationTime;

  public void addAuthentication(HttpServletResponse response, String username) {
    String jwt = Jwts.builder()
      .setSubject(username)
      .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
    response.addHeader(TOKEN_HEADER_NAME, TOKEN_HEADER_PREFIX + " " + jwt);
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(TOKEN_HEADER_NAME);
    if (token != null) {
      String username = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token.replace(TOKEN_HEADER_PREFIX, ""))
        .getBody()
        .getSubject();

      return username != null ?
        new UsernamePasswordAuthenticationToken(username, null, emptyList()) :
        null;
    }
    return null;
  }
}
