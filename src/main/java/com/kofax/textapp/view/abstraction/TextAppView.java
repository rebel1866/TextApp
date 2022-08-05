package com.kofax.textapp.view.abstraction;


import com.kofax.textapp.model.Text;

public interface TextAppView {
    void viewTextWindow(Text text);
    String inputFilePath();
    void printMessage(String s);

    void initGUI();
}
