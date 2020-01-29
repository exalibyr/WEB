package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.File;
import org.springframework.stereotype.Service;

@Service
public interface FileService {

    File save(File file);

}
