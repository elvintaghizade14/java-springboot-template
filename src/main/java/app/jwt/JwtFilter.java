package app.jwt;

import app.config.sec.UserDetailsServiceJPA;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsServiceJPA uds;

  public JwtFilter(JwtService jwtService, UserDetailsServiceJPA uds) {
    this.jwtService = jwtService;
    this.uds = uds;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    try {
      extractToken(request)
          .flatMap(jwtService::tokenToClaims)
          .map(jwtService::claimsToUserId)
          .map(uds::loadUserById)
          .map(ud -> new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities()))
          .ifPresent(auth -> {
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
          });

      // anyway, I need to allow the filter chain,
      // but without authentication set it will be stop by Spring Security
      chain.doFilter(request, response);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(Const.HEADER))
        .filter(h -> h.startsWith(Const.PREFIX))
        .map(h -> h.substring(Const.PREFIX.length()));
  }

}
