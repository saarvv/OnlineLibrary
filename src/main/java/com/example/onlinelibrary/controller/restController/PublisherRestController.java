package com.example.onlinelibrary.controller.restController;

import com.example.onlinelibrary.model.Publisher;
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
    @ModelAttribute("publisher")
    public com.example.onlinelibrary.model.Publisher publisher() {
        return new com.example.onlinelibrary.model.Publisher();
    }
    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() throws NotFoundException {
        List<Publisher> publishers = publisherService.getAll();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Publisher>> findAllByName(@RequestParam String name) throws NotFoundException {
        List<Publisher> publishers = publisherService.findAllByName(name);
        return ResponseEntity.ok(publishers);
    }

    @PostMapping("/save")
    public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher) {
        return ResponseEntity.ok(publisherService.save(publisher));
    }
}
