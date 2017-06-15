package com.colourizmus;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public class CustomColour {

    private final @ColorInt int colorInt;

    private String name;

    private boolean isFavourite;

    CustomColour ( String name, int r, int g, int b){
        //TODO !! VALIDATION !!

        this.name = name;
        this.colorInt = Color.rgb(r, g, b);
        this.isFavourite = false;
    }

    public @ColorInt int getColorInt() {return colorInt;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public boolean isFavourite() {return isFavourite;}
    public void setFavourite(boolean favourite) {isFavourite = favourite;}
}
