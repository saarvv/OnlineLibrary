package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.repository.AuthorRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class AuthorService implements IAuthorService {
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;

    public AuthorService(ModelMapper modelMapper, AuthorRepository authorRepository) {
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() throws NotFoundException {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        Author[] authorDtos = modelMapper.map(authors, Author[].class);
        return Arrays.asList(authorDtos);
    }

    @Override
    public List<Author> findAllByName(String name) throws NotFoundException {
        List<com.example.onlinelibrary.model.Author> authors = authorRepository.findByNameOrLastName(name, name);
        if (authors.size() < 1) {
            throw new NotFoundException("Author doesn't exist");
        }
        Author[] authorDtos = modelMapper.map(authors, Author[].class);
        return Arrays.asList(authorDtos);
    }

    @Override
    public Author save(Author authorDto) {
        com.example.onlinelibrary.model.Author author = modelMapper.map(authorDto, com.example.onlinelibrary.model.Author.class);
        authorRepository.save(author);
        authorDto.setId(author.getId());
        return authorDto;
    }
}
