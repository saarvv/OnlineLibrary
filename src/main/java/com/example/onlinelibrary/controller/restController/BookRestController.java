package com.example.onlinelibrary.controller.restController;


import com.example.onlinelibrary.model.Book;
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
    public ResponseEntity<List<Book>> getAll() throws NotFoundException {
        return ResponseEntity.ok(bookService.getAll());
    }

    @PostMapping("/find/{title}")
    public ResponseEntity<Book> findByTitle(@PathVariable(name = "title", required = true) String name)
            throws NotFoundException {
        return ResponseEntity.ok(bookService.searchBookByTitle(name));
    }

    @PostMapping("/find/{authorName}")
    public ResponseEntity<List<Book>> findByAuthorName(@PathVariable(name = "authorName", required = true) String name)
            throws NotFoundException {
        return ResponseEntity.ok(bookService.findByAuthorName(name));
    }
}
