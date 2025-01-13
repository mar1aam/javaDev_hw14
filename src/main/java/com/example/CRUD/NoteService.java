package com.example.CRUD;

import com.example.todolist.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final List<Note> notes = new ArrayList<>();
    private long currentId = 1;

    public List<Note> listAll() {
        return new ArrayList<>(notes);
    }

    public Note add(Note note) {
        note.setId(currentId++);
        notes.add(note);
        return note;
    }

    public void deleteById(long id) {
        boolean removed = notes.removeIf(note -> note.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Note with id " + id + " not found");
        }
    }

    public void update(Note note) {
        Optional<Note> existingNote = notes.stream()
                .filter(n -> n.getId() == note.getId())
                .findFirst();

        if (existingNote.isPresent()) {
            Note toUpdate = existingNote.get();
            toUpdate.setTitle(note.getTitle());
            toUpdate.setContent(note.getContent());
        } else {
            throw new IllegalArgumentException("Note with id " + note.getId() + " not found");
        }
    }

    public Note getById(long id) {
        return notes.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Note with id " + id + " not found"));
    }
}