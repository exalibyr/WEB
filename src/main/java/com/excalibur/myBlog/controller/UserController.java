package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.controller.service.PublicationService;
import com.excalibur.myBlog.controller.service.UserService;
import com.excalibur.myBlog.controller.service.VerificationDataService;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.PublicationForm;
import com.excalibur.myBlog.utils.Environment;
import com.excalibur.myBlog.utils.PublicationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
//@PreAuthorize(value = "hasRole('USER')")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private VerificationDataService verificationDataService;




    @GetMapping(value = "/user/redirect")
    public String loginSuccess(HttpServletRequest httpServletRequest){
        String login = httpServletRequest.getRemoteUser();
        Integer id = verificationDataService.findByLogin(login).getUser().getId();
       return "redirect:/user/id="
               + id;
    }


    @GetMapping(value = "/user/id={userId}")
    public String showHomePage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                               @PathVariable(name = "userId") Integer userId,
                               Model model){
        Optional<User> userOptional = userService.findUserById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("user", user);
//            List<Publication> publications = publicationService.findPublicationsByUser(user);
            List<PublicationWrapper> publicationWrappers = publicationService.getUserPublications(user);
            model.addAttribute("publicationWrappers", publicationWrappers);
            if ( user.hasAvatar()) {
                model.addAttribute("avatarURI", Environment.getFileStorageURL() + "/user/" + userId + "/avatar");
            } else {
                model.addAttribute("avatarURI", Environment.getDefaultAvatarURI());
            }
            model.addAttribute("backURI", priorPath);
            return "home-page";
        }
        else {
            return "redirect:/error";
        }
    }

    @GetMapping(value = "/user/id={userId}/createPublication")
    public String getPublicationForm(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "userId") Integer userId,
                                     PublicationForm publicationForm,
                                     Model model) {
        model.addAttribute("backURI", priorPath);
        return "createPublication";
    }

    @PostMapping(value = "/user/id={userId}/createPublication")
    public String createPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                    @PathVariable(name = "userId") Integer userId,
                                    @Valid PublicationForm publicationForm,
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/user/id=" + userId + "/createPublication";
        } else {
            Optional<User> userOptional = userService.findUserById(userId);
            if(userOptional.isPresent()){
                //get user from db
                User user = userOptional.get();
                //create new publication instance to insert into db
                Publication newPublication = publicationForm.getPublication();
                newPublication.setDateTime(ZonedDateTime.now());
                newPublication.setUser(user);
                //add new publication to db
                publicationService.saveNewPublication(newPublication);
                return "redirect:/user/id=" + userId + "?prior=" + priorPath;
            }
            else {
                return "redirect:/error";
            }
        }
    }

    @GetMapping(value = "/user/id={userId}/findUsers")
    public String getFindUsersForm(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                   @PathVariable(name = "userId") Integer userId,
                                   Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("userInfo", new User());
        model.addAttribute("backURI", priorPath);
        return "findUsers";
    }

    @PostMapping(value = "/user/id={userId}/findUsers")
    public String findUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @PathVariable(name = "userId") Integer userId,
                            @ModelAttribute(name = "userInfo") User userInfo) {
        return "redirect:/user/id=" + userId + "/showUsers?name="
                + userInfo.getName() + "&surname=" + userInfo.getSurname() + "&prior=" + priorPath;
    }

    @GetMapping(value = "/user/id={userId}/showUsers")
    public String showUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @PathVariable(name = "userId") Integer userId,
                            @RequestParam(name = "name", required = false, defaultValue = "") String name,
                            @RequestParam(name = "surname", required = false, defaultValue = "") String surname,
                            Model model){
        Optional<List<User>> users = userService.findUsersByNameOrSurname(name, surname);
        model.addAttribute("userId", userId);
        model.addAttribute("users", users.orElseGet(ArrayList::new));
        model.addAttribute("backURI", priorPath);
        return "showUsers";
    }

    @GetMapping(value = "/user/id={userId}/showUserPage/id={id}")
    public String showUserPage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                               @PathVariable(name = "userId") Integer userId,
                               @PathVariable(name = "id") Integer id,
                               Model model){
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("userId", userId);
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

    @GetMapping(value = "/user/id={userId}/editProfile")
    public String getEditProfilePage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "userId") Integer userId,
//                                      EditProfileForm editProfileForm,
                                     Model model){
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            if ( user.hasAvatar()) {
                model.addAttribute("avatarURI", Environment.getFileStorageURL() + "/user/" + userId + "/avatar");
            } else {
                model.addAttribute("avatarURI", Environment.getDefaultAvatarURI());
            }
            model.addAttribute("backURI", priorPath);
            return "editProfilePage";
        } else {
            return "redirect:/error";
        }

    }

    @PostMapping(value = "/user/id={userId}/editProfile")
    public String editProfile(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                              @PathVariable(name = "userId") Integer userId,
//                               @Valid EditProfileForm editProfileForm,
                              @ModelAttribute(name = "user") @Valid User user,
                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/user/id=" + userId + "/editProfile";
        } else {
            userService.updateUser(user);
            return "redirect:/user/id=" + userId + "?prior=" + priorPath;
        }
    }

//    @DeleteMapping(value = "/user/id={userId}/deletePublication/id={pubId}")
    @GetMapping(value = "/user/id={userId}/deletePublication/id={pubId}")
    public String deletePublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                    @PathVariable(name = "userId") Integer userId,
                                    @PathVariable(name = "pubId") Integer pubId,
                                    Model model) {
        model.addAttribute("backURI", priorPath);
        publicationService.deletePublicationById(pubId);
        return "redirect:/user/id=" + userId;
    }

}
