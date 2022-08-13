package com.example.onlinelibrary.service;


import com.example.onlinelibrary.dto.UserDto;
import com.example.onlinelibrary.model.User;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    UserDto findByUsername(String username) throws NotFoundException;

    User save(UserDto userDto);

    UserDto findByUsernameOrPassword(String username, String password) throws NotFoundException;


}
