package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.service.FileService;
import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    public File createFile(File file) {
        return fileRepository.save(file);
    }

}
