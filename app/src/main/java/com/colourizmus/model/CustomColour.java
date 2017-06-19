package com.colourizmus.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

/**
 * The CustomColour represents a single, named colour that can be marked as favourite and is persisted in on-disk database.
 */
@Entity ( tableName = CustomColour.TABLE, indices = @Index(value = CustomColour.COLUMN_NAME, unique = true))
public class CustomColour {

    @Ignore public static final String TABLE = "colour";
    @Ignore public static final String COLUMN_PK = "value";
    @Ignore public static final String COLUMN_NAME = "name";
    @Ignore public static final String COLUMN_IS_FAVOURITE = "is_favourite";

    @PrimaryKey (autoGenerate = false)
    @ColumnInfo (name = COLUMN_PK)
    private final @ColorInt int value;

    @ColumnInfo (name = COLUMN_NAME)
    private String name;

    @ColumnInfo (name = COLUMN_IS_FAVOURITE)
    private boolean isFavourite;

    protected CustomColour(String name, @ColorInt int value) {
        //TODO !! VALIDATION !!

        this.name = name;
        this.value = value;
        this.isFavourite = false;
    }


    @ColorInt
    protected int getValue() {
        return value;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        if (name != null) //TODO - check for uniqueness?
            this.name = name;
    }

    protected boolean getIsFavourite() {
        return isFavourite;
    }

    protected void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
