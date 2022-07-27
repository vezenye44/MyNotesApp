package ru.geekbrains.mynotesapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.geekbrains.mynotesapp.Presenter;
import ru.geekbrains.mynotesapp.PresenterImp;
import ru.geekbrains.mynotesapp.R;

public class MainActivity extends AppCompatActivity implements FragmentContainer {

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = PresenterImp.getInstance(this);
        if (savedInstanceState == null) {
            presenter.onFirstCreateActivity(this);
        } else {
            presenter.onCreateActivity(this);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (presenter != null) presenter.onBackPressed();
    }

    @Override
    public int getIdResFirstContainer() {
        return R.id.fragment_container;
    }

    @Override
    public int getIdResSecondContainer() {
        return R.id.fragment_container_v2;
    }
}