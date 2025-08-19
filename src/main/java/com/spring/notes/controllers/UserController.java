package com.spring.notes.controllers;

import com.spring.notes.entities.Note;
import com.spring.notes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final NoteService noteService;

    @GetMapping("/dashboard")
    public String dashboardPage(){
        return "dashboard";
    }

    @GetMapping("/notes")
    @ResponseBody
    public ResponseEntity<?> getNotes(@RequestParam Long userId){
        return noteService.getAllNotes(userId);
    }

    @GetMapping("/note/{noteId}")
    @ResponseBody
    public ResponseEntity<?> getNote(@RequestParam Long userId,
                        @PathVariable("noteId") Long noteId){
        try{
            return ResponseEntity.ok(noteService.getNoteById(noteId,userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Illegal Request! "+e);

        }
    }

}
