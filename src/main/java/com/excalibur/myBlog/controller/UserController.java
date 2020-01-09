package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.controller.service.PublicationService;
import com.excalibur.myBlog.controller.service.UserService;
import com.excalibur.myBlog.controller.service.VerificationDataService;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.EditProfileForm;
import com.excalibur.myBlog.form.PublicationForm;
import com.excalibur.myBlog.utils.Environment;
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
public class UserController{

    @Autowired
    private UserService userService;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private VerificationDataService verificationDataService;




    @GetMapping(value = "/user/redirect")
    public String loginSuccess(HttpServletRequest httpServletRequest){
        String login = httpServletRequest.getRemoteUser();
        Integer id = verificationDataService.findByLogin(login).getUserId();
       return "redirect:/user/id="
               + id;
    }


    @GetMapping(value = "/user/id={userId}")
    public String showHomePage(@PathVariable(name = "userId") Integer userId, Model model){
        Optional<User> userOptional = userService.findUserById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("user", user);
            List<Publication> publications = publicationService.findPublicationsByUser(user);
            model.addAttribute("publications", publications);
            if ( user.hasAvatar()) {
                model.addAttribute("avatarURL", Environment.fileServerDomain + "/user/" + userId + "/avatar");
            } else {
                model.addAttribute("avatarURL", Environment.fileServerDomain + Environment.defaultAvatarURI);
            }
            return "home-page";
        }
        else {
            return "error-page";
        }
    }

    @GetMapping(value = "/user/id={userId}/createPublication")
    public String getPublicationForm( @PathVariable(name = "userId") Integer userId,
                                    PublicationForm publicationForm,
                                    Model model){
        model.addAttribute("userId", userId);
        return "createPublication";
    }

    @PostMapping(value = "/user/id={userId}/createPublication")
    public String createPublication( @PathVariable(name = "userId") Integer userId,
                                     @Valid PublicationForm publicationForm,
                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/user/id=" + userId + "/createPublication";
        }
        else {
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
                return "redirect:/user/id=" + userId;
            }
            else {
                return "error-page";
            }
        }
    }

    @GetMapping(value = "/user/id={userId}/findUsers")
    public String getFindUsersForm( @PathVariable(name = "userId") Integer userId,
                                  Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("userInfo", new User());
        return "findUsers";
    }

    @PostMapping(value = "/user/id={userId}/findUsers")
    public String findUsers(@PathVariable(name = "userId") Integer userId,
                           @ModelAttribute(name = "userInfo") User userInfo)
    {
        return "redirect:/user/id=" + userId + "/showUsers"
                + userInfo.getName() + "," + userInfo.getSurname();
    }

    @GetMapping(value = "/user/id={userId}/showUsers{name},{surname}")
    public String showUsers( @PathVariable(name = "userId") Integer userId,
                             @PathVariable(name = "name") String name,
                             @PathVariable(name = "surname") String surname,
                             Model model){
        Optional<List<User>> users = userService.findUsersByNameOrSurname(name, surname);
        model.addAttribute("userId", userId);
        model.addAttribute("users", users.orElseGet(ArrayList::new));
        return "showUsers";
    }

    @GetMapping(value = "/user/id={userId}/showUserPage/id={id}")
    public String showUserPage( @PathVariable(name = "userId") Integer userId,
                                @PathVariable(name = "id") Integer id,
                                Model model){
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("userId", userId);
            model.addAttribute("user", user);
            List<Publication> publications = publicationService.findPublicationsByUser(user);
            model.addAttribute("publications", publications);
            return "showUserPage";
        }
        else {
            return "error-page";
        }
    }

    @GetMapping(value = "/user/id={userId}/editProfile")
    public String getEditProfilePage( @PathVariable(name = "userId") Integer userId,
//                                      EditProfileForm editProfileForm,
                                      Model model){
//        model.addAttribute("userId", userId);
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            if ( user.hasAvatar()) {
                model.addAttribute("avatarURL", Environment.fileServerDomain + "/user/" + userId + "/avatar");
            } else {
                model.addAttribute("avatarURL", Environment.fileServerDomain + Environment.defaultAvatarURI);
            }
            return "editProfilePage";
        } else {
            return "error-page";
        }

    }

    @PostMapping(value = "/user/id={userId}/editProfile")
    public String editProfile( @PathVariable(name = "userId") Integer userId,
//                               @Valid EditProfileForm editProfileForm,
                               @ModelAttribute(name = "user") @Valid User user,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/user/id=" + userId + "/editProfile";
        }
        else {
//            User user = new User();
//            user.setId(userId);
//            user.setName(editProfileForm.getName());
//            user.setSurname(editProfileForm.getSurname());
//            user.setAbout(editProfileForm.getAbout());
//            if (editProfileForm.getAvatar() != null) {
//                user.setHasAvatar(true);
//            }
            userService.updateUser(user);
            return "redirect:/user/id=" + userId;
        }
    }

//    @DeleteMapping(value = "/user/id={userId}/deletePublication/id={pubId}")
    @GetMapping(value = "/user/id={userId}/deletePublication/id={pubId}")
    public String deletePublication( @PathVariable(name = "userId") Integer userId,
                                     @PathVariable(name = "pubId") Integer pubId)
    {
        publicationService.deletePublicationById(pubId);
        return "redirect:/user/id=" + userId;
    }

    @GetMapping(value = "/error")
    public String error(){
        return "error-page";
    }

}
