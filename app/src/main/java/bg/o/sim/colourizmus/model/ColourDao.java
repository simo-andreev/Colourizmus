package bg.o.sim.colourizmus.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
abstract class ColourDao {

    /**
     * Retrieves all the colours stored in the SQLite Db.
     *
     * @return array of all the {@link CustomColour}s stored in the Db.
     */
    @Query("SELECT * FROM " + CustomColour.TABLE)
    protected abstract LiveData<List<CustomColour>> getAllColours();

    /**
     * Retrieves all colors marked as 'favourite' from the SQLite Db.
     * @return array of all the colours marked as favourite.
     */
    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_IS_FAVOURITE + " = 1 ")
    protected abstract CustomColour[] getFavouriteColours();


    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_PK + " = :value")
    protected abstract CustomColour getColour(int value);

    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_NAME + " = :name")
    protected abstract CustomColour getColour(String name);

    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_NAME + " LIKE '%' || :name || '%'")
    protected abstract CustomColour[] searchForColours(String name);

    /**
     * Insert new items in the SQLite persistence Db.
     *
     * @param colours item(s) which to insert
     * @return array of the RowIds of the newly-created rows.
     */
    @Insert (onConflict = IGNORE) //TODO - consider another onConflict strategy.
    protected abstract long[] insertColours(CustomColour ... colours);

    /**
     * Insert new items in the SQLite persistence Db.
     *
     * @param colours item(s) which to insert
     * @return array of the RowIds of the newly-created rows.
     */
    @Insert (onConflict = IGNORE) //TODO - consider another onConflict strategy.
    protected abstract long insertColour(CustomColour colours);

    /**
     * Modify content of existing rows.
     *
     * @param colour item which to change.
     * @return number of rows affected.
     */
    @Update (onConflict = REPLACE)
    protected abstract int updateColour(CustomColour colour);

    /**
     * Remove passed items from the persistent SQLite storage.
     *
     * @param colours items to remove.
     * @return number of rows affected.
     */
    @Delete
    protected abstract int deleteColours(CustomColour ... colours);

}
