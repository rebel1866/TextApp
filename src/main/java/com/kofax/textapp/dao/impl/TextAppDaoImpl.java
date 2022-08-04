package com.kofax.textapp.dao.impl;

import com.kofax.textapp.dao.TextAppDao;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Repository
public class TextAppDaoImpl implements TextAppDao {
    @Override
    public String findText(String path) {
        String data = "";
        try {
            FileInputStream inputStream = new FileInputStream(path);
            data = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Logger logger = LogManager.getLogger();
            String message = String.format("Error has occurred. \nCan not find the file on given path: %s " +
                    "\nApplication will be stopped now.", path);
            logger.error(message);
            System.exit(0);
        }
        return data;
    }
}
