package com.spring.notes.repositories;

import com.spring.notes.entities.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findNoteByUserId(Long userId);

    @Query("SELECT n FROM Note n WHERE n.id = :noteId AND n.user.id = :userId")
    Optional<Note> findByIdAndUserId(Long noteId,Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Note n WHERE n.id = :noteId AND n.user.id = :userId")
    void deleteByIdAndUserId(Long noteId,Long userId);
}
