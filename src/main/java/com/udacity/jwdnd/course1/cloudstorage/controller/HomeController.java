package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private NotesService notesService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            model.addAttribute("userFiles", fileService.getUserFiles(currUser.getUserId()));
            model.addAttribute("userNotes", notesService.getUserNotes(currUser.getUserId()));
            model.addAttribute("credentials", credentialService.getUserCredentials(currUser.getUserId()));
            model.addAttribute("encryptionService", encryptionService);
        }

        return "home";
    }

    @PostMapping("/note")
    public String submitUserNote(Authentication authentication, @ModelAttribute Notes notes, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            try {
                if (notes.getNoteId() == null) {
                    notes.setUserId(currUser.getUserId());
                    notesService.insert(notes);
                } else {
                    notesService.update(notes);
                }

                model.addAttribute("success", true);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        model.addAttribute("tabName", "nav-notes");

        return "result";
    }

    @GetMapping("/note/delete/{id}")
    public String deleteUserNote(Authentication authentication, @PathVariable Integer id, Model model) {

        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            try {
                notesService.delete(id);
                model.addAttribute("success", true);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        model.addAttribute("tabName", "nav-notes");

        return "result";
    }

    @PostMapping("/credential")
    public String submitUserCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            try {
                if (credential.getCredentialId() == null) {
                    credential.setUserId(currUser.getUserId());
                    credentialService.insert(credential);
                } else {
                    credentialService.update(credential);
                }
                model.addAttribute("success", true);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        model.addAttribute("tabName", "nav-credentials");

        return "result";
    }

    @GetMapping("/credential/delete/{id}")
    public String deleteUserCredential(Authentication authentication, @PathVariable Integer id, Model model) {

        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            try {
                credentialService.delete(id);
                model.addAttribute("success", true);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        model.addAttribute("tabName", "nav-credentials");

        return "result";
    }

    @PostMapping("/file/upload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model) {
        model.addAttribute("tabName", "nav-files");

        if (file.isEmpty()) {
            return "result";
        }

        User currUser = userService.getUser(authentication.getName());

        if (currUser == null) {
            return "result";
        }

        try {
            fileService.storeFile(file, currUser.getUserId());
            model.addAttribute("success", true);
            return "result";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "result";
        }
    }

    @GetMapping(value = "/file/view/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] viewFile(Authentication authentication, @PathVariable Integer id) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            File userFile = fileService.getFile(id);
            return userFile.getFileData();
        }

        return null;
    }

    @GetMapping("/file/delete/{id}")
    public String deleteFile(Authentication authentication, @PathVariable Integer id, Model model) {
        User currUser = userService.getUser(authentication.getName());

        if (currUser != null) {
            try {
                fileService.delete(id);
                model.addAttribute("success", true);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        model.addAttribute("tabName", "nav-files");

        return "result";
    }

}
