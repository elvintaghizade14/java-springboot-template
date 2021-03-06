package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TemplateEngineBean {

  @Bean
  public RestTemplate createRestTemplate() {
    return new RestTemplate();
  }
}
