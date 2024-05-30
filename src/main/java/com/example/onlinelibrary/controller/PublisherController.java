package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Publisher;
import com.example.onlinelibrary.repository.PublisherRepository;
import com.example.onlinelibrary.service.PublisherService;
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
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @ModelAttribute("publisher")
    public Publisher Publisher() {
        return new Publisher();
    }

    @RequestMapping(value = "addPublisher", method = RequestMethod.GET)
    public String addPublisher(Model model) {
        model.addAttribute("publishers", new Publisher());
        return "all";
    }

    @PostMapping("/pubsliher/find/{name}")
    public List<Publisher> findByPublisherName(@PathVariable(name = "publisherName") String name) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            List<Publisher> publishers = publisherService.findAllByName(name);
            return publishers;
        }
        throw new NotFoundException("Can not find your search");
    }
}
