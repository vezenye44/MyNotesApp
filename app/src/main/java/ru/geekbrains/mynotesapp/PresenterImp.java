package ru.geekbrains.mynotesapp;

import android.content.res.Configuration;
import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.mynotesapp.model.Note;
import ru.geekbrains.mynotesapp.ui.FragmentContainer;
import ru.geekbrains.mynotesapp.ui.NoteDetailFragment;
import ru.geekbrains.mynotesapp.ui.NotesFragment;
import ru.geekbrains.mynotesapp.ui.SelectDateFragment;

import java.time.LocalDate;

public class PresenterImp implements Presenter {

    private FragmentManager fragmentManager;
    private static PresenterImp INSTANCE;
    private boolean isLandscape;
    private boolean isCalendarSelect = false;
    private Note note;

    public PresenterImp() {
        super();
    }

    @Override
    public void updateDate(int dayOfMonth, int month, int year) {
        note.setCreateData(LocalDate.of(year, month, dayOfMonth));
    }

    @Override
    public void updateTitle(String title) {
        note.setTitleOfNote(title);
    }

    @Override
    public void updateText(String text) {
        note.setTextOfNote(text);
    }

    @Override
    public void updatedDate() {
    }

    @Override
    public void updatedTitle() {
        if (isLandscape & !isCalendarSelect)
            fragmentManager.beginTransaction().replace(fragContainerId01, NotesFragment.newInstance()).commit();
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
    public void onClickNotesItem(Note note) {
        isCalendarSelect = false;
        this.note = note;
        if (isLandscape) {
            fragmentManager.beginTransaction().replace(fragContainerId02, NoteDetailFragment.newInstance()).commit();
        } else {
            fragmentManager.beginTransaction().add(fragContainerId01, NoteDetailFragment.newInstance()).addToBackStack("").commit();
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
            fragmentManager.beginTransaction().replace(fragContainerId02, NoteDetailFragment.newInstance()).commit();
        } else {
            if (note != null)
                fragmentManager.beginTransaction().add(fragContainerId01, NoteDetailFragment.newInstance()).addToBackStack("").commit();
        }

        if (isCalendarSelect) {
            isCalendarSelect = false;
            onDateClick();
        }
    }

    /*@Override
    public void onStopActivity() {
        if (fragmentManager == null) fragmentManager.popBackStack();
        if (isLandscape) {
            List<Fragment> fragments = fragmentManager.getFragments();
            for (Fragment fragment: fragments) {
                if (fragment instanceof NoteDetailFragment) fragmentManager.beginTransaction().remove(fragment);
            }
        }
    }*/

    private void initFragments() {
        if (isLandscape) {
            fragmentManager.beginTransaction().replace(fragContainerId01, NotesFragment.newInstance()).commit();

            fragmentManager.beginTransaction().replace(fragContainerId02, NoteDetailFragment.newInstance()).commit();
        } else {
            fragmentManager.beginTransaction().replace(fragContainerId01, NotesFragment.newInstance()).commit();

            if (note != null)
                fragmentManager.beginTransaction().add(fragContainerId01, NoteDetailFragment.newInstance()).addToBackStack("").commit();
        }
    }

    @Override
    public Note onCreateDetailFragment() {
        if (note != null) {
            return note;
        } else if (!Note.getNotes().isEmpty()) {
            return note = Note.getNotes().get(0);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if (!isLandscape & !isCalendarSelect) {
            note = null;
        }
        isCalendarSelect = false;
        initFragments();
    }

    @Override
    public void onDateClick() {
        if (!isCalendarSelect) {
            isCalendarSelect = true;
            LocalDate dateTime = note.getCreateData();
            int dayOfMonth = dateTime.getDayOfMonth();
            int month = dateTime.getMonthValue();
            int year = dateTime.getYear();
            if (isLandscape) {
                //
                fragmentManager.beginTransaction().add(fragContainerId02, SelectDateFragment.newInstance(dateTime)).addToBackStack("").commit();

            } else {
                //
                fragmentManager.beginTransaction().add(fragContainerId01, SelectDateFragment.newInstance(dateTime)).addToBackStack("").commit();

            }
        }
    }
}
