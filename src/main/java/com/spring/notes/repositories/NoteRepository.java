package com.spring.notes.repositories;

import com.spring.notes.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findNoteByUserId(Long userId);
    Optional<Note> findByIdAndUserId(Long noteId,Long userId);
}
