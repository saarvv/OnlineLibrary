package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.BookDto;
import com.example.onlinelibrary.model.Book;
import javassist.NotFoundException;

import java.util.List;


public interface IBookService {

    BookDto save(BookDto bookDto) throws Exception;

    Book save(Book book);

    List<BookDto> getAll() throws NotFoundException;

    Boolean delete(Long id) throws NotFoundException;

    List<BookDto> searchBookByTitle(String title) throws NotFoundException;

    List<BookDto> findByAuthorName(String name) throws NotFoundException;

    List<Book> findAll();
}
