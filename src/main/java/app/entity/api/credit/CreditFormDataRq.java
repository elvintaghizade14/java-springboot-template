package app.entity.api.credit;

import lombok.Data;

@Data
public class CreditFormDataRq {
  private double amount;
  private int term;
}
