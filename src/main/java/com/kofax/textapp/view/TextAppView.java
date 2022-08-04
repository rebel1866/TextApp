package com.kofax.textapp.view;


import com.kofax.textapp.model.Text;

public interface TextAppView {
    void viewText(Text text);

    String inputFilePath();

    void printMessage(String s);
}
