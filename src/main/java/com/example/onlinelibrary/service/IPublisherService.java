package com.example.onlinelibrary.service;


import com.example.onlinelibrary.dto.PublisherDto;
import javassist.NotFoundException;

import java.util.List;

public interface IPublisherService {

    public PublisherDto save(PublisherDto publisherDto);

    public List<PublisherDto> getAll() throws NotFoundException;

    public List<PublisherDto> findAllByName(String name) throws NotFoundException;
}
