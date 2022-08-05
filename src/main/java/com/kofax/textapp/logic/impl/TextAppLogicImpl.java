package com.kofax.textapp.logic.impl;


import com.kofax.textapp.dao.abstraction.TextAppDao;
import com.kofax.textapp.logic.abstraction.TextAppLogic;
import com.kofax.textapp.model.Text;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

@Service
public class TextAppLogicImpl implements TextAppLogic {

    private TextAppDao textAppDao;
    @Value("${big.font.size}")
    private String bigFontSize;
    @Value("${small.font.size}")
    private String smallFontSize;
    @Value("${font}")
    private String font;
    @Value("${pref.size.width}")
    private int prefSizeWidth;

    private static final String HTML_OPEN_TAG = "<html>";
    private static final String HTML_CLOSE_TAG = "</html>";
    private static final String LINE_BREAK = "<br>";
    private static final String SPAN_OPEN_TAG = "<span style=\"font-size:%spx; font-family:%s\">";
    private static final String SPAN_CLOSE_TAG = "</span>";
    private static final String SPACE = " ";

    private double lineWidth;


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
        appendWordsList(words, targetText);
        targetText.append(HTML_CLOSE_TAG);
        return new Text(targetText.toString());
    }

    public void appendWordsList(List<String> words, StringBuilder targetText) {
        boolean isLowerFont = true;
        double coefFrameWidthProportion = 1.4;
        lineWidth = prefSizeWidth / coefFrameWidthProportion;
        for (int i = 0; i < words.size(); i = i + 2) {
            if (isLowerFont) {
                wrapSpan(bigFontSize, words, i, targetText);
                isLowerFont = false;
            } else {
                wrapSpan(smallFontSize, words, i, targetText);
                isLowerFont = true;
            }
            targetText.append(SPACE);
        }
    }

    public void wrapSpan(String fontSize, List<String> words, int i, StringBuilder targetText) {
        String spanOpen = String.format(SPAN_OPEN_TAG, fontSize, font);
        int size = Integer.parseInt(fontSize);
        targetText.append(spanOpen);
        appendWord(targetText, words.get(i), size);
        targetText.append(SPACE);
        if (i + 1 != words.size()) {
            appendWord(targetText, words.get(i + 1), size);
        }
        targetText.append(SPAN_CLOSE_TAG);
    }

    public void appendWord(StringBuilder targetText, String word, int size) {
        if (calculateStringPixels(word, size, font) < lineWidth) {
            targetText.append(word);
        } else {
            ArrayDeque<String> deque = new ArrayDeque<>();
            deque.add(word);
            deque = divideLongWordIntoParts(deque, size);
            for (String element : deque) {
                targetText.append(element);
                targetText.append(SPACE);
            }
        }
    }

    public ArrayDeque<String> divideLongWordIntoParts(ArrayDeque<String> deque, int fontSize) {
        String last = deque.getLast();
        double lastElementPixLength = calculateStringPixels(last, fontSize, font);
        if (lastElementPixLength < lineWidth) {
            return deque;
        }
        String lastRemoved = deque.removeLast();
        SplittedString splittedString = splitSourceString(lastRemoved, fontSize, font);
        deque.addLast(splittedString.source);
        deque.addLast(splittedString.rest);
        return divideLongWordIntoParts(deque, fontSize);
    }


    public double calculateStringPixels(String text, int size, String fontName) {
        FontRenderContext fontRenderContext = new FontRenderContext(new AffineTransform(), true, true);
        Font font = new Font(fontName, Font.PLAIN, size);
        Rectangle2D rectangle2D = font.getStringBounds(text, fontRenderContext);
        return rectangle2D.getWidth();
    }

    public SplittedString splitSourceString(String source, int size, String fontName) {
        for (int i = 0; i < source.length(); i++) {
            String firstPart = source.substring(0, i);
            if (calculateStringPixels(firstPart, size, fontName) > lineWidth) {
                return new SplittedString(firstPart, source.substring(i));
            }
        }
        return new SplittedString("", "");
    }

    @AllArgsConstructor
    @NoArgsConstructor
    static class SplittedString {
        private String source;
        private String rest;
    }
}

