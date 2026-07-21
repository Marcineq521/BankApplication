package bank.BankApplication.dto.response;

import bank.BankApplication.enums.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String ownerPesel;
    private AccountType accountType;
    private LocalDateTime createdAt;

}
