package bank.BankApplication.entity;

import bank.BankApplication.enums.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 28)
    private String accountNumber;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal balance=BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions=new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
    }
}
