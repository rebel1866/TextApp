package com.kofax.textapp.dao.impl;

import com.kofax.textapp.dao.TextAppDao;
import com.kofax.textapp.model.TextModel;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Repository
public class TextAppDaoImpl implements TextAppDao {
    @Override
    public TextModel findText(String path) {
        String data = "";
        try {
            FileInputStream inputStream = new FileInputStream(path);
            data = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.exit(0);
        }
        return new TextModel(data);
    }
}
