package com.excalibur.myBlog.fileStorage.service;

import com.excalibur.myBlog.dao.response.ResponseBodyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public interface FileWebService {

    String postFile(MultipartFile multipartFile, Integer userId) throws ResponseBodyException, ResponseStatusException, IOException;

}
