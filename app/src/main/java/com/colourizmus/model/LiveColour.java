package com.colourizmus.model;

import android.arch.lifecycle.LiveData;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.Log;

import com.colourizmus.utils.Util;

import java.io.Serializable;

/**
 * An implementation of the {@link LiveData} class, made for storing a colour.
 * Intended for the ColourCreation fragments of the {@link com/colourizmus/view/MainActivity.java}.
 * It should allow a more efficient synchronisation of the current value, than the previous Communicator system,
 * due to the Lifecycle awareness of the LiveData type.
 */
public class LiveColour extends LiveData<Integer> implements Serializable {

    //TODO ! might be considerably more resource efficient to find a way to separate the 3 col. channels. (maybe 3 LiveColour instances ?) - it would stop updating unchanged channels.

    //TODO - the setX methods can skip the validation if they are set to take 'byte' as a param,
    //TODO - that will also cut a bit of memory overhead,
    //TODO - on the other hand, that would expose it to throwing an error, or to lossy conversion from int to byte


    //TODO - might make it singleton, tho it is not strictly necessary.
    public LiveColour(){
        //TODO (fu-ft) might store the current colour in shPrefs, and retrieve on app restart.
        //TODO (fu-ft) might make it start as a random colour (possibly supplementary to a random UI colour, which to also make assessable).
        setValue(new Integer(Color.BLACK));
    }



    public int getRed() { return Color.red(getValue()); }
    public int getGreen() { return Color.green(getValue()); }
    public int getBlue() { return Color.blue(getValue()); }

    //TODO - SING_CHANNEL METHODS SEEM TO GIVE WRONG RESULT ON START-UP. TEST 'DA ALPHA OUT OF THEIR ASSES!

    public void setRed(final int red) {
        if (red >= 0 && red < 256)
            setValue(Color.rgb(red, getGreen(), getBlue()));
        else
            Log.e(Util.LOG_TAG_ERR, "LIVE_COLOUR.setRed(int red) -> invalid value passed for red! [" + red + "]");
    }
    public void setGreen(final int green) {
        if (green >= 0 && green < 256)
            setValue(Color.rgb(getRed(), green, getBlue()));
        else
            Log.e(Util.LOG_TAG_ERR, "LIVE_COLOUR.setGreen(int green) -> invalid value passed for red! [" + green + "]");
    }
    public void setBlue(final int blue) {
        if (blue >= 0 && blue < 256)
            setValue(Color.rgb(getRed(), getGreen(), blue));
        else
            Log.e(Util.LOG_TAG_ERR, "LIVE_COLOUR.setBlue(int blue) -> invalid value passed for red! [" + blue + "]");
    }

    @Override
    protected void setValue(Integer value) {
        //TODO validate based on ' int color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 16 | (B & 0xff); '
        super.setValue(value);
    }

    public void setColour(@ColorInt int colour){
        this.setValue(colour);
        Log.e(Util.LOG_TAG_DEV, "setColour: " + colour);
    }
}
