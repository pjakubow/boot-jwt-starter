package net.simplewebapps.domain.user;

public class UserDto {

  private Long id;
  private String name;

  public Long getId() {
    return id;
  }

  public UserDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UserDto setName(String name) {
    this.name = name;
    return this;
  }
}
