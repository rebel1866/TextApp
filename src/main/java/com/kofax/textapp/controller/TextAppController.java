package com.kofax.textapp.controller;

import com.kofax.textapp.logic.TextAppLogic;
import com.kofax.textapp.model.TextModel;
import com.kofax.textapp.view.TextAppView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TextAppController {
    private TextAppLogic textAppLogic;
    private TextAppView textAppView;

    @Autowired
    public void setTextAppLogic(TextAppLogic textAppLogic) {
        this.textAppLogic = textAppLogic;
    }

    @Autowired
    public void setTextAppView(TextAppView textAppView) {
        this.textAppView = textAppView;
    }

    public void initApp() {
        String path = textAppView.inputPathToFile();
        TextModel textModel = textAppLogic.findText(path);
        TextModel formattedTextModel = textAppLogic.formatText(textModel);
        textAppView.viewText(formattedTextModel);
    }
}
