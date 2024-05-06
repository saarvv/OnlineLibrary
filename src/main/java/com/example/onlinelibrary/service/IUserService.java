package com.example.onlinelibrary.service;


import com.example.onlinelibrary.model.User;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User findByUsername(String username) throws NotFoundException;

    com.example.onlinelibrary.model.User save(User userDto);

    User findByUsernameOrPassword(String username, String password) throws NotFoundException;
}
