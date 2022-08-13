package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.AuthorDto;
import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.repository.AuthorRepository;
import com.example.onlinelibrary.repository.UserRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class AuthorService implements IAuthorService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public AuthorService(ModelMapper modelMapper, UserRepository userRepository, AuthorRepository authorRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> getAll() throws NotFoundException {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        AuthorDto[] authorDtos = modelMapper.map(authors, AuthorDto[].class);
        return Arrays.asList(authorDtos);
    }

    @Override
    public List<AuthorDto> findAllByName(String name) throws NotFoundException {
        List<Author> authors = authorRepository.findByNameOrLastName(name, name);
        if (authors.size() < 1) {
            throw new NotFoundException("Author doesn't exist");
        }
        AuthorDto[] authorDtos = modelMapper.map(authors, AuthorDto[].class);
        return Arrays.asList(authorDtos);
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto, Author.class);
        authorRepository.save(author);
        authorDto.setId(author.getId());
        return authorDto;
    }


}
