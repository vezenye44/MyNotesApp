package ru.geekbrains.mynotesapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import ru.geekbrains.mynotesapp.PresenterImp;
import ru.geekbrains.mynotesapp.R;

public class AddSmartNoteDialogFragment extends DialogFragment {

    public static final String TAG = "DIALOG";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText editText = (EditText) requireActivity().getLayoutInflater().inflate(R.layout.drawer_add_note_dialoge, null);

        return new AlertDialog.Builder(requireActivity())
                .setTitle("Введите сообщение:")
                .setView(editText)
                .setPositiveButton("Создать заметку", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PresenterImp.getInstance(requireActivity()).onClickAddSmartNote(editText.getText().toString());
                    }
                })
                .setNeutralButton("Отменить", null)
                .create();
    }
}
