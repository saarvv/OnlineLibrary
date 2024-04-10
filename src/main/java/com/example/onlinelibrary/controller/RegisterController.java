package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.dto.UserDto;
import com.example.onlinelibrary.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    private IUserService userService;

    public RegisterController(DaoAuthenticationProvider daoAuthenticationProvider, IUserService userService) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticated = daoAuthenticationProvider.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        return "redirect:/dashboard";
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration.html";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDto userDto) {
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        System.out.println(userDto);
        userService.save(userDto);
        return "login.html";
    }
}
