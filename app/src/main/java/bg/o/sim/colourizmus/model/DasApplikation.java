package bg.o.sim.colourizmus.model;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.support.annotation.Nullable;

import java.util.List;

import bg.o.sim.colourizmus.utils.Util;

/**
 * Created by simeon on 9/24/17.
 */

public class DasApplikation extends Application {

    // Called when the application is starting, before any other application objects have been created.
    @Override
    public void onCreate() {
        super.onCreate();
        ColourDatabase database = Room.databaseBuilder(this, ColourDatabase.class, Util.DB_NAME).build();
        CR.innit(database);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // TODO: 24-09-17 - might be reasonable to clear cache until requested a-new
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
