package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.BookDto;
import com.example.onlinelibrary.model.Book;
import javassist.NotFoundException;


import java.util.List;


public interface IBookService {


    public BookDto save(BookDto bookDto) throws Exception;

    public Book save(Book book);

    public List<BookDto> getAll() throws NotFoundException;

    public Boolean delete(Long id) throws NotFoundException;

    public List<BookDto> SearchBookByTitle(String title) throws NotFoundException;

    List<Book> findAll();

}
