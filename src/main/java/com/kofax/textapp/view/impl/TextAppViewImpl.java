package com.kofax.textapp.view.impl;

import com.kofax.textapp.model.TextModel;
import com.kofax.textapp.view.TextAppView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

@Component
public class TextAppViewImpl implements TextAppView {
    @Value("${default.width}")
    private int DEFAULT_FRAME_WIDTH;
    @Value("${default.height}")
    private int DEFAULT_FRAME_HEIGHT;
    @Value("${location.x}")
    private int LOCATION_X;
    @Value("${location.y}")
    private int LOCATION_Y;
    @Value("${pref.size.width}")
    private int PREF_SIZE_WIDTH;
    @Value("${pref.size.height}")
    private int PREF_SIZE_HEIGHT;
    private static final String CONTENT_TYPE = "text/html";
    private static final String TITLE = "Kofax test task";

    @Override
    public void viewText(TextModel text) {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        jFrame.setLocation(LOCATION_X, LOCATION_Y);
        jFrame.setTitle(TITLE);
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.BLUE);
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setContentType(CONTENT_TYPE);
        jEditorPane.setEditable(false);
        jEditorPane.setText(text.getValue());
        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(PREF_SIZE_WIDTH, PREF_SIZE_HEIGHT));
        jPanel.add(jScrollPane);
        jFrame.add(jPanel);
        jPanel.revalidate();
    }

    @Override
    public String inputPathToFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter path to file:");
        return scanner.nextLine();
    }
}
