package app.config.sec;

import app.jwt.JwtFilter;
import app.service.XUserCreationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecAppSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtFilter jwtFilter;

  public SecAppSecurityConfig(XUserCreationService x, JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
    x.create();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable();

    http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth/**", "/credit/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/auth/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .failureForwardUrl("/auth/login")
            .defaultSuccessUrl("/credit", true).permitAll()
            .and()
            .exceptionHandling()
            .accessDeniedPage("/error");

    http
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    http.headers().frameOptions().disable();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
