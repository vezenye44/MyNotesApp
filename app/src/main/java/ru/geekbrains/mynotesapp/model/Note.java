package ru.geekbrains.mynotesapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class Note implements Parcelable {
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

    protected Note(Parcel in) {
        titleOfNote = in.readString();
        textOfNote = in.readString();
        createData = (LocalDate) in.readSerializable();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        if (titleOfNote.equals("")) {
            return "note " + (notes.indexOf(this) + 1);
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(titleOfNote);
        parcel.writeString(textOfNote);
        parcel.writeSerializable(createData);
    }
}