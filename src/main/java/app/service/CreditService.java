package app.service;

import app.entity.Customer;
import app.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditService {
  private final CustomerRepo CUSTOMER_REPO;

  public CreditService(CustomerRepo CUSTOMER_REPO) {
    this.CUSTOMER_REPO = CUSTOMER_REPO;
  }

  public boolean findCustomers(double monthlyPayment) {
    List<Customer> result = CUSTOMER_REPO.findAppropriateCustomer(monthlyPayment);
    return sendToManager(result);
  }

  public boolean sendToManager(List<Customer> customers) {
    return customers.size() > 0;
  }
}
