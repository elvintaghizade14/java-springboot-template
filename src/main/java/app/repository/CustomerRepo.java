package app.repository;

import app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

  @Query(value = "select *\n" +
          "from customer c\n" +
          "         inner join r_customer_credit_hist rcch on c.id = rcch.customer_id\n" +
          "         inner join credit_history ch on rcch.credit_id = ch.id\n" +
          "where c.approx_monthly_payment > ?\n" +
          " or c.approx_monthly_payment = ?\n" +
          "order by ch.delay_days;", nativeQuery = true)
  List<Customer> findAppropriateCustomer(double monthlyPayment);
}
