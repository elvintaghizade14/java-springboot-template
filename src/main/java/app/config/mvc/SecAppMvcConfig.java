package app.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class SecAppMvcConfig implements WebMvcConfigurer {

  private final static String[][] mappings = {
          {"/auth/login", "login"},
          {"/credit", "credit"},
          {"/error", "error"}
  };

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    Arrays.stream(mappings).forEach(m ->
            registry.addViewController(m[0])
                    .setViewName(m[1])
    );
  }
}
