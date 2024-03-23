package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.dto.BookDto;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.File;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.repository.FileRepository;
import com.example.onlinelibrary.service.IBookService;
import com.example.onlinelibrary.service.IFileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    private IBookService bookService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private FileRepository fileRepository;

    public BookController(BookRepository bookRepository, IBookService bookService, IFileService fileService, FileRepository fileRepository) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @ModelAttribute("book")
    public BookDto bookDto() {
        return new BookDto();
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addBooks(Model model) {
        model.addAttribute("books", new Book());
        return "all";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String saveBook(@Valid Book book, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        return "all";
    }

    @GetMapping("all")
    public String allBooks(BookDto bookDto, Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "all";
    }

    @SneakyThrows
    @PostMapping("/books/save")
    public String library(@ModelAttribute("book") BookDto bookDto) {
        System.out.println(bookDto);
        bookService.save(bookDto);
        return "redirect:/all";
    }

    @SneakyThrows
    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile multipartFile, @RequestParam Long id) {
        Optional<Book> book = bookRepository.findById(id);
        File file = new File();
        file.setData(multipartFile.getBytes());
        file.setContent(multipartFile.getContentType());
        file.setName(multipartFile.getName());
        fileService.save(file);
        book.get().setFile(file);
        bookService.save(book.get());
        return "redirect:/all";
    }

    @GetMapping("/books/download")
    public void downloadFile(@RequestParam("id") Long id, Model model, HttpServletResponse response) throws IOException {
        Optional<Book> book = bookRepository.findById(id);
        Optional<File> temp = fileRepository.findById(book.get().getFile().getId());
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
