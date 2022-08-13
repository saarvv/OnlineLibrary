package com.example.onlinelibrary.service;


import com.example.onlinelibrary.dto.PublisherDto;
import com.example.onlinelibrary.model.Publisher;
import com.example.onlinelibrary.repository.PublisherRepository;
import com.example.onlinelibrary.repository.UserRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PublisherService implements IPublisherService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Autowired
    private final PublisherRepository publisherRepository;

    public PublisherService(ModelMapper modelMapper, UserRepository userRepository, PublisherRepository publisherRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public PublisherDto save(PublisherDto publisherDto) {
        Publisher publisher = modelMapper.map(publisherDto, Publisher.class);
        publisherRepository.save(publisher);
        publisherDto.setId(publisher.getId());
        return publisherDto;
    }

    @Override
    public List<PublisherDto> getAll() throws NotFoundException {
        List<Publisher> publishers = (List<Publisher>) publisherRepository.findAll();
        PublisherDto[] publisherDtos = modelMapper.map(publishers, PublisherDto[].class);
        return Arrays.asList(publisherDtos);
    }
    @Override
    public List<PublisherDto> findAllByName(String name) throws NotFoundException {
        List<Publisher> publishers = publisherRepository.findByNameOrLastName(name, name);
        PublisherDto[] publisherDtos = modelMapper.map(publishers, PublisherDto[].class);
        return Arrays.asList(publisherDtos);
    }
}
