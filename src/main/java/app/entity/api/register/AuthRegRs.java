package app.entity.api.register;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class AuthRegRs {
  int code;
  String message;

  public static AuthRegRs Ok() {
    return new AuthRegRs(0, "");
  }

  public static AuthRegRs Err(String message) {
    return new AuthRegRs(-1, message);
  }
}
