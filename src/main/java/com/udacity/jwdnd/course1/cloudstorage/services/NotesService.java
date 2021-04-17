package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.repositories.NotesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public List<Notes> getUserNotes(Integer userId) {
        return notesRepository.getUserNotes(userId);
    }

    public Integer insert(Notes notes) {
        return notesRepository.insert(notes);
    }

    public Integer update(Notes notes) {
        return notesRepository.update(notes);
    }

    public Integer delete(Integer noteId) {
        return notesRepository.delete(noteId);
    }
}
