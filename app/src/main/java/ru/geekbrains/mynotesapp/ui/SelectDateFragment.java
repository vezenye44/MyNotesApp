package ru.geekbrains.mynotesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import ru.geekbrains.mynotesapp.PresenterImp;
import ru.geekbrains.mynotesapp.R;

import java.time.LocalDate;
import java.util.Date;


public class SelectDateFragment extends Fragment {

    public SelectDateFragment() {
    }

    private static final String DAY_OF_MONTH = "dayOfMonth";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private int dayOfMonth;
    private int month;
    private int year;

    public static SelectDateFragment newInstance(Date date) {
        SelectDateFragment fragment = new SelectDateFragment();
        Bundle args = new Bundle();
        args.putInt(DAY_OF_MONTH, date.getDay());
        args.putInt(MONTH, date.getMonth());
        args.putInt(YEAR, date.getYear());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayOfMonth = getArguments().getInt(DAY_OF_MONTH, 1);
            month = getArguments().getInt(MONTH, 1);
            year = getArguments().getInt(YEAR, 2022);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_date, container, false);

        FrameLayout datePickerContainer = rootView.findViewById(R.id.date_picker_container);
        DatePicker datePicker = new DatePicker(requireActivity());
        datePicker.updateDate(year, month, dayOfMonth);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                PresenterImp.getInstance(requireActivity()).updateDate(i2, i1, i);
                PresenterImp.getInstance(requireActivity()).updatedDate();
            }
        });

        datePickerContainer.addView(datePicker);

        return rootView;
    }
}