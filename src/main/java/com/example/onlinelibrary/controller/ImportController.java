package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.messages.ResponseMessage;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImportController {
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;


    @PostMapping(value = "/excelUpload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestBody MultipartFile file) {
        String message = "";
        try {
            bookService.save(file);
            message = "Uploaded Successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}

