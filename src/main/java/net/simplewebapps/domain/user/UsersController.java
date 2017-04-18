package net.simplewebapps.domain.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class UsersController {

  @RequestMapping("/users")
  public @ResponseBody List<UserDto> users() {
    return Arrays.asList(user(1L, "First User"), user(2L, "Second User"));
  }

  private UserDto user(long id, String name) {
    UserDto user = new UserDto();
    user.setId(id);
    user.setName(name);
    return user;
  }
}
