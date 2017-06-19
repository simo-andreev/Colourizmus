package com.colourizmus.model;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;

import java.util.List;

/**
 * This class offered access to true-data in a clean abstracted way to the rest of the application.
 * It handles retrieving and caching data and provides an access-point to the persistence model.
 */
public abstract class ColourRepository {

    /**A single  */
    public static final LiveColour LIVE_COLOR = new LiveColour();

                                                                          //TODO \/ this bit just feels wrong m8//
    private static final ColourDatabase dbInstance = Room.databaseBuilder((new Activity()).getApplicationContext(), ColourDatabase.class, "bg.o.sim.colourizmus.db").build();;
    private static final LiveData<List<CustomColour>> cachedColours = getPersistedColours();


    private static LiveData<List<CustomColour>> getPersistedColours() {
        return dbInstance.colourDao().getAllColours();
    }


    public static LiveData<List<CustomColour>> getCachedColours() {
        return cachedColours;
    }

    public static long saveColour(CustomColour colour){
        return dbInstance.colourDao().insertColours(colour)[0];
    }
}
