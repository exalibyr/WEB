package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin/users")
    public String getUsers(Model model) {
        List<User> users = this.userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("admin", true);
        return "admin_showUsers";
    }

}
