package com.kofax.textapp.logic.impl;


import com.kofax.textapp.dao.TextAppDao;
import com.kofax.textapp.logic.TextAppLogic;
import com.kofax.textapp.model.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TextAppLogicImpl implements TextAppLogic {

    private TextAppDao textAppDao;
    @Value("${big.font.size}")
    private String BIG_FONT_SIZE;
    @Value("${small.font.size}")
    private String SMALL_FONT_SIZE;
    private static final String HTML_OPEN_TAG = "<html>";
    private static final String HTML_CLOSE_TAG = "</html>";
    private static final String LINE_BREAK = "<br>";
    private static final String SPAN_OPEN_TAG = "<span style=\"font-size:%spx\">";
    private static final String SPAN_CLOSE_TAG = "</span>";
    private static final String SPACE = " ";


    @Autowired
    public void setTextAppDao(TextAppDao textAppDao) {
        this.textAppDao = textAppDao;
    }


    @Override
    public Text getFormattedText(String path) {
        String sourceText = textAppDao.findText(path);
        StringBuilder targetText = new StringBuilder();
        targetText.append(HTML_OPEN_TAG);
        sourceText = sourceText.replaceAll("\n", LINE_BREAK);
        List<String> words = Arrays.stream(sourceText.split(SPACE)).toList();
        appendWords(words, targetText);
        targetText.append(HTML_CLOSE_TAG);
        return new Text(targetText.toString());
    }

    public void appendWords(List<String> words, StringBuilder targetText) {
        boolean isLowerFont = true;
        String spanOpenForBigFont = String.format(SPAN_OPEN_TAG, BIG_FONT_SIZE);
        String spanOpenForSmallFont = String.format(SPAN_OPEN_TAG, SMALL_FONT_SIZE);
        for (int i = 0; i < words.size(); i = i + 2) {
            if (isLowerFont) {
                wrapSpan(spanOpenForBigFont, words, i, targetText);
                isLowerFont = false;
            } else {
                wrapSpan(spanOpenForSmallFont, words, i, targetText);
                isLowerFont = true;
            }
            targetText.append(SPACE);
        }
    }

    public void wrapSpan(String spanOpenForSmallFont, List<String> words, int i, StringBuilder targetText) {
        targetText.append(spanOpenForSmallFont);
        targetText.append(words.get(i));
        targetText.append(SPACE);
        if (i + 1 != words.size()) {
            targetText.append(words.get(i + 1));
        }
        targetText.append(SPAN_CLOSE_TAG);
    }
}

