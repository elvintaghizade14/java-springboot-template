package app.service;

import app.entity.db.XUser;
import app.entity.sec.XUserDetails;
import app.jwt.Const;
import app.jwt.JwtService;
import app.repository.XUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AuthService {

  private final AuthenticationManager am;
  private final JwtService jwtService;
  private final XUserRepo repo;
  private final PasswordEncoder enc;

  public AuthService(AuthenticationManager am, JwtService jwtService, XUserRepo repo, PasswordEncoder enc) {
    this.am = am;
    this.jwtService = jwtService;
    this.repo = repo;
    this.enc = enc;
  }

  public boolean register(String username, String password) {
    Optional<XUser> found = repo.findByUsername(username);
    if (found.isPresent()) return false;
    repo.save(new XUser(username, enc.encode(password), "USER"));
    return true;
  }

  public Optional<String> login(String username, String password, Boolean rememberMe) {
    Authentication auth = am.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    return Optional.of(auth)
        .filter(Authentication::isAuthenticated)
        .map(a -> (XUserDetails) a.getPrincipal())
        .map(XUserDetails::getId)
        .map(id -> jwtService.generateToken(id, rememberMe))
        .map(t -> Const.PREFIX + t);
  }
}