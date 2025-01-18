package com.example.todolist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@Controller
public class NoteController {
    private final List<Note> notes = new ArrayList<>();
    public NoteController() {
        // Тестові нотатки
        notes.add(new Note(1L, "Нотатка 1", "Зелений, жовтий, червоний"));
        notes.add(new Note(2L, "Нотатка 2", "1, 2, 3"));
        notes.add(new Note(3L, "Нотатка 3", "3, 2, 1"));
    }
    @GetMapping("/note/list")
    public String listNotes(Model model) {
        model.addAttribute("notes", notes);
        return "note-list";
    }
    @PostMapping("/note/delete")
    public String deleteNote(@RequestParam int id) {
        notes.removeIf(note -> note.getId() == id);
        return "redirect:/note/list";
    }
    @GetMapping("/note/edit")
    public String editNotePage(@RequestParam int id, Model model) {
        Note note = notes.stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("note", note);
        return "note-edit";
    }
    @PostMapping("/note/edit")
    public String editNote(@RequestParam int id, @RequestParam String title, @RequestParam String content) {
        for (Note note : notes) {
            if (note.getId() == id) {
                note.setTitle(title);
                note.setContent(content);
                break;
            }
        }
        return "redirect:/note/list";
    }
}