package com.hardik.SpringSecurity.SecurityApplication.services;

import com.hardik.SpringSecurity.SecurityApplication.dto.LoginDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.SignUpDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.UserDTO;
import com.hardik.SpringSecurity.SecurityApplication.entities.User;
import com.hardik.SpringSecurity.SecurityApplication.exceptions.ResourceNotFoundException;
import com.hardik.SpringSecurity.SecurityApplication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;






    @Autowired
    ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User of "+username+" not found " ));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<User> user=userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already "+ signUpDTO.getEmail() +"exist");
        }

        User toCreate=mapper.map(signUpDTO,User.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        User savedUser=userRepository.save(toCreate);

        return mapper.map(savedUser,UserDTO.class);

    }


}
