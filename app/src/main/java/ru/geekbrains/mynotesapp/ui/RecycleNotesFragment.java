package ru.geekbrains.mynotesapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import ru.geekbrains.mynotesapp.Presenter;
import ru.geekbrains.mynotesapp.PresenterImp;
import ru.geekbrains.mynotesapp.R;
import ru.geekbrains.mynotesapp.model.Note;


public class RecycleNotesFragment extends Fragment {

    Presenter presenter;
    MyAdapter adapter;

    public RecycleNotesFragment() {
        // Required empty public constructor
    }

    public static RecycleNotesFragment newInstance() {
        return new RecycleNotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        presenter = PresenterImp.getInstance(requireActivity());
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycle_notes, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view);

        initRecycleView(recyclerView);

        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.add_note_btn);

        floatingActionButton.setOnClickListener(view -> presenter.onClickAddButton());

        registerForContextMenu(recyclerView);

        return rootView;
    }

    private void initRecycleView(RecyclerView recyclerView) {
        DataSource dataSource = new DataSource() {
            @Override
            public Note getData(int position) {
                return Note.getNotes().get(position);
            }

            @Override
            public int getDataSize() {
                return Note.getNotes().size();
            }

            @Override
            public void deleteData(int position) {
                Note.getNotes().remove(position);
            }


        };

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new MyAdapter(dataSource, this);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener((view, position) -> {
            Note note = dataSource.getData(position);
            presenter.onClickNotesItem(note);
        });

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.clear();
        requireActivity().getMenuInflater().inflate(R.menu.notes_popup_menu, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.context_menu_delete) {
            presenter.onClickDeleteNote(adapter.getItemPosition());
            return true;
        }
        if (item.getItemId() == R.id.context_menu_modify) {
            presenter.onClickNotesItem(Note.getNotes().get(adapter.getItemPosition()));
            return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_list_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_menu_addWithTitle:
                presenter.onClickAddSmartNoteBtn();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteItem(int position) {
        View rootView = requireView();
        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view);

        ((MyAdapter) recyclerView.getAdapter()).deleteItemByPosition(position);
    }

    public void updateItem(int position) {
        View rootView = requireView();
        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view);

        ((MyAdapter) recyclerView.getAdapter()).updateItemByPosition(position);
    }

}