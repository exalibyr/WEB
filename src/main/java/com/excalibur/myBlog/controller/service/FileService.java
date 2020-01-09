package com.excalibur.myBlog.controller.service;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File save(File file) {
        return fileRepository.save(file);
    }


}
