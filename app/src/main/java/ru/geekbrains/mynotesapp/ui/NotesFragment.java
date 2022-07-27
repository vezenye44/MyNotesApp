package ru.geekbrains.mynotesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import ru.geekbrains.mynotesapp.Presenter;
import ru.geekbrains.mynotesapp.PresenterImp;
import ru.geekbrains.mynotesapp.R;
import ru.geekbrains.mynotesapp.model.Note;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    Presenter presenter;

    public NotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterImp.getInstance(requireActivity());
    }

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        ListView listView = rootView.findViewById(R.id.notes_container);

        ArrayList<Note> notes = Note.getNotes();

        ArrayAdapter<Note> adapter = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, notes);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = (Note) adapterView.getItemAtPosition(i);
                PresenterImp.getInstance(requireActivity()).onClickNotesItem(note);
            }
        });

        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.add_note_btn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickAddButton();
            }
        });

        return rootView;
    }


}