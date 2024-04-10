package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    @Query("select a from Author a where a.name like %:name% or a.lastName like %:lastName%")
    List<Author> findByNameOrLastName(String name, String lastName);
}
