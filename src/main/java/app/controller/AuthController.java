package app.controller;

import app.entity.api.login.AuthLoginRq;
import app.entity.api.login.AuthLoginRs;
import app.entity.api.register.AuthRegRq;
import app.entity.api.register.AuthRegRs;
import app.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/login")
  public String handle_login_get() {
    log.info("GET -> /auth/login");
    return "login";
  }

  @PostMapping("/login")
  public String handle_login_post(@RequestBody AuthLoginRq rq) {
    Optional<String> login = authService.login(rq.getUsername(), rq.getPassword(), false);
    log.info("login is : " + login.get());
    return login.isPresent() ? "redirect:credit" : "login";
//    return authService.login(rq.getUsername(), rq.getPassword(), false)
//            .map(AuthLoginRs::Ok)
//            .orElse(AuthLoginRs.Err());
  }

  @PostMapping("/register")
  public AuthRegRs handle_register(@RequestBody AuthRegRq rq) {
    return authService.register(rq.getUsername(), rq.getPassword())
            ? AuthRegRs.Ok()
            : AuthRegRs.Err(String.format("%s user already exists", rq.getUsername()));
  }
}
