package com.example.onlinelibrary.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "publisher")
public class Publisher{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;


}
