package com.example.onlinelibrary.model;


import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "publisher_name")
    private String publisherName;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file", referencedColumnName = "id")
    private File file;


}
