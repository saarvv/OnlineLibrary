package com.example.onlinelibrary.service;


import com.example.onlinelibrary.model.Publisher;
import javassist.NotFoundException;

import java.util.List;

public interface IPublisherService {

    Publisher save(Publisher publisher);

    List<Publisher> getAll() throws NotFoundException;

    List<Publisher> findAllByName(String name) throws NotFoundException;
}
