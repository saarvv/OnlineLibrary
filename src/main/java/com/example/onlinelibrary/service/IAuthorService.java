package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Author;
import javassist.NotFoundException;

import java.util.List;


public interface IAuthorService {

    Author save(Author author);

    List<Author> getAll() throws NotFoundException;

    List<Author> findAllByName(String name) throws NotFoundException;
}
