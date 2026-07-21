package bank.BankApplication.dto.request;

import bank.BankApplication.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAccountRequest {

    @NotNull
    private AccountType accountType;

    @NotBlank
    @Pattern(regexp = "\\d{11}")
    private String pesel;

}
