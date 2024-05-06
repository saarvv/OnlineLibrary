package com.example.onlinelibrary.controller.restController;


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
    public User userDto() {
        return new User();
    }

    @GetMapping("/findUser/{username}")
    public ResponseEntity<User> findBydUserName(@PathVariable(name = "username", required = true) String username)
            throws NotFoundException {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() throws NotFoundException {
        List<User> userDtos = userService.getAll();
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping
    public ResponseEntity<com.example.onlinelibrary.model.User> createUser(@Valid @RequestBody User userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }
}

