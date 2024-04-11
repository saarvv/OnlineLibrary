package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.BookDto;
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
    public BookDto save(BookDto bookDto) throws Exception {
        Book book = modelMapper.map(bookDto, Book.class);

        bookDto.setId(book.getId());
        bookDto.setAuthorName(book.getAuthorName());
        bookDto.setPublisherName(book.getPublisherName());
        bookRepository.save(book);
        return bookDto;
    }

    @Override
    public List<BookDto> getAll() throws NotFoundException {

        List<Book> books = (List<Book>) bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (books.size() < 1) {
            throw new NotFoundException("Book doesn't exist");
        }
        BookDto[] bookDtos = modelMapper.map(books, BookDto[].class);
        Arrays.asList(bookDtos).forEach(data -> {
            data.setAuthorId(data.getAuthorId());
        });
        return Arrays.asList(bookDtos);
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
    public List<BookDto> searchBookByTitle(String title) throws NotFoundException {
        List<Book> books = bookRepository.searchBooksByTitle(title.trim());
        if (books.size() < 1) {
            throw new NotFoundException("Book doesn't exist");
        }
        BookDto[] bookDtos = modelMapper.map(books, BookDto[].class);
        return Arrays.asList(bookDtos);
    }

    @Override
    public List<BookDto> findByAuthorName(String name) throws NotFoundException {
        List<Book> books = bookRepository.findByAuthorName(name);
        BookDto[] bookDtos = modelMapper.map(books, BookDto[].class);
        return Arrays.asList(bookDtos);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
