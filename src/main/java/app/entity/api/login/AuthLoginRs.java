package app.entity.api.login;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class AuthLoginRs {
  int code;
  String token;
  String message;

  public static AuthLoginRs Ok(String token) {
    return new AuthLoginRs(0, token, "");
  }

  public static AuthLoginRs Err() {
    return new AuthLoginRs(-1, "", "username + password combination is wrong");
  }

}
