package ru.geekbrains.mynotesapp.ui;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ru.geekbrains.mynotesapp.Presenter;
import ru.geekbrains.mynotesapp.PresenterImp;
import ru.geekbrains.mynotesapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentContainer {

    private Presenter presenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_navigation);

        ListView listView = findViewById(R.id.drawer_list);
        ArrayList<String> list = new ArrayList<>();
        list.add("All");
        list.add("Today");
        list.add("...");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: реализовать выбор элемента
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close){

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(getTitle());
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Group notes");
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


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