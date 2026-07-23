package bank.BankApplication.service;

import bank.BankApplication.dto.request.LoginRequest;
import bank.BankApplication.dto.response.AuthResponse;
import bank.BankApplication.entity.User;
import bank.BankApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request){
        User user=userRepository.findByEmail(request.getEmail()).
                orElseThrow(()->new IllegalArgumentException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token= jwtService.generateToken(user);

        return new AuthResponse(token,user.getRole());
    }
}
