package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.configuration.AppConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping(value = AppConfiguration.ERROR_URN)
    public String getErrorPage(Model model) {
        model.addAttribute("errorURI", AppConfiguration.getErrorURI());
        return AppConfiguration.ERROR_TEMPLATE;
    }

}
