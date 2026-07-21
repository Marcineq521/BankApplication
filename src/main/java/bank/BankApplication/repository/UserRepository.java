package bank.BankApplication.repository;


import bank.BankApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPesel(String pesel);

    boolean existsByEmail(String email);

    Optional<User> findByPesel(String pesel);

    Optional<User> findByEmail(String email);
}
