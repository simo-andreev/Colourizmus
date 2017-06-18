package com.colourizmus.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.ColorInt;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import static android.arch.persistence.room.OnConflictStrategy.ROLLBACK;

@Dao
public abstract class ColourDao {

    /**
     * Retrieves all the colours stored in the SQLite Db.
     *
     * @return array of all the {@link CustomColour}s stored in the Db.
     */
    @Query("SELECT * FROM " + CustomColour.TABLE)
    public abstract CustomColour[] getAllColours();

    /**
     * Retrieves all coulors marked as 'favourite' from the SQLite Db.
     *
     * @return array of all the colours marked as favourite.
     */
    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_IS_FAVOURITE + " = 1 ")
    public abstract CustomColour[] getFavouriteColours();

    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_PK + " = :value")
    public abstract CustomColour getColour(int value);

    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_NAME + " = :name")
    public abstract CustomColour getColour(String name);

    @Query("SELECT * FROM " + CustomColour.TABLE + " WHERE " + CustomColour.COLUMN_NAME + " LIKE '%' || :name || '%'")
    public abstract CustomColour[] searchForColours(String name);

    /**
     * Insert new items in the SQLite persistence Db.
     *
     * @param colours item(s) which to insert
     * @return array of the RowIds of the newly-created rows.
     */
    @Insert (onConflict = IGNORE) //TODO - consider another onConflict strategy.
    public abstract long[] insertColours(CustomColour ... colours);

    /**
     * Modify content of existing rows.
     *
     * @param colour item which to change.
     * @return number of rows affected.
     */
    @Update (onConflict = REPLACE)
    public abstract int updateColour(CustomColour colour);

    /**
     * Remove passed items from the persistent SQLite storage.
     *
     * @param colours items to remove.
     * @return number of rows affected.
     */
    @Delete public abstract int deleteColours(CustomColour ... colours);


}
