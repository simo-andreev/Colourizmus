package bg.o.sim.colourizmus.model;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * This class offered access to true-data in a clean abstracted way to the rest of the application.
 * It handles retrieving and caching data and provides an access-point to the persistence model.
 * <p>
 * note: ColourRepository shortened to CR for general code brevity, as it is oft referenced and always in a static manner.
 */
public abstract class CR {

    //the currently active colour
    public static final LiveColour LIVE_COLOR = new LiveColour();
    private static ColourDatabase sDatabase;
    private static LiveData<List<CustomColour>> cachedColours;


    /**
     * Initialize the Repository by passing a database instance and start s query to pull data into cache
     *
     * @param database
     */
    static void innit(ColourDatabase database) {
        sDatabase = database;
        cachedColours = database.colourDao().getAllColours();
    }


    public static void saveColour(final CustomColour colour) {
        new SaveTask().execute(colour);
    }

    public static void setColourFavorite(final CustomColour colour, boolean isChecked) {
        colour.setIsFavourite(isChecked);
        new UpdateTask().execute(colour);
    }

    public static LiveData<List<CustomColour>> getCachedColours() {
        return cachedColours;
    }

    public static void deleteAllColours() {
        new DeleteTask().execute();
    }

    private static class UpdateTask extends AsyncTask<CustomColour, Void, Integer> {
        @Override
        protected Integer doInBackground(CustomColour... customColours) {
            return sDatabase.colourDao().updateColour(customColours[0]);
        }
    }

    private static class SaveTask extends AsyncTask<CustomColour, Void, Long[]> {
        @Override
        protected Long[] doInBackground(CustomColour... customColours) {
            Long[] ids = new Long[customColours.length];

            for (int i = 0; i < customColours.length; i++)
                if (!isCancelled()) ids[i] = sDatabase.colourDao().insertColour(customColours[i]);

            return ids;
        }
    }

    private static class DeleteTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            sDatabase.colourDao().deleteAllColours();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            cachedColours.getValue().clear();
        }
    }
}
