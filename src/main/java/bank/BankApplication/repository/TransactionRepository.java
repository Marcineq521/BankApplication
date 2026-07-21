package bank.BankApplication.repository;

import bank.BankApplication.entity.Account;
import bank.BankApplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByAccount(Account account);
}
