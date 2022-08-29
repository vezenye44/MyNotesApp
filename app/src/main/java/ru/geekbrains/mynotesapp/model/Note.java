package ru.geekbrains.mynotesapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Note implements Parcelable {

    private String id;
    private String titleOfNote;
    private String textOfNote;
    private Date date;

    public Note(String titleOfNote, String textOfNote, Date date) {
        this.titleOfNote = titleOfNote;
        this.textOfNote = textOfNote;
        this.date = date;
    }

    protected Note(Parcel in) {
        titleOfNote = in.readString();
        textOfNote = in.readString();
        date = (Date) in.readSerializable();
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

//    @NonNull
//    @Override
//    public String toString() {
//        if (titleOfNote.equals("")) {
//            return "note " + (notes.indexOf(this) + 1);
//        }
//        return getTitleOfNote();
//    }


    public String getTitleOfNote() {
        return titleOfNote;
    }

    public String getTextOfNote() {
        return textOfNote;
    }


    public void setTitleOfNote(String titleOfNote) {
        this.titleOfNote = titleOfNote;
    }

    public void setTextOfNote(String textOfNote) {
        this.textOfNote = textOfNote;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(titleOfNote);
        parcel.writeString(textOfNote);
        parcel.writeSerializable(date);
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}