package com.colourizmus.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

@Entity ( tableName = CustomColour.TABLE, indices = @Index(value = CustomColour.COLUMN_NAME, unique = true))
public class CustomColour {

    @Ignore public static final String TABLE = "colour";
    @Ignore public static final String COLUMN_PK = "value";
    @Ignore public static final String COLUMN_NAME = "name";
    @Ignore public static final String COLUMN_IS_FAVOURITE = "is_favourite";

    @PrimaryKey (autoGenerate = false)
    @ColumnInfo ( name = COLUMN_PK)
    private final @ColorInt int value;

    @ColumnInfo ( name = COLUMN_NAME )
    private String name;

    @ColumnInfo ( name = COLUMN_IS_FAVOURITE)
    private boolean isFavourite;

    protected CustomColour(String name, @ColorInt int value) {
        //TODO !! VALIDATION !!

        this.name = name;
        this.value = value;
        this.isFavourite = false;
    }

    public
    @ColorInt
    int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) //TODO - check for uniqueness?
            this.name = name;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
