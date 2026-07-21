package bank.BankApplication.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String name;

    private String surname;

    private String pesel;

    private String email;

    private LocalDateTime createdAt;


}
