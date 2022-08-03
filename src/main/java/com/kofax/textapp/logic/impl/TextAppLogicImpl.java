package com.kofax.textapp.logic.impl;


import com.kofax.textapp.dao.TextAppDao;
import com.kofax.textapp.logic.TextAppLogic;
import com.kofax.textapp.model.TextModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TextAppLogicImpl implements TextAppLogic {

    private TextAppDao textAppDao;

    @Autowired
    public void setTextAppDao(TextAppDao textAppDao) {
        this.textAppDao = textAppDao;
    }

    @Override
    public TextModel findText(String pathToFile) {
        return textAppDao.findText(pathToFile);
    }

    @Override
    public TextModel formatText(TextModel text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        String sourceText = text.getValue();
        sourceText = sourceText.replaceAll("\n", "<br>");
        List<String> list = Arrays.stream(sourceText.split(" ")).toList();
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                builder.append("<span style=\"font-size:18px\">");
                builder.append(list.get(i));
                builder.append("</span>");
                builder.append(" ");
            } else {
                builder.append(list.get(i));
                builder.append(" ");
            }
        }
        builder.append("</html>");
        return new TextModel(builder.toString());
    }
}

