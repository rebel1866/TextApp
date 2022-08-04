package com.kofax.textapp.view.impl;

import com.kofax.textapp.model.Text;
import com.kofax.textapp.view.TextAppView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private static final String TITLE = "Kofax test task";
    private static final int UP_KEY_CODE = 38;
    private static final int DOWN_KEY_CODE = 40;

    private JScrollPane jScrollPane;

    @Override
    public void viewText(Text text) {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(defaultFrameWidth, defaultFrameHeight);
        jFrame.setLocation(locationX, locationY);
        jFrame.setTitle(TITLE);
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.BLUE);
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setContentType(CONTENT_TYPE);
        jEditorPane.setEditable(false);
        jEditorPane.setText(text.getValue());
        jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(prefSizeWidth, prefSizeHeight));
        createKeyListener(jEditorPane);
        jPanel.add(jScrollPane);
        jFrame.add(jPanel);
        jPanel.revalidate();
    }

    public void createKeyListener(JEditorPane jEditorPane) {
        jEditorPane.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == DOWN_KEY_CODE) {
                    jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getValue() + scrollStep);
                }
                if (e.getKeyCode() == UP_KEY_CODE) {
                    jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getValue() - scrollStep);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

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
