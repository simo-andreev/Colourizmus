package com.colourizmus.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.colourizmus.R;

/**
 * Created by simeon on 3/27/17.
 */

public class ColourDbAdapter {

    private static final long FAILED_INSERT = -1;

    //TODO - not here per se, but implement a system whereas  user can add an existing colour to favs, but should be prompted that it already exists first. [maybe keep a String array of names, in case of multple]
    private static final String DB_NAME = "colorizmus-database";
    private static final int DB_VERSION = 1;

    private static final String TABLE_FAVOURITES = "FAVOURITE_COLOURS";
    private static final String FAVOURITES_PRIMARY_KEY = "_id";
    private static final String FAVOURITES_COLUMN_NAME = "name";
    private static final String FAVOURITES_CREATE_TABLE = "CREATE TABLE " + TABLE_FAVOURITES + " ( " + FAVOURITES_PRIMARY_KEY + " INTEGER PRIMARY KEY, " + FAVOURITES_COLUMN_NAME + " VARCHAR(64));";

    private Context c;
    private ColourDbHelper dbHelper;

    public ColourDbAdapter(Context context) {
        if (context != null) {
            this.c = context;
            this.dbHelper = new ColourDbHelper(context);
        }
    }

    public boolean addCoulourEntry(int colourInt, String name){
        if (colourInt < 1 || name == null || name.length() < 4)
            return false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FAVOURITES_PRIMARY_KEY, colourInt);
        values.put(FAVOURITES_COLUMN_NAME, name);

        long id;

        try {
            id = db.insertOrThrow(TABLE_FAVOURITES,null,values);
        }catch (SQLiteException e){
            id = FAILED_INSERT;
            //TODO - get Colour name from favs and add at end of Toast.
            Toast.makeText(c, c.getString(R.string.err_already_favourite) + getSingleFavouriteColour(colourInt), Toast.LENGTH_LONG).show();
        }

        //TODO - temp : test strctre
        Log.e(id + " ",colourInt + " " + name);
        return id == FAILED_INSERT;
    }
    public boolean addColourEntry(int r, int g, int b, String name){
        if (r < 1 || g < 1 || b < 1)
            return false;
        //return addColourEntry(int);
        return false;
    }


    public void getAllFavouriteColours(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVOURITES,null,null,null,null,null,FAVOURITES_COLUMN_NAME);

        int nameColumn = cursor.getColumnIndex(FAVOURITES_COLUMN_NAME);
        int valueColumn = cursor.getColumnIndex(FAVOURITES_PRIMARY_KEY);

        while (cursor.moveToNext()){
            int value = cursor.getInt(valueColumn);
            String name = cursor.getString(nameColumn);
            if (name == null )
                name = "I AM A NULL VALUE!!! You've fucked-up somewhere bruh...";
            Toast.makeText(c,"value: " + value + ", name: " + name,Toast.LENGTH_SHORT).show();
        }

    }

    public String getSingleFavouriteColour(int colourInt){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVOURITES,null,FAVOURITES_PRIMARY_KEY + " = " + colourInt,null,null,null,null);
        if(!cursor.moveToNext()) {
            Toast.makeText(c, "Something went wrong capt'n! We've got " + cursor.getCount() + " items on da ship!", Toast.LENGTH_SHORT).show();
            return "I'M HERE TO SPARE A NULL-CKECK ON THE OTHER END OF MY METHOD. BYE.";
        }
        return cursor.getString(cursor.getColumnIndex(FAVOURITES_COLUMN_NAME)) + "!";
    }






    class ColourDbHelper extends SQLiteOpenHelper {

        private ColourDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            //throws SQLException if String is inadequate. Maybe should be try-caught, but for now I'll leave it optimistically thinking 'I *tataly* ain't EVER gonna write a String that doesn't work!!!'
            db.execSQL(FAVOURITES_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    }
}
