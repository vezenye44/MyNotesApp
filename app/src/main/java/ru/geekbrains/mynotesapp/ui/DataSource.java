package ru.geekbrains.mynotesapp.ui;

import ru.geekbrains.mynotesapp.model.Note;

public interface DataSource {
    Note getData(int position);

    int getDataSize();

    void deleteData(int position);
}
