package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query("select a from File a where a.id = :fff")
    Optional<File> findById(@Param(value = "fff") Long id);
}
