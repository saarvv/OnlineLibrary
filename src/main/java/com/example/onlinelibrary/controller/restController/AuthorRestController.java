package com.example.onlinelibrary.controller.restController;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.service.AuthorService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("author")
    public Author author() {
        return new Author();
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() throws NotFoundException {
        List<Author> authors = authorService.getAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Author>> findAllByName(@RequestParam String name) throws NotFoundException {
        List<Author> authors = authorService.findAllByName(name);
        return ResponseEntity.ok(authors);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        return ResponseEntity.ok(authorService.save(author));
    }
}
