package bank.BankApplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DepositRequest {

    @NotBlank
    @Pattern(regexp = "PL\\d{26}")
    private String accountNumber;

    @NotNull
    @Positive
    private BigDecimal amount;


}
