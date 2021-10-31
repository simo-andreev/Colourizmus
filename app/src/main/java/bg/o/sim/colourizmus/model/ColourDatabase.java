package bg.o.sim.colourizmus.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = CustomColour.class, version = 1, exportSchema = false)
public abstract class ColourDatabase extends RoomDatabase {
    @SuppressWarnings("UnusedReturnValue") // Canonical Room Db class requires return type.
    protected abstract ColourDao colourDao();
}
