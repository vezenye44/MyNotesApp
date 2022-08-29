package ru.geekbrains.mynotesapp.model;

public interface DataSource {
    DataSource init(CardsSourceResponse cardsSourceResponse);
    boolean isEmpty();
    int indexOf(Note note);
    void addData(Note note);
    void updateData(int position, Note note);
    Note getData(int position);
    int getDataSize();
    void deleteData(int position);
}
