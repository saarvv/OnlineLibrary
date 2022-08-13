package com.example.onlinelibrary.repository;


import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {


    @Query("select u from User u where u.username =:username ")
    User findByUsername(@Param(value = "username") String username);

    @Query("select u.books from User u where u.username =:username")
    ArrayList<Book> findBookByUsername(@Param(value = "username") String username);

    @Query("select u.favorite from User u where u.username =:username")
    ArrayList<Book> findBookByFavorite(@Param(value = "username") String username);

    @Query("select u from User u where u.username like %:username% or u.password like %:password%")
    List<User> findByUsernameOrPassword(String username, String password);

}
