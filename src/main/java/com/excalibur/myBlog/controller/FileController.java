package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.response.ResponseBody;
import com.excalibur.myBlog.fileStorage.entity.FileStorageResponseBody;
import com.excalibur.myBlog.service.FileService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = ApplicationUtils.CREATE_FILE_CALLBACK)
    public ResponseEntity<ResponseBody> createFile(@RequestBody FileStorageResponseBody responseBody) {
        try {
            File file = fileService.setAvatarFile(responseBody);
            return new ResponseEntity<>(
                    new ResponseBody(
                            true,
                            ApplicationUtils.getUserAvatarURI(file.getUser())
                    ),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    new ResponseBody(
                            false,
                            e.getMessage()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
