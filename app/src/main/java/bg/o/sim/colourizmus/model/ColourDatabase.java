package bg.o.sim.colourizmus.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = CustomColour.class, version = 1, exportSchema = false)
public abstract class ColourDatabase extends RoomDatabase {
    @SuppressWarnings("UnusedReturnValue") // Canonical Room Db class requires return type.
    protected abstract ColourDao colourDao();
}
