package com.example.onlinelibrary.service;

import com.example.onlinelibrary.excel.ExcelFile;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.repository.AuthorRepository;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.repository.UserRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class BookService implements IBookService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    private final BookRepository bookRepository;

    public BookService(ModelMapper modelMapper, UserRepository userRepository, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    public void save(MultipartFile file) {
        try {
            List<Book> books = ExcelFile.bookToExcels(file.getInputStream());
            bookRepository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Override
    public Book save(Book Book) throws Exception {
        Book book = modelMapper.map(Book, Book.class);

        Book.setId(book.getId());
        Book.setAuthorName(book.getAuthorName());
        Book.setPublisherName(book.getPublisherName());
        bookRepository.save(book);
        return Book;
    }

    @Override
    public List<Book> getAll() throws NotFoundException {

        List<Book> books = (List<Book>) bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (books.size() < 1) {
            throw new NotFoundException("Book doesn't exist");
        }
        Book[] Books = modelMapper.map(books, Book[].class);
        Arrays.asList(Books).forEach(data -> {
            data.setAuthor(data.getAuthor());
        });
        return Arrays.asList(Books);
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            throw new NotFoundException("Book doesn't exist with this id: " + id);
        }
        bookRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Book> searchBookByTitle(String title) throws NotFoundException {
        List<Book> books = bookRepository.searchBooksByTitle(title.trim());
        if (books.size() < 1) {
            throw new NotFoundException("Book doesn't exist");
        }
        Book[] Books = modelMapper.map(books, Book[].class);
        return Arrays.asList(Books);
    }

    @Override
    public List<Book> findByAuthorName(String name) throws NotFoundException {
        List<Book> books = bookRepository.findByAuthorName(name);
        Book[] Books = modelMapper.map(books, Book[].class);
        return Arrays.asList(Books);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
