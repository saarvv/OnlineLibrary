package com.example.onlinelibrary.controller.restController;

import com.example.onlinelibrary.dto.PublisherDto;
import com.example.onlinelibrary.service.PublisherService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/publisher")
public class PublisherRestController {

    @Autowired
    private final PublisherService publisherService;


    public PublisherRestController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<PublisherDto>> getAll() throws NotFoundException {
        List<PublisherDto> publisherDtos = publisherService.getAll();
        return ResponseEntity.ok(publisherDtos);
    }

    @GetMapping("/find")
    public ResponseEntity<List<PublisherDto>> findAllByName(@RequestParam String name) throws NotFoundException {
        List<PublisherDto> publisherDtos = publisherService.findAllByName(name);
        return ResponseEntity.ok(publisherDtos);
    }

    @PostMapping("/save")
    public ResponseEntity<PublisherDto> createPublisher(@Valid @RequestBody PublisherDto publisherDto) {
        return ResponseEntity.ok(publisherService.save(publisherDto));
    }

}

