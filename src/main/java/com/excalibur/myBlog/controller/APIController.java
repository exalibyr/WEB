package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.response.ResponseBody;
import com.excalibur.myBlog.service.FileService;
import com.excalibur.myBlog.service.UserService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    JacksonJsonParser jsonParser = new JacksonJsonParser();

    @Autowired
    UserService userService;

    @Autowired
    private FileService fileService;

    @PatchMapping(value = "/admin/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseBody> updateUserData(@RequestBody String body) {
        try {
            List<Object> payload = this.jsonParser.parseList(body);
            this.userService.updateUsers(payload);
            return new ResponseEntity<>(
                    new ResponseBody(true),
                    HttpStatus.ACCEPTED
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    new ResponseBody(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping(value = ApplicationUtils.CREATE_FILE_CALLBACK)
    public ResponseEntity<ResponseBody> createFile(@RequestBody String body) {
        try {
            Map<String, Object> payload = this.jsonParser.parseMap(body);
            File file = this.fileService.setAvatarFile(payload);
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
