package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;
import javassist.NotFoundException;

import java.util.List;


public interface IBookService {

    Book save(Book Book) throws Exception;

    List<Book> getAll() throws NotFoundException;

    Boolean delete(Long id) throws NotFoundException;

    Book searchBookByTitle(String title) throws NotFoundException;

    List<Book> findByAuthorName(String name) throws NotFoundException;

    List<Book> findAll();
}
