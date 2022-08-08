package ru.geekbrains.mynotesapp.ui;

import androidx.annotation.IdRes;

public interface FragmentContainer {

    @IdRes
    int getIdResFirstContainer();

    @IdRes
    int getIdResSecondContainer();

}
