package com.kofax.textapp.controller;

import com.kofax.textapp.logic.TextAppLogic;
import com.kofax.textapp.model.Text;
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
        textAppView.printMessage("Enter path to file:");
        String path = textAppView.inputFilePath();
        Text text = textAppLogic.getFormattedText(path);
        textAppView.viewText(text);
    }
}
