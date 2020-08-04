package app;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class CreditFinderApp {
  public static void main(String[] args) {
    log.info(":: SpringBoot Application starting...");
    SpringApplication.run(CreditFinderApp.class, args);
    log.info(":: SpringBoot Application ...started");
  }
}