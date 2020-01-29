package com.excalibur.myBlog.controller;


import com.excalibur.myBlog.service.PublicationService;
import com.excalibur.myBlog.service.Impl.UserServiceImpl;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.RegistrationForm;
import com.excalibur.myBlog.configuration.Environment;
import com.excalibur.myBlog.utils.PublicationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GuestController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PublicationService publicationService;

    @GetMapping(value = "/guest/sign-up")
    public String showRegistrationForm(RegistrationForm registrationForm){
        return "sign-up";
    }

    @PostMapping(value = "/guest/sign-up")
    public String tryToRegisterUser( @Valid RegistrationForm registrationForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "sign-up";
        } else {
            try {
                User createdUser = userService.createUser(registrationForm);
                return "redirect:/guest/registration-success?id=" + createdUser.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return Environment.ERROR_REDIRECT;
            }
        }
    }

    @GetMapping(value = "/guest/registration-success")
    public String registrationSuccess(@RequestParam(name = "id") Integer id, Model model){
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("user", user);
            model.addAttribute("welcomeURI", Environment.getWelcomeURI());
            return "registration-success";
        } else {
            return Environment.ERROR_TEMPLATE;
        }
    }

    @GetMapping(value = "/guest/findUsers")
    public String getFindUsersForm(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                   Model model){
        model.addAttribute("userId", null);
        model.addAttribute("userInfo", new User());
        model.addAttribute("backURI", priorPath);
        return "findUsers";
    }

    @PostMapping(value = "/guest/findUsers")
    public String findUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @ModelAttribute(name = "userInfo") User userInfo) {
        return "redirect:/guest/showUsers?name="
                + userInfo.getName() + "&surname=" + userInfo.getSurname() + "&prior=" + priorPath;
    }

    @GetMapping(value = "/guest/showUsers")
    public String showUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @RequestParam(name = "name", required = false, defaultValue = "") String name,
                            @RequestParam(name = "surname", required = false, defaultValue = "") String surname,
                            Model model){
        Optional<List<User>> users = userService.findUsersByNameOrSurname(name, surname);
        model.addAttribute("userId", null);
        model.addAttribute("users", users.orElseGet(ArrayList::new));
        model.addAttribute("backURI", priorPath);
        return "showUsers";
    }

    @GetMapping(value = "/guest/showUser/{id}")
    public String showUserPage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                               @PathVariable(name = "id") Integer id,
                               Model model){
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("userId", null);
            model.addAttribute("user", user);
            List<PublicationWrapper> publicationWrappers = publicationService.getUserPublications(user);
            model.addAttribute("publicationWrappers", publicationWrappers);
            model.addAttribute("backURI", priorPath);
            return "showUserPage";
        }
        else {
            return Environment.ERROR_TEMPLATE;
        }
    }

}
