package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.fileStorage.configuration.FileStorageConfiguration;
import com.excalibur.myBlog.service.PublicationService;
import com.excalibur.myBlog.service.Impl.UserServiceImpl;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.PublicationForm;
import com.excalibur.myBlog.utils.ApplicationUtils;
import com.excalibur.myBlog.dao.wrapper.PublicationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletException;
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
    private UserServiceImpl userService;
    @Autowired
    private PublicationService publicationService;

    @GetMapping(value = "/home/logout")
    public String processLogout(HttpServletRequest request) {
        try {
            request.logout();
            return "redirect:/guest/login";
        } catch (ServletException e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }

    @GetMapping(value = "/home")
    public String showHomePage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                               HttpServletRequest request,
                               Model model){
        Optional<User> userOptional = userService.getUser(request.getRemoteUser());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<PublicationWrapper> publicationWrappers = publicationService.getUserPublications(user);
            model.addAttribute("user", user);
            model.addAttribute("publicationWrappers", publicationWrappers);
            model.addAttribute("backURI", priorPath);
            if ( user.hasAvatar()) {
                model.addAttribute("avatarURI", FileStorageConfiguration.getFileStorageURL() + "/user/" + user.getId() + "/avatar");
            } else {
                model.addAttribute("avatarURI", FileStorageConfiguration.getDefaultAvatarURI());
            }
            return "home";
        }
        else {
            return ApplicationUtils.getErrorTemplate();
        }
    }

    @GetMapping(value = "/home/findUsers")
    public String getFindUsersForm(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                   Model model) {
        model.addAttribute("userInfo", new User());
        model.addAttribute("backURI", priorPath);
        return "user_findUsers";
    }

    @PostMapping(value = "/home/findUsers")
    public String findUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @ModelAttribute(name = "userInfo") User userInfo) {
        return "redirect:/home/users?name="
                + userInfo.getName() + "&surname=" + userInfo.getSurname() + "&prior=" + priorPath;
    }

    @GetMapping(value = "/home/users")
    public String showUsers(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                            @RequestParam(name = "name", required = false, defaultValue = "") String name,
                            @RequestParam(name = "surname", required = false, defaultValue = "") String surname,
                            Model model){
        Optional<List<User>> users = userService.findUsersByNameOrSurname(name, surname);
        model.addAttribute("users", users.orElseGet(ArrayList::new));
        model.addAttribute("backURI", priorPath);
        return "user_showUsers";
    }

    @GetMapping(value = "/home/user/{id}")
    public String showUserPage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                               @PathVariable(name = "id") Integer id,
                               HttpServletRequest request,
                               Model model){
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if (user.getUsername().equals(request.getRemoteUser())) {
                return "redirect:/home";
            }
            List<PublicationWrapper> publicationWrappers = publicationService.getUserPublications(user);
            model.addAttribute("user", user);
            model.addAttribute("publicationWrappers", publicationWrappers);
            model.addAttribute("backURI", priorPath);
            return "user_showUser";
        } else {
            return ApplicationUtils.getErrorTemplate();
        }
    }

    @GetMapping(value = "/home/editProfile")
    public String getEditProfilePage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     HttpServletRequest request,
