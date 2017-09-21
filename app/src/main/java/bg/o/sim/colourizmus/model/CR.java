package bg.o.sim.colourizmus.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.util.List;

/**
 * This class offered access to true-data in a clean abstracted way to the rest of the application.
 * It handles retrieving and caching data and provides an access-point to the persistence model.
 * <p>
 * note: ColourRepository shortened to CR for general code brevity, as it is oft referenced and always in a static manner.
 */
public final class CR {

    // TODO: 9/21/17 = Check if using a LruCache and observe LiveData list localy! 
    public static final LiveColour LIVE_COLOR = new LiveColour();
    private static ColourDao sColourDao;
    private static LiveData<List<CustomColour>> cachedColours;

    /** sneaky constructor, hidin' from the w'rld **/
    private CR() {}

    public static void init(Application appContext) {
        ColourDatabase dbInstance = Room.databaseBuilder(appContext, ColourDatabase.class, "bg.o.sim.colourizmus").build();
        sColourDao = dbInstance.colourDao();
        cachedColours = sColourDao.getAllColours();
    }

    public static LiveData<List<CustomColour>> getCachedColours() {
        return cachedColours;
    }

    public static void saveColour(final CustomColour colour) {
        new SaveTask().execute(colour);
    }

    public static void setColourFavorite(final CustomColour colour) {
        new UpdateTask().execute(colour);
    }

    private static class UpdateTask extends AsyncTask<CustomColour, Void, Integer> {
        @Override
        protected Integer doInBackground(CustomColour... customColours) {
            return sColourDao.updateColour(customColours[0]);
        }
    }

    private static class SaveTask extends AsyncTask<CustomColour, Void, Long[]> {
        @Override
        protected Long[] doInBackground(CustomColour... customColours) {
            Long[] ids = new Long[customColours.length];

            for (int i = 0; i < customColours.length; i++)
                if (!isCancelled()) ids[i] = sColourDao.insertColour(customColours[i]);

            return ids;
        }
    }
}
