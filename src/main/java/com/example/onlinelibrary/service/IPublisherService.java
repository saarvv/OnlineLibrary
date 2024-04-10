package com.example.onlinelibrary.service;


import com.example.onlinelibrary.dto.PublisherDto;
import javassist.NotFoundException;

import java.util.List;

public interface IPublisherService {

    PublisherDto save(PublisherDto publisherDto);

    List<PublisherDto> getAll() throws NotFoundException;

    List<PublisherDto> findAllByName(String name) throws NotFoundException;
}
