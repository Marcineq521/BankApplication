package bank.BankApplication.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransferRequest {

    @NotBlank
    @Pattern(regexp = "PL\\d{26}")
    private String fromAccountNumber;

    @NotBlank
    @Pattern(regexp = "PL\\d{26}")
    private String toAccountNumber;

    @NotNull
    @Positive
    private BigDecimal amount;

    @Size(max = 500)
    private String description;


}


