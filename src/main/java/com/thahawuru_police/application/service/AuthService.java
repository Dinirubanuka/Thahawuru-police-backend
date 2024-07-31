package com.thahawuru_police.application.service;

import com.thahawuru_police.application.dto.request.LoginRequestDTO;
import com.thahawuru_police.application.dto.response.LoginResponseDTO;
import com.thahawuru_police.application.dto.response.UserResponseDTO;
import com.thahawuru_police.application.entity.Roles;
import com.thahawuru_police.application.exception.UserNotFoundException;
import com.thahawuru_police.application.entity.User;
import com.thahawuru_police.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private JWTService jwtService;


    public LoginResponseDTO login(LoginRequestDTO user){
        User current = userRepository.findUserByEmail(user.getEmail().toLowerCase()).orElseThrow(()-> new UserNotFoundException("User Not Found!"));
        if (!current.getRole().equals(Roles.POLICEOFFICER)) {
            throw new IllegalStateException("THIS ROUTE IS NOT ALLOWED!");
        }
        if(encryptionService.verifyPassword(user.getPassword(),current.getPassword())){
            String token=  jwtService.generateJWT(current);
            return new LoginResponseDTO(new UserResponseDTO(current.getId(),current.getEmail(),current.getRole()),token);
        }else{
            throw new IllegalStateException("Password is incorrect!");
        }

    }

    public LoginResponseDTO loginAdmin(LoginRequestDTO user) {
        User current = userRepository.findUserByEmail(user.getEmail().toLowerCase()).orElseThrow(() -> new UserNotFoundException("User Not Found!"));

        if (!current.getRole().equals(Roles.ADMIN)) {
            throw new IllegalStateException("THIS ROUTE IS NOT ALLOWED!");
        }


        if (encryptionService.verifyPassword(user.getPassword(), current.getPassword())) {
            String token = jwtService.generateJWT(current);
            return new LoginResponseDTO(new UserResponseDTO(current.getId(), current.getEmail(), current.getRole()), token);
        } else {
            throw new IllegalStateException("Password is incorrect!");
        }
    }


        public UserResponseDTO registerAdmin(User user){
        if(userRepository.findUserByEmail(user.getEmail().toLowerCase()).isPresent()){
            throw new IllegalStateException("email already exists!");
        }else{
            User newuser = new User();
            newuser.setEmail(user.getEmail().toLowerCase());
            newuser.setPassword(encryptionService.encryptPassword(user.getPassword()));
            newuser.setRole(Roles.ADMIN);
            User newUser=userRepository.save(newuser);
            return new UserResponseDTO(newUser.getId(),newUser.getEmail(),newUser.getRole());
        }
    }




}