package com.excalibur.myBlog.controller;


import com.excalibur.myBlog.controller.service.Impl.RoleServiceImpl;
import com.excalibur.myBlog.controller.service.UserService;
import com.excalibur.myBlog.controller.service.VerificationDataService;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.VerificationData;
import com.excalibur.myBlog.form.RegistrationForm;
import com.excalibur.myBlog.form.VerificationForm;
import com.excalibur.myBlog.utils.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class GuestController {

    @Autowired
    UserService userService;

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

    @GetMapping(value = "/sign-up")
    public String showRegistrationForm(RegistrationForm registrationForm){
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
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
                return "redirect:/registration-success?userId=" + newUser.getId();
            } else {
                throw new RuntimeException("Roles not matched to database");
            }
        }
    }

    @GetMapping(value = "/registration-success")
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

}
