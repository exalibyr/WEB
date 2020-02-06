package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.fileStorage.entity.FileStorageResponseBody;
import com.excalibur.myBlog.service.FileService;
import com.excalibur.myBlog.service.UserService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @PostMapping(value = ApplicationUtils.CREATE_FILE_CALLBACK)
    public @ResponseBody String createFile(@RequestBody FileStorageResponseBody responseBody) {
        try {
            File file = fileService.setAvatarFile(responseBody);
            return ApplicationUtils.getUserAvatarURI(file.getUser());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
