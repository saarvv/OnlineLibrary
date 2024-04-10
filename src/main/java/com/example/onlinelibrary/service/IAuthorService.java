package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.AuthorDto;
import javassist.NotFoundException;

import java.util.List;


public interface IAuthorService {

    AuthorDto save(AuthorDto authorDto);

    List<AuthorDto> getAll() throws NotFoundException;

    List<AuthorDto> findAllByName(String name) throws NotFoundException;
}
