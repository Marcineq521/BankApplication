package bank.BankApplication.service;

import bank.BankApplication.dto.response.AccountResponse;
import bank.BankApplication.dto.response.TransactionResponse;
import bank.BankApplication.entity.Account;
import bank.BankApplication.entity.Transaction;
import bank.BankApplication.repository.AccountRepository;
import bank.BankApplication.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public List<TransactionResponse> getTransactionHistory(String accountNumber){
        Account account=accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()->new IllegalArgumentException("There is no account with that number"));


        return account.getTransactions().stream().map(this::mapToResponse).toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction){
        TransactionResponse response=new TransactionResponse();

        response.setId(transaction.getId());
        response.setTransactionType(transaction.getTransactionType());
        response.setAmount(transaction.getAmount());
        response.setDescription(transaction.getDescription());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setAccountNumber(transaction.getAccount().getAccountNumber());
        response.setCounterpartyAccountNumber(transaction.getCounterpartyAccountNumber());

        return response;
    }
}


