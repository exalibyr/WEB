package com.excalibur.myBlog.controller;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.wrapper.UserWrapper;
import com.excalibur.myBlog.service.PublicationService;
import com.excalibur.myBlog.service.Impl.UserServiceImpl;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.PublicationForm;
import com.excalibur.myBlog.service.UserService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
//@PreAuthorize(value = "hasRole('USER')")
public class UserController {

    @Autowired
    private UserService userService;
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
        try {
            UserWrapper userWrapper = new UserWrapper(userService.getUser(request.getRemoteUser()));
            model.addAttribute("userWrapper", userWrapper);
            model.addAttribute("publicationWrappers", publicationService.getPublicationWrappers(userWrapper.getUser()));
            model.addAttribute("backURI", priorPath);
            model.addAttribute("avatarMethod", ApplicationUtils.getAvatarMethod(userWrapper.getUser()));
            model.addAttribute("callbackURI", ApplicationUtils.getCallbackURI(ApplicationUtils.Callback.createFile));
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
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
        model.addAttribute("userWrappers", userService.getUserWrappers(name, surname));
        model.addAttribute("backURI", priorPath);
        return "user_showUsers";
    }

    @GetMapping(value = "/home/user/{id}")
    public String showUserPage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                               @PathVariable(name = "id") String id,
                               HttpServletRequest request,
                               Model model){
        try {
            UserWrapper wrapper = userService.getUserWrapper(id);
            if (wrapper.getUser().getUsername().equals(request.getRemoteUser())) {
                return "redirect:/home";
            }
            model.addAttribute("userWrapper", wrapper);
            model.addAttribute("publicationWrappers", publicationService.getPublicationWrappers(wrapper.getUser()));
            model.addAttribute("backURI", priorPath);
            return "user_showUser";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }

    @GetMapping(value = "/home/editProfile")
    public String getEditProfilePage(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     HttpServletRequest request,
                                     Model model){
        try {
            User user = userService.getUser(request.getRemoteUser());
            model.addAttribute("user", user);
            model.addAttribute("avatarURI", ApplicationUtils.getUserAvatarURI(user));
            model.addAttribute("avatarMethod", ApplicationUtils.getAvatarMethod(user));
            model.addAttribute("callbackURI", ApplicationUtils.getCallbackURI(ApplicationUtils.Callback.createFile));
            model.addAttribute("backURI", priorPath);
            return "editProfile";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }

    }

    @PostMapping(value = "/home/editProfile")
    public String editProfile(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                              @ModelAttribute(name = "user") @Valid User user,
                              HttpServletRequest request,
                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/home/editProfile";
        } else {
            User existingUser = userService.getUser(request.getRemoteUser());
            user.setId(existingUser.getId());
            user.setAvatar(existingUser.getAvatar());
            userService.updateUser(user);
            return "redirect:/home?prior=" + priorPath;
        }
    }

    /********************** PUBLICATIONS *******************************/

    @GetMapping(value = "/home/publication/{pubId}/delete")
    public String deletePublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                    @PathVariable(name = "pubId") String pubId,
                                    Model model) {
        model.addAttribute("backURI", priorPath);
        publicationService.deletePublication(ApplicationUtils.getDecryptedID(pubId));
        return "redirect:/home";
    }

    @GetMapping(value = "/home/publication/{pubId}/edit")
    public String getEditPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "pubId") String pubId,
                                     Model model) {
        try {
            model.addAttribute("publication", publicationService.getPublication(ApplicationUtils.getDecryptedID(pubId)).getPublication());
            model.addAttribute("backURI", priorPath);
            model.addAttribute("mode", ApplicationUtils.PageMode.edit.toString());
            model.addAttribute("pubId", pubId);
            return "publication";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }

    }

    @PostMapping(value = "/home/publication/{pubId}/edit")
    public String postEditPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                      @PathVariable(name = "pubId") String pubId,
                                      HttpServletRequest request,
                                      @ModelAttribute(name = "publication") @Valid Publication publication,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/home/publication/" + pubId + "/edit";
        } else {
            try {
                publication.setId(ApplicationUtils.getDecryptedID(pubId));
                publication.setUser(userService.getUser(request.getRemoteUser()));
                publicationService.updatePublication(publication);
                return "redirect:/home/publication/" + pubId + "/view";
            } catch (Exception e) {
                e.printStackTrace();
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
            try {
                publicationForm.setUsername(request.getRemoteUser());
                publicationService.createPublication(publicationForm);
                return "redirect:/home?prior=" + priorPath;
            } catch (Exception e) {
                e.printStackTrace();
                return ApplicationUtils.getErrorRedirect();
            }
        }
    }

    @GetMapping(value = "/home/publication/{pubId}/view")
    public String getViewPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "pubId") String pubId,
                                     Model model) {
        try {
            model.addAttribute("publicationWrapper", publicationService.getPublication(ApplicationUtils.getDecryptedID(pubId)));
            model.addAttribute("backURI", priorPath);
            model.addAttribute("mode", ApplicationUtils.PageMode.view.toString());
            model.addAttribute("pubId", pubId);
            model.addAttribute("ownerId", null);
            return "publication";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }

    @GetMapping(value = "/home/user/{id}/publication/{pubId}/view")
    public String getViewPublication(@RequestParam(name = "prior", required = false, defaultValue = "") String priorPath,
                                     @PathVariable(name = "pubId") String pubId,
                                     @PathVariable(name = "id") String id,
                                     Model model) {
        try {
            model.addAttribute("publicationWrapper", publicationService.getPublication(ApplicationUtils.getDecryptedID(pubId)));
            model.addAttribute("backURI", priorPath);
            model.addAttribute("mode", ApplicationUtils.PageMode.view.toString());
            model.addAttribute("pubId", pubId);
            model.addAttribute("ownerId", id);
            return "publication";
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationUtils.getErrorTemplate();
        }
    }
}
