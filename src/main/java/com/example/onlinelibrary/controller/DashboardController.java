package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public DashboardController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "dashboard.html";
    }

    @RequestMapping("/dashboard")
    public ModelAndView dashboard() {
        return new ModelAndView("dashboard");
    }

    @GetMapping("/fav")
    public String favoriteBooks(@RequestParam(value = "id", required = false) Long id, Model model) {
        User userDetail = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.example.onlinelibrary.model.User user = userRepository.findByUsername(userDetail.getUsername());
        List<Book> books = userRepository.findBookByFavorite(userDetail.getUsername());
        if (id != null) {
            Optional<Book> book = bookRepository.findById(id);
            books.add(book.get());
            user.setFavorite(books);
            bookRepository.save(book.get());
        }
        model.addAttribute("books", user.getFavorite());
        return "fav";
    }

    @GetMapping("/lib")
    public String Library(@RequestParam(value = "id", required = false) Long id, Model model) {
        User userDetail = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.example.onlinelibrary.model.User user = userRepository.findByUsername(userDetail.getUsername());
        List<Book> books = userRepository.findBookByUsername(userDetail.getUsername());
        if (id != null) {
            Optional<Book> book = bookRepository.findById(id);
            books.add(book.get());
            user.setBooks(books);
            bookRepository.save(book.get());
        }
        model.addAttribute("books", books);
        return "lib";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

}
