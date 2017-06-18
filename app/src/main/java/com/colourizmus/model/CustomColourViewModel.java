package com.colourizmus.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class CustomColourViewModel extends ViewModel{

    LiveData<CustomColour> colour;

    public LiveData<CustomColour> getColour() {
        return colour;
    }
    
}
