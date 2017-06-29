package com.colourizmus.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.Set;

/**
 * This class offered access to true-data in a clean abstracted way to the rest of the application.
 * It handles retrieving and caching data and provides an access-point to the persistence model.
 */
public abstract class ColourRepository {

    public static final LiveColour LIVE_COLOR = new LiveColour();

    private static InvalidationTracker invalidationTracker;

    private static ColourDatabase dbInstance;
    private static LiveData<List<CustomColour>> cachedColours;


    public static LiveData<List<CustomColour>> getCachedColours() {
        if (dbInstance == null) throw new NullPointerException("The Repo has not been initialized! call ColourRepository#init(Context); !!!");
        return cachedColours;
    }
    public static void saveColour(final CustomColour colour){
        if (dbInstance == null) throw new NullPointerException("The Repo has not been initialized! call ColourRepository#init(Context); !!!");
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                dbInstance.colourDao().insertColours(colour);
                return null;
            }
        }.execute();
    }

    public static void init(Application appContext){
        dbInstance = Room.databaseBuilder(appContext, ColourDatabase.class, "bg.o.sim.colourizmus").build();
        cachedColours = dbInstance.colourDao().getAllColours();

        Log.e("kkkkkkkk","kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");

        //noinspection RestrictedApi
        invalidationTracker = new InvalidationTracker(dbInstance, CustomColour.TABLE);
        invalidationTracker.addObserver(new InvalidationTracker.Observer(CustomColour.TABLE) {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
                cachedColours = dbInstance.colourDao().getAllColours();
            }
        });
    }

    /**
     * TODO - DOCUMENT ME YE' LAZY LANDLUBBER!
     * @param c
     * @param isChecked
     * @return
     */
    public static void setColourFavorite(final CustomColour c, boolean isChecked) {
        c.setIsFavourite(isChecked);
        //TODO - I'm in a bit of hurry so I'll start this in a simple AsyncTask, to avoid UI locks.
        //TODO - This approach might, however, prove problematic if the the 'fav' checkbox is 'spammed' ->
        //TODO - thus starting a bunch of async executions leaving the end result indeterminate!!!

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                dbInstance.colourDao().updateColour(c);

                return null;
            }
        }.execute();

    }
}
