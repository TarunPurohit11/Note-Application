package com.spring.notes.controllers;

import com.spring.notes.entities.Note;
import com.spring.notes.entities.User;
import com.spring.notes.services.NoteService;
import com.spring.notes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NoteService noteService;

    @GetMapping("/dashboard")
    public String dashboardPage(Model model,
                                @AuthenticationPrincipal UserDetails userDetails){
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        model.addAttribute("userId",userId);
            List<Note> notes = noteService.getAllNotes(userId);
            model.addAttribute("notesList",notes);

        return "dashboard";
    }


    @GetMapping("/note/{noteId}")
    public String getNote(@AuthenticationPrincipal UserDetails userDetails,
                        @PathVariable("noteId") Long noteId,
                          RedirectAttributes redirectAttributes){
            Long userId = userService.findByUsername(userDetails.getUsername()).getId();
            Note note = noteService.getNoteById(noteId,userId);
            System.out.println("Note is present and added"+" "+note.getUser()+" "+note.getContent()+" "+note.getId());
            redirectAttributes.addFlashAttribute("note",note);
        return "redirect:/user/note/"+noteId;
    }

    @GetMapping("/note/createNote")
    public String createNote(@RequestParam("userId") Long userId, Model model){
        System.out.println(userId);
        model.addAttribute("userId", userId);
        return "notes";
    }

    @PostMapping("note/save")
    public String save(@RequestParam("title") String title,
                       @RequestParam("content") String content,
                       @ModelAttribute("userId") Long userId,
                       Model model){
        Long noteId;
        try{
            noteId = noteService.saveNote(userId,title,content);
            model.addAttribute("success","note saved");
        }catch (Exception e){
            model.addAttribute("error",
                    "Due to some error note can't be saved!");
            throw new IllegalArgumentException(e);
        }
        return "redirect:/user/note/"+noteId;
    }

}
