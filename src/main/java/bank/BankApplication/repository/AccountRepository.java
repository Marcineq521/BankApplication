package bank.BankApplication.repository;


import bank.BankApplication.entity.Account;
import bank.BankApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByOwner(User owner);

    boolean existsByAccountNumber(String accountNumber);
}
