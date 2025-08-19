package com.spring.notes.services;

import com.spring.notes.entities.Note;
import com.spring.notes.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public ResponseEntity<?> getAllNotes(Long userId){
        List<Note> notes = noteRepository.findNoteByUserId(userId);
        if(!notes.isEmpty()){
            return ResponseEntity.ok(notes);
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Empty Here! Create now!");
    }

    public Note getNoteById(Long noteId, Long userId){
        return noteRepository.findByIdAndUserId(noteId,userId)
                .orElseThrow();
    }
}
