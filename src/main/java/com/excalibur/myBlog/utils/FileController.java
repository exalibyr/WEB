package com.excalibur.myBlog.utils;

import com.excalibur.myBlog.utils.fileService.FTPService;
import com.excalibur.myBlog.utils.fileService.StoreResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@RestController
public class FileController {

    @Autowired
    FTPService ftpService;

    @GetMapping(value = "/retriveFile")
    public byte[] retriveFile(String fileName, String userId) {
        return null;
    }

    @PostMapping(value = "/storeFile?userId={userId}&fileName={fileName}")
    public String storeFile(@RequestParam(name = "userId") String userId,
                            @RequestParam(name = "fileName") String fileName,
                            @RequestBody byte[] fileContent) {
        StoreResult storeResult = ftpService.storeFile(userId, fileName, fileContent);
        try {
            return new ObjectMapper().writeValueAsString(storeResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "FATAL ERROR: Couldn't compose JSON response";
        }
    }

}
