package app.entity.api.register;

import lombok.Data;

@Data
public class AuthRegRq {
  private String username;
  private String password;
}
