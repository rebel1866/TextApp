package com.kofax.textapp.logic;


import com.kofax.textapp.model.TextModel;

public interface TextAppLogic {


    TextModel findText(String path);

    TextModel formatText(TextModel textModel);
}
