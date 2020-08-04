package app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "credit_history")
public class CreditHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private double amount;
  private LocalDate dueDate;
  private LocalDate paidDate;
  private int delayDays;

  @ManyToOne
  @JoinTable(name = "r_customer_credit_hist",
          joinColumns = { @JoinColumn(
                  name = "credit_id",
                  referencedColumnName = "id"
          ) },
          inverseJoinColumns = { @JoinColumn(
                  name = "customer_id",
                  referencedColumnName = "id"
          ) }
  )
  private Customer customer;
}