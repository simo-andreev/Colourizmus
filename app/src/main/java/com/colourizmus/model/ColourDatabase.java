package com.colourizmus.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = CustomColour.class, version = 1, exportSchema = false)
public abstract class ColourDatabase extends RoomDatabase {
    protected abstract ColourDao colourDao();
}
