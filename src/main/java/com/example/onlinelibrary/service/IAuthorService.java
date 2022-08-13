package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.AuthorDto;
import javassist.NotFoundException;

import java.util.List;


public interface IAuthorService {

    public AuthorDto save(AuthorDto authorDto);

    public List<AuthorDto> getAll() throws NotFoundException;

    public List<AuthorDto> findAllByName(String name) throws NotFoundException;

}
