package com.example.onlinelibrary.dto;

import com.example.onlinelibrary.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookDto {

    private Long id;

    private String title;

    public String authorName;

    public Long authorId;

    private String publisherName;

    private List<Book> books;

    public void addBook(Book book) {
        this.books.add(book);
    }
}
