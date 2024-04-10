package com.example.onlinelibrary.controller.restController;


import com.example.onlinelibrary.dto.BookDto;
import com.example.onlinelibrary.service.BookService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() throws NotFoundException {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/find/{title}")
    public ResponseEntity<List<BookDto>> findByTitle(@PathVariable(name = "title", required = true) String name)
            throws NotFoundException {
        return ResponseEntity.ok(bookService.searchBookByTitle(name));
    }

    @GetMapping("/find/{authorName}")
    public ResponseEntity<List<BookDto>> findByAuthorName(@PathVariable(name = "authorName", required = true) String name)
            throws NotFoundException {
        return ResponseEntity.ok(bookService.findByAuthorName(name));
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) throws Exception {
        return ResponseEntity.ok(bookService.save(bookDto));
    }
}
