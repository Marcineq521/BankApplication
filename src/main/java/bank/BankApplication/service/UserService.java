package bank.BankApplication.service;

import bank.BankApplication.dto.request.CreateUserRequest;
import bank.BankApplication.dto.response.UserResponse;
import bank.BankApplication.entity.User;
import bank.BankApplication.enums.Role;
import bank.BankApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request){

        if(userRepository.existsByPesel(request.getPesel())){
            throw new IllegalArgumentException("User with this PESEL already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("User with this e-mail already exists");
        }

        User user=new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPesel(request.getPesel());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User savedUser=userRepository.save(user);

        return mapToResponse(savedUser);

    }

    public List<UserResponse> getAllUsers(){
       return userRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public UserResponse getUserByPesel(String pesel){
        return mapToResponse(findUserByPesel(pesel));
    }

    public void deleteUser(String pesel){
        User user=findUserByPesel(pesel);

        if(!user.getAccounts().isEmpty()){
            throw new IllegalStateException("Cannot delete user with existing accounts");
        }
        userRepository.delete(user);
    }

    private User findUserByPesel(String pesel){
        return userRepository.findByPesel(pesel).orElseThrow(()->new IllegalArgumentException("User with that PESEL doesn't exist"));
    }


    private UserResponse mapToResponse(User user){
        UserResponse response=new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setSurname(user.getSurname());
        response.setPesel(user.getPesel());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }
}
