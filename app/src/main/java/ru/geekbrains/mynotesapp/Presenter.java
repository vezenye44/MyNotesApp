package ru.geekbrains.mynotesapp;

import android.content.Context;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import ru.geekbrains.mynotesapp.model.DataSource;
import ru.geekbrains.mynotesapp.model.Note;

public interface Presenter {

    void onClickNotesItem(Note note);

    void onFirstCreateActivity(FragmentActivity activity);

    void onCreateActivity(FragmentActivity activity);

    Note onCreateDetailFragment();

    void onBackPressed();

    void onDateClick();

    void updateDate(int dayOfMonth, int month, int year);

    void updateTitle(String title);

    void updateText(String text);

    void updatedDate();

    void updatedTitle();

    void updatedText();

    void onClickAddButton();

    void onDeleteNote(int position);

    void onClickDeleteNote();

    void onClickDeleteNote(int position);

    void setAppContext(Context context);

    void onClickAddSmartNoteBtn();

    void onClickAddSmartNote(String toString);

    void setDataSource(DataSource dataSource);
}
