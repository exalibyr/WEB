package com.excalibur.myBlog.controller;


import com.excalibur.myBlog.controller.service.Impl.RoleServiceImpl;
import com.excalibur.myBlog.controller.service.PublicationService;
import com.excalibur.myBlog.controller.service.UserService;
import com.excalibur.myBlog.controller.service.VerificationDataService;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.VerificationData;
import com.excalibur.myBlog.form.RegistrationForm;
import com.excalibur.myBlog.form.VerificationForm;
import com.excalibur.myBlog.utils.Environment;
import com.excalibur.myBlog.utils.PublicationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GuestController {

    @Autowired
    UserService userService;

    @Autowired
    PublicationService publicationService;

//    @Autowired
//    VerificationDataService verificationDataService;

    @Autowired
    RoleServiceImpl roleService;


//    @GetMapping(value = "/sign-in")
//    public String showSingInForm(VerificationForm verificationForm){
//        return "sign-in";
//    }
//
//    @PostMapping(value = "/sign-in")
//    public String verifyUser(@Valid VerificationForm verificationForm, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return "sign-in";
//        }
//        else{
//            Optional<VerificationData> verificationData = verificationDataService
//                    .verifyUser(verificationForm.getUserLogin(), verificationForm.getUserPassword());
//            if(verificationData.isPresent()){
//                return userService
//                        .findUserByVerificationData(verificationData.get())
//                        .map(user -> "redirect:/user/id=" + user.getId())
//                        .orElse("redirect:error");
//            }
//            else {
//                return "sign-in";
//            }
//
//        }
//    }

    @GetMapping(value = "/guest/sign-up")
    public String showRegistrationForm(RegistrationForm registrationForm){
        return "sign-up";
    }

    @PostMapping(value = "/guest/sign-up")
    public String tryToRegisterUser( @Valid RegistrationForm registrationForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "sign-up";
        } else{
            if (roleService.matchWithDatabase(Environment.getUserRolesString())) {
                User newUser = registrationForm.getUser();
                VerificationData userVerificationData = registrationForm.getValidationData();
                newUser.setVerificationData(userVerificationData);
                userVerificationData.setUser(newUser);
                newUser.setRoles(roleService.getAllowedRoles(Environment.UserRole.admin.toString()));
                newUser = userService.registerNewUser(newUser);
                return "redirect:/guest/registration-success?userId=" + newUser.getId();
            } else {
                throw new RuntimeException("Roles not matched to database");
            }
        }
    }

    @GetMapping(value = "/guest/registration-success")
    public String registrationSuccess(@RequestParam(name = "userId") Integer userId, Model model){
        Optional<User> userOptional = userService.findUserById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("user", user);
            model.addAttribute("welcomeURI", Environment.getWelcomeURI());
            return "registration-success";
        } else {
            return "redirect:/error";
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

    @GetMapping(value = "/guest/showUserPage/id={id}")
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
            return "redirect:/error";
        }
    }

}
