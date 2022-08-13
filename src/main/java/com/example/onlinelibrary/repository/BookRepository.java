package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    List<Book> findByAuthorName(String authorName);

    List<Book> findAll();

    @Query("select b from Book b where b.id = :bbb")
    Optional<Book> findById(@Param(value = "bbb") Long id);


    @Query("select b from Book b where b.title like %:title%")
    List<Book> SearchBooksByTitle(String title);
}
