package app.controller;


import app.entity.api.credit.CreditFormDataRq;
import app.service.CreditService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/credit")
public class CreditController {
  private final CreditService CREDIT_SERVICE;

  public CreditController(CreditService CREDIT_SERVICE) {
    this.CREDIT_SERVICE = CREDIT_SERVICE;
  }

  @GetMapping
  public String handle_credit_get(Authentication auth) {
    return auth.isAuthenticated() ? "credit" : "error";
  }

  @PostMapping
  public void handle_credit_post(CreditFormDataRq data) {
    double amount = data.getAmount();
    int term = data.getTerm();
    double monthlyPayment = amount / term;
    boolean result = CREDIT_SERVICE.findCustomers(monthlyPayment);
    log.info("Manager says: " + (result ? "okay" : "no"));
  }
}