//                                      EditProfileForm editProfileForm,
                                     Model model){
        Optional<User> userOptional = userService.getUser(request.getRemoteUser());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            if ( user.hasAvatar()) {
                model.addAttribute("avatarURI", FileStorageConfiguration.getFileStorageURL() + "/user/" + user.getId() + "/avatar");
            } else {
                model.addAttribute("avatarURI", FileStorageConfiguration.getDefaultAvatarURI());
            }
            model.addAttribute("backURI", priorPath);
            return "editProfile";
        } else {
            return ApplicationUtils.getErrorTemplate();
        }

    }

    @PostMapping(value = "/home/editProfile")
    public String editProfile(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
//                               @Valid EditProfileForm editProfileForm,
                              @ModelAttribute(name = "user") @Valid User user,
                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/home/editProfile";
        } else {
            userService.updateUser(user);
            return "redirect:/home?prior=" + priorPath;
        }
    }

    /********************** PUBLICATIONS *******************************/

    @GetMapping(value = "/home/publication/{pubId}/delete")
    public String deletePublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                    @PathVariable(name = "pubId") Integer pubId,
                                    Model model) {
        model.addAttribute("backURI", priorPath);
        publicationService.deletePublicationById(pubId);
        return "redirect:/home";
    }

    @GetMapping(value = "/home/publication/{pubId}/edit")
    public String getEditPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "pubId") Integer pubId,
                                     PublicationForm publicationForm,
                                     Model model) {
        try {
            PublicationWrapper wrapper = publicationService.getPublicationById(pubId);
            publicationForm.setContent(wrapper.getPublication().getContent());
            publicationForm.setTitle(wrapper.getPublication().getTitle());
            model.addAttribute("backURI", priorPath);
            model.addAttribute("mode", ApplicationUtils.PageMode.edit.toString());
            model.addAttribute("pubId", pubId);
            model.addAttribute("publicationWrapper", wrapper);
            return "publication";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }

    }

    @PostMapping(value = "/home/publication/{pubId}/edit")
    public String postEditPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                      HttpServletRequest request,
                                      @PathVariable(name = "pubId") Integer pubId,
                                      @Valid PublicationForm publicationForm,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/home/publication/" + pubId + "/edit";
        } else {
            Optional<User> optionalUser = userService.getUser(request.getRemoteUser());
            if (optionalUser.isPresent()) {
                Publication publication = publicationForm.getPublication();
                publication.setDateTime(ZonedDateTime.now());
                publication.setUser(optionalUser.get());
                publication.setId(pubId);
                publicationService.saveNewPublication(publication);
                return "redirect:/home/publication/" + pubId + "/view";
            } else {
                return ApplicationUtils.getErrorRedirect();
            }
        }
    }

    @GetMapping(value = "/home/publication/create")
    public String getCreatePublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                       PublicationForm publicationForm,
                                       Model model) {
        model.addAttribute("backURI", priorPath);
        model.addAttribute("mode", ApplicationUtils.PageMode.create.name());
        return "publication";
    }

    @PostMapping(value = "/home/publication/create")
    public String postCreatePublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                        HttpServletRequest request,
                                        @Valid PublicationForm publicationForm,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/home/publication/create";
        } else {
            Optional<User> userOptional = userService.getUser(request.getRemoteUser());
            if(userOptional.isPresent()){
                //get user from db
                User user = userOptional.get();
                //create new publication instance to insert into db
                Publication newPublication = publicationForm.getPublication();
                newPublication.setDateTime(ZonedDateTime.now());
                newPublication.setUser(user);
                //add new publication to db
                publicationService.saveNewPublication(newPublication);
                return "redirect:/home?prior=" + priorPath;
            }
            else {
                return ApplicationUtils.getErrorRedirect();
            }
        }
    }

    @GetMapping(value = "/home/publication/{pubId}/view")
    public String getViewPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "pubId") Integer pubId,
                                     Model model) {
        try {
            PublicationWrapper wrapper = publicationService.getPublicationById(pubId);
            model.addAttribute("backURI", priorPath);
            model.addAttribute("mode", ApplicationUtils.PageMode.view.toString());
            model.addAttribute("pubId", pubId);
            model.addAttribute("publicationWrapper", wrapper);
            model.addAttribute("ownerId", null);
            return "publication";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }

    @GetMapping(value = "/home/user/{id}/publication/{pubId}/view")
    public String getViewPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "pubId") Integer pubId,
                                     @PathVariable(name = "id") Integer id,
                                     Model model) {
        try {
            PublicationWrapper wrapper = publicationService.getPublicationById(pubId);
            model.addAttribute("backURI", priorPath);
            model.addAttribute("mode", ApplicationUtils.PageMode.view.toString());
            model.addAttribute("pubId", pubId);
            model.addAttribute("publicationWrapper", wrapper);
            model.addAttribute("ownerId", id);
            return "publication";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }
}
