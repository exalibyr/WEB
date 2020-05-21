package com.excalibur.myBlog.controller;


import com.excalibur.myBlog.Application;
import com.excalibur.myBlog.dao.wrapper.UserWrapper;
import com.excalibur.myBlog.fileStorage.configuration.FileStorageConfiguration;
import com.excalibur.myBlog.service.PublicationService;
import com.excalibur.myBlog.service.Impl.UserServiceImpl;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.RegistrationForm;
import com.excalibur.myBlog.utils.ApplicationUtils;
import com.excalibur.myBlog.utils.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class GuestController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PublicationService publicationService;

    @GetMapping(value = "/parse")
    public @ResponseBody Image parse(@RequestParam(name = "base64") String base64) {
        return ApplicationUtils.parseBase64Image(base64);
    }

    @GetMapping(value = "/guest/signUp")
    public String showRegistrationForm(RegistrationForm registrationForm){
        return "signUp";
    }

    @PostMapping(value = "/guest/signUp")
    public String tryToRegisterUser( @Valid RegistrationForm registrationForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signUp";
        } else if ( !registrationForm.getUserPassword().equals(registrationForm.getUserPasswordRepeat())) {
            return "signUp";
        } else  {
            try {
                User createdUser = userService.createUser(registrationForm);
                return "redirect:/guest/registrationSuccess?user=" + ApplicationUtils.getEncryptedID(createdUser.getId());
            } catch (Exception e) {
                e.printStackTrace();
                return ApplicationUtils.getErrorRedirect();
            }
        }
    }

    @GetMapping(value = "/guest/registrationSuccess")
    public String registrationSuccess(@RequestParam(name = "user") String id, Model model){
        try {
            model.addAttribute("user", userService.getUser(ApplicationUtils.getDecryptedID(id)));
            model.addAttribute("welcomeURI", FileStorageConfiguration.getWelcomeURI());
            return "registrationSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }

    @GetMapping(value = "/guest/findUsers")
    public String getFindUsersForm(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                   Model model){
        model.addAttribute("userInfo", new User());
        model.addAttribute("backURI", priorPath);
        return "guest_findUsers";
    }

    @PostMapping(value = "/guest/findUsers")
    public String findUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @ModelAttribute(name = "userInfo") User userInfo) {
        return "redirect:/guest/users?name="
                + userInfo.getName() + "&surname=" + userInfo.getSurname() + "&prior=" + priorPath;
    }

    @GetMapping(value = "/guest/users")
    public String showUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @RequestParam(name = "name", required = false, defaultValue = "") String name,
                            @RequestParam(name = "surname", required = false, defaultValue = "") String surname,
                            Model model){
        model.addAttribute("userWrappers", userService.getUserWrappers(name, surname));
        model.addAttribute("backURI", priorPath);
        return "guest_showUsers";
    }

    @GetMapping(value = "/guest/user/{id}")
    public String showUserPage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                               @PathVariable(name = "id") String id,
                               Model model){
        try {
            UserWrapper wrapper = userService.getUserWrapper(id);
            model.addAttribute("userWrapper", wrapper);
            model.addAttribute("publicationWrappers", publicationService.getPublicationWrappers(wrapper.getUser()));
            model.addAttribute("backURI", priorPath);
            return "guest_showUser";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }

}
