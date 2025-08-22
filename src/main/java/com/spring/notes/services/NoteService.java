package com.spring.notes.services;

import com.spring.notes.entities.Note;
import com.spring.notes.repositories.NoteRepository;
import com.spring.notes.repositories.UserRepository;
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
    private final UserRepository userRepository;

    public List<Note> getAllNotes(Long userId){
        return noteRepository.findNoteByUserId(userId);

    }

    public Note getNoteById(Long noteId, Long userId){

         Note note =  noteRepository.findByIdAndUserId(noteId,userId)
                .orElseThrow();
        System.out.println(note.getId() + " " + note.getTitle() + " " + note.getContent() );
        return note;
    }

    public Long saveNote(Long userId,String title, String content){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Note note = new Note();
        note.setUser(user);
        note.setContent(content);
        note.setTitle(title);

        noteRepository.save(note);
        return note.getId();
    }

    public boolean deleteNote(Long noteId,Long userId){
         noteRepository.deleteByIdAndUserId(noteId,userId);

         return true;
    }
}
