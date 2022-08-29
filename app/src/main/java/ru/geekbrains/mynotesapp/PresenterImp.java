package ru.geekbrains.mynotesapp;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.mynotesapp.model.DataSource;
import ru.geekbrains.mynotesapp.model.Note;
import ru.geekbrains.mynotesapp.ui.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class PresenterImp implements Presenter {

    private FragmentManager fragmentManager;

    private DataSource dataSource;
    private static PresenterImp INSTANCE;
    private boolean isLandscape;
    private boolean isCalendarSelect = false;
    private Note note;
    private boolean isDeleteNoteProcess;
    private int deleteNotePosition;

    @Override
    public void updateDate(int dayOfMonth, int month, int year) {
        note.setDate(new Date(year, month, dayOfMonth));
        dataSource.updateData(dataSource.indexOf(note),note);
    }

    @Override
    public void updateTitle(String title) {
        note.setTitleOfNote(title);
        dataSource.updateData(dataSource.indexOf(note),note);
    }

    @Override
    public void updateText(String text) {
        note.setTextOfNote(text);
        dataSource.updateData(dataSource.indexOf(note),note);
    }

    @Override
    public void updatedDate() {

        RecycleNotesFragment recycleFragment = (RecycleNotesFragment) fragmentManager.getFragments().stream().
                filter((fragment) -> (fragment.getClass() == RecycleNotesFragment.class))
                .findFirst().get();

        dataSource.updateData(dataSource.indexOf(note),note);
        recycleFragment.updateItem(dataSource.indexOf(note));
        //Note.getNotes().indexOf(note)
    }

    @Override
    public void updatedTitle() {
        RecycleNotesFragment recycleFragment = (RecycleNotesFragment) fragmentManager.getFragments().stream().
                filter((fragment) -> (fragment.getClass() == RecycleNotesFragment.class))
                .findFirst().get();

        dataSource.updateData(dataSource.indexOf(note),note);
        recycleFragment.updateItem(dataSource.indexOf(note));
        //Note.getNotes().indexOf(note)
    }

    @Override
    public void updatedText() {
    }

    private @IdRes int fragContainerId01;
    private @IdRes int fragContainerId02;

    private PresenterImp(FragmentActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
        FragmentContainer fragmentContainer = (FragmentContainer) activity;
        fragContainerId01 = fragmentContainer.getIdResFirstContainer();
        fragContainerId02 = fragmentContainer.getIdResSecondContainer();
    }

    public static Presenter getInstance(FragmentActivity activity) {
        if (INSTANCE == null) {
            INSTANCE = new PresenterImp(activity);
        }
        return INSTANCE;
    }

    @Override
    public void onClickAddButton() {
        onClickAddButton("");
    }

    private void onClickAddButton(String title) {
        note = new Note(title, "", Date.from(Instant.now()));
        dataSource.addData(note);

        if (isLandscape) {
            fragmentManager.beginTransaction().replace(fragContainerId01, RecycleNotesFragment.newInstance()).commit();
        }

        onClickNotesItem(note);
    }

    @Override
    public void onDeleteNote(int position) {
        int notePosition = dataSource.indexOf(note);
        //Note.getNotes().indexOf(note)
        if (notePosition == position) {
            note = null;

            RecycleNotesFragment recycleFragment = (RecycleNotesFragment) fragmentManager.getFragments().stream().
                    filter((fragment) -> (fragment.getClass() == RecycleNotesFragment.class))
                    .findFirst().get();

            recycleFragment.deleteItem(position);
            dataSource.deleteData(position);

            if (isCalendarSelect) {
                fragmentManager.popBackStack();
                isCalendarSelect = false;
            }

            if (isLandscape) {
                fragmentManager.beginTransaction().add(fragContainerId02, NoteDetailFragment.newInstance(onCreateDetailFragment())).addToBackStack("").commit();

            } else {
                fragmentManager.popBackStack();
            }
        } else {

            RecycleNotesFragment recycleFragment = (RecycleNotesFragment) fragmentManager.getFragments().stream().
                    filter((fragment) -> (fragment.getClass() == RecycleNotesFragment.class))
                    .findFirst().get();

            recycleFragment.deleteItem(position);
            dataSource.deleteData(position);

        }
    }

    @Override
    public void onClickDeleteNote() {
        onClickDeleteNote(dataSource.indexOf(note));
    }

    @Override
    public void onClickDeleteNote(int position) {
        deleteNotePosition = position;
        isDeleteNoteProcess = true;
        new AlertDialog.Builder(appContext)
                .setTitle("Удалить заметку?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    onDeleteNote(position);
                    isDeleteNoteProcess = false;
                    Toast.makeText(appContext, "Заметка удалена", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    isDeleteNoteProcess = false;
                    Toast.makeText(appContext, "Удаление отменено", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    Context appContext;

    @Override
    public void setAppContext(Context context) {
        appContext = context;
    }

    @Override
    public void onClickAddSmartNoteBtn() {
        new AddSmartNoteDialogFragment().show(fragmentManager, AddSmartNoteDialogFragment.TAG);
    }

    @Override
    public void onClickAddSmartNote(String toString) {
        onClickAddButton(toString);
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void onClickNotesItem(Note note) {
        isCalendarSelect = false;
        this.note = note;
        if (isLandscape) {
            fragmentManager.beginTransaction().replace(fragContainerId02, NoteDetailFragment.newInstance(onCreateDetailFragment())).commit();
        } else {
            fragmentManager.beginTransaction().add(fragContainerId01, NoteDetailFragment.newInstance(onCreateDetailFragment())).addToBackStack("").commit();
        }

    }

    @Override
    public void onFirstCreateActivity(FragmentActivity activity) {
        isLandscape = activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        fragmentManager = activity.getSupportFragmentManager();

        if (isLandscape) {
            FragmentContainer fragmentContainer = (FragmentContainer) activity;
            fragContainerId01 = fragmentContainer.getIdResFirstContainer();
            fragContainerId02 = fragmentContainer.getIdResSecondContainer();
        }

        initFragments();

        if (isCalendarSelect) {
            isCalendarSelect = false;
            onDateClick();
        }
    }

    @Override
    public void onCreateActivity(FragmentActivity activity) {
        isLandscape = activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        fragmentManager = activity.getSupportFragmentManager();

        if (isLandscape) {
            fragmentManager.beginTransaction().replace(fragContainerId02, NoteDetailFragment.newInstance(onCreateDetailFragment())).commit();
        } else {
            if (note != null)
                fragmentManager.beginTransaction().add(fragContainerId01, NoteDetailFragment.newInstance(onCreateDetailFragment())).addToBackStack("").commit();
        }

        if (isCalendarSelect) {
            isCalendarSelect = false;
            onDateClick();
        }
        if (isDeleteNoteProcess) {
            onClickDeleteNote(deleteNotePosition);
        }
    }

    private void initFragments() {
        if (isLandscape) {
            fragmentManager.beginTransaction().replace(fragContainerId01, RecycleNotesFragment.newInstance()).commit();

            fragmentManager.beginTransaction().replace(fragContainerId02, NoteDetailFragment.newInstance(onCreateDetailFragment())).commit();
        } else {
            fragmentManager.beginTransaction().replace(fragContainerId01, RecycleNotesFragment.newInstance()).commit();

            if (note != null)
                fragmentManager.beginTransaction().add(fragContainerId01, NoteDetailFragment.newInstance(onCreateDetailFragment())).addToBackStack("").commit();
        }
    }

    @Override
    public Note onCreateDetailFragment() {
        if (note != null) {
            return note;
        } else if (!dataSource.isEmpty()) {
            return note = dataSource.getData(0);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if (!isLandscape & !isCalendarSelect) {
            note = null;
            initFragments();
        } else if (isCalendarSelect) {
            isCalendarSelect = false;
            initFragments();
        }
    }

    @Override
    public void onDateClick() {
        if (!isCalendarSelect) {
            isCalendarSelect = true;
            Date date = note.getDate();
            if (isLandscape) {
                //
                fragmentManager.beginTransaction().add(fragContainerId02, SelectDateFragment.newInstance(date)).addToBackStack("").commit();

            } else {
                //
                fragmentManager.beginTransaction().add(fragContainerId01, SelectDateFragment.newInstance(date)).addToBackStack("").commit();

            }
        }
    }
}
