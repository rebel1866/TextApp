package com.kofax.textapp.view.impl;

import com.kofax.textapp.model.Text;
import com.kofax.textapp.view.abstraction.TextAppView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

@Component
public class TextAppViewImpl implements TextAppView {
    @Value("${default.width}")
    private int defaultFrameWidth;
    @Value("${default.height}")
    private int defaultFrameHeight;
    @Value("${location.x}")
    private int locationX;
    @Value("${location.y}")
    private int locationY;
    @Value("${pref.size.width}")
    private int prefSizeWidth;
    @Value("${pref.size.height}")
    private int prefSizeHeight;
    @Value("${scroll.step}")
    private int scrollStep;
    private static final String CONTENT_TYPE = "text/html";
    private static final String TITLE = "Kofax Test Task";
    private static final int UP_KEY_CODE = 38;
    private static final int DOWN_KEY_CODE = 40;
    private final JFrame jFrame = new JFrame();
    private JScrollPane jScrollPane;
    private final JPanel jPanel = new JPanel();
    private final JEditorPane jEditorPane = new JEditorPane();

    @Override
    public void initGUI() {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(defaultFrameWidth, defaultFrameHeight);
        jFrame.setLocation(locationX, locationY);
        jFrame.setTitle(TITLE);
        jPanel.setBackground(Color.BLUE);
        jEditorPane.setContentType(CONTENT_TYPE);
        jEditorPane.setEditable(false);
        jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(prefSizeWidth, prefSizeHeight));
        createKeyListener(jEditorPane);
        jPanel.add(jScrollPane);
        jFrame.add(jPanel);
    }


    @Override
    public void viewTextWindow(Text text) {
        jFrame.setVisible(true);
        jEditorPane.setText(text.getValue());
        jPanel.revalidate();
    }

    public void createKeyListener(JEditorPane jEditorPane) {
        jEditorPane.addKeyListener(new KeyAdapter() {
            final JScrollBar scrollBar = jScrollPane.getVerticalScrollBar();

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == DOWN_KEY_CODE) {
                    scrollBar.setValue(scrollBar.getValue() + scrollStep);
                }
                if (e.getKeyCode() == UP_KEY_CODE) {
                    scrollBar.setValue(scrollBar.getValue() - scrollStep);
                }
            }
        });
    }

    @Override
    public void printMessage(String message) {
        Logger logger = LogManager.getLogger();
        logger.info("\n" + message);
    }

    @Override
    public String inputFilePath() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
