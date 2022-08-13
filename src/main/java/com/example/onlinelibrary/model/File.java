package com.example.onlinelibrary.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private Long id;
    private String name;

    private String content;
    private String type;

    private String url;
    @Lob
    private byte[] data;

    public File(String name, String content, String type) {
        this.name = name;
        this.content = content;
        this.type = type;
    }

    public File() {

    }
}
