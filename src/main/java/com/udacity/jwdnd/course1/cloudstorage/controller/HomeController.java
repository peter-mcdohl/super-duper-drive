package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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

    public HomeController(UserService userService, NotesService notesService) {
        this.userService = userService;
        this.notesService = notesService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            model.addAttribute("userNotes", notesService.getUserNotes(currUser.getUserId()));
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
}
