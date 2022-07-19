package ru.geekbrains.mynotesapp.model;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class Note {
    private String titleOfNote;
    private String textOfNote;
    private LocalDate createData;

    private static final ArrayList<Note> notes = new ArrayList<>();

    static {
        notes.add(new Note("title 1", "description 1", LocalDate.now()));
        notes.add(new Note("title 2", "description 2", LocalDate.now()));
        notes.add(new Note("title 3", "description 3", LocalDate.now()));
    }

    public Note(String titleOfNote, String textOfNote, LocalDate createData) {
        this.titleOfNote = titleOfNote;
        this.textOfNote = textOfNote;
        this.createData = createData;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitleOfNote();
    }

    public static ArrayList<Note> getNotes() {
        return notes;
    }

    public String getTitleOfNote() {
        return titleOfNote;
    }

    public String getTextOfNote() {
        return textOfNote;
    }

    public LocalDate getCreateData() {
        return createData;
    }

    public void setTitleOfNote(String titleOfNote) {
        this.titleOfNote = titleOfNote;
    }

    public void setTextOfNote(String textOfNote) {
        this.textOfNote = textOfNote;
    }

    public void setCreateData(LocalDate createData) {
        this.createData = createData;
    }
}