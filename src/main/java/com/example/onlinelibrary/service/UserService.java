package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.UserDto;
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

    public List<UserDto> getAll() throws NotFoundException {
        List<User> users = (List<User>) userRepository.findAll();
        if (users.size() < 1) {
            throw new NotFoundException("No user exist");
        }
        UserDto[] userDtos = modelMapper.map(users, UserDto[].class);
        return Arrays.asList(userDtos);
    }

    @Override
    public UserDto findByUsername(String username) throws NotFoundException {
        try {
            User user = (User) userRepository.findByUsername(username);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        } catch (Exception e) {
            throw new NotFoundException("User Doesn't exist with this name called: " + username);
        }
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setNationalCode(userDto.getNationalCode());
        return userRepository.save(user);
    }

    @Override
    public UserDto findByUsernameOrPassword(String username, String password) throws NotFoundException {
        try {
            User user = (User) userRepository.findByUsernameOrPassword(username, password);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        } catch (Exception e) {
            throw new NotFoundException("User Doesn't Exist" + username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return Arrays.asList(new SimpleGrantedAuthority("user"));
    }
}
