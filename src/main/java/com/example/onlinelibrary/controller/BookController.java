package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.File;
import com.example.onlinelibrary.model.Publisher;
import com.example.onlinelibrary.repository.*;
import com.example.onlinelibrary.service.IBookService;
import com.example.onlinelibrary.service.IFileService;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    private IBookService bookService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    public BookController(BookRepository bookRepository, IBookService bookService, IFileService fileService, FileRepository fileRepository) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @ModelAttribute("book")
    public Book Book() {
        return new Book();
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addBooks(Model model) {
        model.addAttribute("books", new Book());
        return "all";
    }

    @GetMapping("all")
    public String allBooks(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            List<Book> books = userRepository.findBookByUsername(((User) authentication.getPrincipal()).getUsername());
            model.addAttribute("books", books);
        }
        return "all";
    }

    @PostMapping("/books/find/{title}")
    public Book findByTitle(@PathVariable(name = "title") String title) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            Book books = bookService.searchBookByTitle(title);
            return books;
        }
        throw new NotFoundException("Can not find your search");
    }

    @SneakyThrows
    @PostMapping("/books/save")
    public String library(@ModelAttribute("book") Book book1, Author author, Publisher publisher) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            com.example.onlinelibrary.model.User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
            Book book = bookService.save(book1);
            user.getBooks().add(book);
            author.setName(book.getAuthorName());
            publisher.setName(book.getPublisherName());
            authorRepository.save(author);
            userRepository.save(user);
        }
        return "redirect:/all";
    }

    @SneakyThrows
    @Transactional
    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            Book book = bookRepository.findById(id).get();
            if (null != book) {
                com.example.onlinelibrary.model.User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
                user.getBooks().remove(book);
                user.getFavorite().remove(book);
                userRepository.save(user);
            }
        }
        return "redirect:/all";
    }

    @SneakyThrows
    @Transactional
    @PostMapping("/books/remove/favorite/{id}")
    public String deleteFromFavorite(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            Book book = bookRepository.findById(id).get();
            if (null != book) {
                com.example.onlinelibrary.model.User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
                user.getFavorite().remove(book);
                userRepository.save(user);
            }
        }
        return "redirect:/fav";
    }

    @SneakyThrows
    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile multipartFile, @RequestParam Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            Book book = bookRepository.findById(id).get();
            File file = new File();
            file.setData(multipartFile.getBytes());
            file.setContent(multipartFile.getContentType());
            file.setName(multipartFile.getName());
            fileService.save(file);
            book.setFile(file);
            bookService.save(book);
        }
        return "redirect:/all";
    }

    @GetMapping("/books/download")
    public void downloadFile(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        Optional<Book> book = bookRepository.findById(id);
        Optional<File> temp = fileRepository.findById(book.get().getFile().getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.getPrincipal() instanceof User) {
            if (temp.isPresent()) {
                File file = temp.get();
                response.setContentType("application/octet-stream");
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename = " + file.getId();
                response.setHeader(headerKey, headerValue);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(file.getData());
                outputStream.close();
            }
        }
    }
}
