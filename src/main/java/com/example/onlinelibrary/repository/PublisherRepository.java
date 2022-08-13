package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends PagingAndSortingRepository<Publisher, Long> {

    Publisher findAllByName(String name);


    @Query("select a from Publisher a where a.name like %:name% or a.lastName like %:lastName%")
    List<Publisher> findByNameOrLastName(String name, String lastName);
}
