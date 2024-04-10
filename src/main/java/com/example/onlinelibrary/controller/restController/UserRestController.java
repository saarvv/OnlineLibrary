package com.example.onlinelibrary.controller.restController;


import com.example.onlinelibrary.dto.UserDto;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping("/findUser/{username}")
    public ResponseEntity<UserDto> findBydUserName(@PathVariable(name = "username", required = true) String username)
            throws NotFoundException {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() throws NotFoundException {
        List<UserDto> userDtos = userService.getAll();
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }
}

