package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.configuration.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping(value = "/error")
    public String getErrorPage(Model model) {
        model.addAttribute("errorURI", Environment.getErrorURI());
        return "error-page";
    }

}
