package ru.geekbrains.mynotesapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ru.geekbrains.mynotesapp.Presenter;
import ru.geekbrains.mynotesapp.PresenterImp;
import ru.geekbrains.mynotesapp.R;
import ru.geekbrains.mynotesapp.model.Note;


public class NoteDetailFragment extends Fragment {

    Presenter presenter;
    boolean isFirstCreated;

    public static NoteDetailFragment newInstance() {
        NoteDetailFragment fragment = new NoteDetailFragment();
        return fragment;
    }

    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SAVED_NOTE", note);
        fragment.setArguments(bundle);
        return fragment;
    }

    public NoteDetailFragment() {
    }

    int key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();

        presenter = PresenterImp.getInstance(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_detail, container, false);

        setHasOptionsMenu(true);

        //Note note = presenter.onCreateDetailFragment();
        Note note = getArguments().getParcelable("SAVED_NOTE");

        EditText title = rootView.findViewById(R.id.title_of_note_view);
        TextView date = rootView.findViewById(R.id.date_of_note_view);
        EditText text = rootView.findViewById(R.id.text_of_note_view);

        if (note != null) {
            title.setText(note.getTitleOfNote());
            date.setText(note.getCreateData().toString());
            text.setText(note.getTextOfNote());
        }

        if (savedInstanceState == null) {

            title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (presenter != null) presenter.updateTitle(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (presenter != null) presenter.updatedTitle();
                }
            });

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (presenter != null) presenter.onDateClick();
                }
            });

            text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (presenter != null) presenter.updateText(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (presenter != null) presenter.updatedText();
                }
            });
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_detail_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                presenter.onClickDeleteNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}