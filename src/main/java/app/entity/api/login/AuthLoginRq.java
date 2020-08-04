package app.entity.api.login;

import lombok.Data;

@Data
public class AuthLoginRq {
  private String username;
  private String password;
  private Boolean rememberMe;
}