package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Role;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.repository.UserRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(ModelMapper modelMapper, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAll() throws NotFoundException {
        List<com.example.onlinelibrary.model.User> users = (List<com.example.onlinelibrary.model.User>) userRepository.findAll();
        if (users.size() < 1) {
            throw new NotFoundException("No user exist");
        }
        User[] userDtos = modelMapper.map(users, User[].class);
        return Arrays.asList(userDtos);
    }

    @Override
    public User findByUsername(String username) throws NotFoundException {
        try {
            com.example.onlinelibrary.model.User user = (com.example.onlinelibrary.model.User) userRepository.findByUsername(username);
            User userDto = modelMapper.map(user, User.class);
            return userDto;
        } catch (Exception e) {
            throw new NotFoundException("User Doesn't exist with this name called: " + username);
        }
    }

    @Override
    public com.example.onlinelibrary.model.User save(User userDto) {
        com.example.onlinelibrary.model.User user = new com.example.onlinelibrary.model.User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setNationalCode(userDto.getNationalCode());
        return userRepository.save(user);
    }

    @Override
    public User findByUsernameOrPassword(String username, String password) throws NotFoundException {
        try {
            com.example.onlinelibrary.model.User user = (com.example.onlinelibrary.model.User) userRepository.findByUsernameOrPassword(username, password);
            User userDto = modelMapper.map(user, User.class);
            return userDto;
        } catch (Exception e) {
            throw new NotFoundException("User Doesn't Exist" + username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.onlinelibrary.model.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return Arrays.asList(new SimpleGrantedAuthority("user"));
    }
}
