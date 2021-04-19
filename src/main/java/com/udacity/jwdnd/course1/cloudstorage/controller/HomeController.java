package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserService userService;
    private NotesService notesService;
    private CredentialService credentialService;

    public HomeController(UserService userService, NotesService notesService, CredentialService credentialService) {
        this.userService = userService;
        this.notesService = notesService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            model.addAttribute("userNotes", notesService.getUserNotes(currUser.getUserId()));
            model.addAttribute("credentials", credentialService.getUserCredentials(currUser.getUserId()));
        }

        return "home";
    }

    @PostMapping("/note")
    public String submitUserNote(Authentication authentication, @ModelAttribute Notes notes, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            if (notes.getNoteId() == null) {
                notes.setUserId(currUser.getUserId());
                notesService.insert(notes);
            } else {
                notesService.update(notes);
            }
            model.addAttribute("success", true);
        }

        model.addAttribute("tabName", "nav-notes");

        return "result";
    }

    @GetMapping("/note/delete/{id}")
    public String deleteUserNote(Authentication authentication, @PathVariable Integer id, Model model) {

        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            notesService.delete(id);
            model.addAttribute("success", true);
        }

        model.addAttribute("tabName", "nav-notes");

        return "result";
    }

    @PostMapping("/credential")
    public String submitUserCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            if (credential.getCredentialId() == null) {
                credential.setUserId(currUser.getUserId());
                credentialService.insert(credential);
            } else {
                credentialService.update(credential);
            }
            model.addAttribute("success", true);
        }

        model.addAttribute("tabName", "nav-credentials");

        return "result";
    }

    @GetMapping("/credential/delete/{id}")
    public String deleteUserCredential(Authentication authentication, @PathVariable Integer id, Model model) {

        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            credentialService.delete(id);
            model.addAttribute("success", true);
        }

        model.addAttribute("tabName", "nav-credentials");

        return "result";
    }
}
