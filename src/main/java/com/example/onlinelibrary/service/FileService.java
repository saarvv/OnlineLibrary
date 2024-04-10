package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.File;
import com.example.onlinelibrary.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService implements IFileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public void save(File file) {
        fileRepository.save(file);
    }

    public File getFile(Long id) {
        return fileRepository.findById(id).get();
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
}


