package bank.BankApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,length = 50)
    private String surname;

    @Column(unique = true, nullable = false,length = 11)
    private String pesel;

    @Column(unique = true, nullable = false,length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts=new ArrayList<>();

    @PrePersist
    public void prePersist(){
        createdAt=LocalDateTime.now();
    }
}
