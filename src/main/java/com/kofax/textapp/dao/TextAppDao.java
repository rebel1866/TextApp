package com.kofax.textapp.dao;

import com.kofax.textapp.model.TextModel;

public interface TextAppDao {
    TextModel findText(String path);
}
