package bg.o.sim.colourizmus.model;

import android.arch.lifecycle.LiveData;
import android.graphics.Color;
import android.support.annotation.ColorInt;

import bg.o.sim.colourizmus.view.ColourCreationActivity;

import java.io.Serializable;

/**
 * An implementation of the {@link LiveData} class, made for storing a single colour.
 * Intended for the ColourCreation fragments of the {@link ColourCreationActivity}.
 * It should allow a more efficient synchronisation of the current value, than the previous Communicator system,
 * due to the Lifecycle awareness of the LiveData type.
 */
public class LiveColour extends LiveData<Integer> implements Serializable {

    LiveColour(){
        setValue(0xFF000000);
    }

    public int getRed() { return Color.red(getValue()); }
    public int getGreen() { return Color.green(getValue()); }
    public int getBlue() { return Color.blue(getValue()); }


    public void setRed(final int red) {
        setValue(Color.rgb(red, getGreen(), getBlue()));
    }
    public void setGreen(final int green) {
        setValue(Color.rgb(getRed(), green, getBlue()));
    }
    public void setBlue(final int blue) {
        setValue(Color.rgb(getRed(), getGreen(), blue));
    }

    @Override
    protected void setValue(@ColorInt Integer value) {
        super.setValue(value);
    }
}
