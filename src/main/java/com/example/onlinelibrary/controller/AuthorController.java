package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.service.AuthorService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @ModelAttribute("author")
    public Author Author() {
        return new Author();
    }

    @RequestMapping(value = "addAuthor", method = RequestMethod.GET)
    public String addAuthor(Model model) {
        model.addAttribute("authors", new Author());
        return "all";
    }

    @PostMapping("/author/find/{AuthorName}")
    public List<Author> findByAuthorName(@RequestParam String name) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            List<Author> authors = authorService.findAllByName(name);
            return authors;
        }
        throw new NotFoundException("Can not find your search");
    }

}
