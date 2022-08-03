package com.kofax.textapp.view;


import com.kofax.textapp.model.TextModel;

public interface TextAppView {
    void viewText(TextModel text);

    String inputPathToFile();
}
