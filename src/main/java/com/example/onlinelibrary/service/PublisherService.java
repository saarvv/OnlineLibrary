package com.example.onlinelibrary.service;


import com.example.onlinelibrary.model.Publisher;
import com.example.onlinelibrary.repository.PublisherRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PublisherService implements IPublisherService {

    private final ModelMapper modelMapper;
    @Autowired
    private final PublisherRepository publisherRepository;

    public PublisherService(ModelMapper modelMapper, PublisherRepository publisherRepository) {
        this.modelMapper = modelMapper;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher save(Publisher publisherDto) {
        Publisher publisher = modelMapper.map(publisherDto, Publisher.class);
        publisherRepository.save(publisher);
        publisherDto.setId(publisher.getId());
        return publisherDto;
    }

    @Override
    public List<Publisher> getAll() throws NotFoundException {
        List<Publisher> publishers = (List<Publisher>) publisherRepository.findAll();
        Publisher[] publisherDtos = modelMapper.map(publishers, Publisher[].class);
        return Arrays.asList(publisherDtos);
    }

    @Override
    public List<Publisher> findAllByName(String name) throws NotFoundException {
        List<Publisher> publishers = publisherRepository.findByNameOrLastName(name, name);
        Publisher[] publisherDtos = modelMapper.map(publishers, Publisher[].class);
        return Arrays.asList(publisherDtos);
    }
}
