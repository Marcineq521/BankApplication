package bank.BankApplication.service;

import bank.BankApplication.dto.request.CreateAccountRequest;
import bank.BankApplication.dto.request.DepositRequest;
import bank.BankApplication.dto.request.TransferRequest;
import bank.BankApplication.dto.request.WithdrawRequest;
import bank.BankApplication.dto.response.AccountResponse;
import bank.BankApplication.dto.response.TransactionResponse;
import bank.BankApplication.entity.Account;
import bank.BankApplication.entity.Transaction;
import bank.BankApplication.entity.User;
import bank.BankApplication.enums.TransactionType;
import bank.BankApplication.repository.AccountRepository;
import bank.BankApplication.repository.TransactionRepository;
import bank.BankApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountResponse createAccount(CreateAccountRequest request){
        User user=userRepository.findByPesel(request.getPesel()).orElseThrow(
                ()->new IllegalArgumentException("There is no user with that PESEL"));


        Account account=new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setOwner(user);

        Account savedAccount=accountRepository.save(account);
        return mapToResponse(savedAccount);

    }

    public List<AccountResponse> getAccountsByPesel(String pesel){
        User owner=userRepository.findByPesel(pesel).orElseThrow(
                ()->new IllegalStateException("There is no user with that PESEL"));

        List<Account> accounts=owner.getAccounts();
        return accounts.stream().map(this::mapToResponse).toList();


    }

    @Transactional
    public AccountResponse deposit(DepositRequest request){
        Account account=accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("There is no account with that number"));


        account.setBalance(account.getBalance().add(request.getAmount()));
        Transaction transaction=new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setDescription("Cash deposit");
        account.getTransactions().add(transaction);

        Account savedAfterDeposit=accountRepository.save(account);
        transactionRepository.save(transaction);

        return mapToResponse(savedAfterDeposit);
    }


    @Transactional
    public AccountResponse withdraw(WithdrawRequest request){
        Account account=accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("There is no account with that number"));


        if(account.getBalance().compareTo(request.getAmount())<0){
            throw new IllegalArgumentException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        Transaction transaction=new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setDescription("Cash withdrawal");
        account.getTransactions().add(transaction);

        Account savedAfterWithdraw=accountRepository.save(account);
        transactionRepository.save(transaction);

        return mapToResponse(savedAfterWithdraw);
    }
    @Transactional
    public AccountResponse transfer(TransferRequest request){
        if(request.getFromAccountNumber().equals(request.getToAccountNumber())){
            throw new IllegalArgumentException("Cannot transfer money to the same account");
        }

        Account fromAccount=accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("Source account does not exist"));
        Account toAccount=accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("Destination account does not exist"));

        if(fromAccount.getBalance().compareTo(request.getAmount())<0){
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        String description = request.getDescription();

        if (description == null || description.isBlank()) {
            description = "Bank transfer";
        }



        Transaction fromAccountTransaction=new Transaction();
        fromAccountTransaction.setTransactionType(TransactionType.TRANSFER_OUT);
        fromAccountTransaction.setAccount(fromAccount);
        fromAccountTransaction.setAmount(request.getAmount());
        fromAccountTransaction.setDescription(description);
        fromAccountTransaction.setCounterpartyAccountNumber(toAccount.getAccountNumber());
        fromAccount.getTransactions().add(fromAccountTransaction);


        Transaction toAccountTransaction=new Transaction();
        toAccountTransaction.setTransactionType(TransactionType.TRANSFER_IN);
        toAccountTransaction.setAccount(toAccount);
        toAccountTransaction.setAmount(request.getAmount());
        toAccountTransaction.setDescription(description);
        toAccountTransaction.setCounterpartyAccountNumber(fromAccount.getAccountNumber());
        toAccount.getTransactions().add(toAccountTransaction);

        transactionRepository.save(fromAccountTransaction);
        transactionRepository.save(toAccountTransaction);

        Account savedFromAccount=accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return mapToResponse(savedFromAccount);


    }

    private AccountResponse mapToResponse(Account account){
        AccountResponse response=new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());
        response.setOwnerPesel(account.getOwner().getPesel());
        response.setAccountType(account.getAccountType());
        response.setCreatedAt(account.getCreatedAt());

        return response;
    }

    private String generateAccountNumber(){
        String accountNumber;

        do{
            accountNumber="PL"+generateRandomDigits(26);
        } while(accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private String generateRandomDigits(int length){

        Random random=new Random();
        StringBuilder builder=new StringBuilder();

        for(int i=0; i<length; i++){
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
