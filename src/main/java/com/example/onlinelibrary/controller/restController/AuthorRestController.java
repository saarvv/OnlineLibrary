package com.example.onlinelibrary.controller.restController;

import com.example.onlinelibrary.dto.AuthorDto;
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

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAll() throws NotFoundException {
        List<AuthorDto> authorDtos = authorService.getAll();
        return ResponseEntity.ok(authorDtos);
    }

    @GetMapping("/find")
    public ResponseEntity<List<AuthorDto>> findAllByName(@RequestParam String name) throws NotFoundException {
        List<AuthorDto> authorDtos = authorService.findAllByName(name);
        return ResponseEntity.ok(authorDtos);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        return ResponseEntity.ok(authorService.save(authorDto));
    }
}
