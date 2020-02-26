package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.fileStorage.configuration.FileStorageConfiguration;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController {

    @GetMapping(value = ApplicationUtils.ERROR_URN)
    public String getErrorPage(Model model) {
        model.addAttribute("errorURI", FileStorageConfiguration.getErrorURI());
        return ApplicationUtils.getErrorTemplate();
    }

}
