package app.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
@PropertySource("classpath:jwt.properties")
public class JwtService {

  @Value("${jwt.secret}")
  private String secret; // echo 'super secret 2020' | base64

  @Value("${jwt.expiry.normal}")
  private long expiry_normal;   // 1 d

  @Value("${jwt.expiry.remember}")
  private long expiry_remember; // 10 d

  public String generateToken(Integer user_id, boolean rememberMe) {
    final Date now = new Date();
    final long delta = rememberMe ? expiry_remember : expiry_normal;
    final Date expiration = new Date(now.getTime() + delta);
    return Jwts.builder()
        .setSubject(user_id.toString())
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Optional<Jws<Claims>> tokenToClaims(String token) {
    try {
      return Optional.of(Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token));
    } catch (SignatureException ex) {
      log.error("JWT: Invalid signature");
    } catch (MalformedJwtException ex) {
      log.error("JWT: Invalid token");
    } catch (ExpiredJwtException ex) {
      log.error("JWT: Expired token");
    } catch (UnsupportedJwtException ex) {
      log.error("JWT: Unsupported token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT: token is empty.");
    }

    return Optional.empty();
  }

  public int claimsToUserId(Jws<Claims> claims) {
    return Integer.parseInt(claims.getBody().getSubject());
  }

}
