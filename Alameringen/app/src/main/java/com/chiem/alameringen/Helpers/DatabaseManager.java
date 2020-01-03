package com.chiem.alameringen.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chiem.alameringen.Models.Place;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private ArrayList<Place> places;

    private static final String dbName = "AlarmeringenDB";
    private static final int dbVersion = 1;

    private static final String dbTablePlaces = "Places";

    private static final String CREATE_TABLE_P = "CREATE TABLE " + dbTablePlaces + "(Name text)";

    public DatabaseManager(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_P);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Place> getPlaces() {

        places = new ArrayList<>();
        String getStatement = "SELECT * FROM " + dbTablePlaces;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);
        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("Name"));
                places.add(new Place(name, null));
            }
        }

        db.close();

        return places;
    }

    public boolean insertPlace(Place place) {

        if(placeExists(place)) {
            return false;
        }

        String insertStatement = "INSERT INTO " + dbTablePlaces + "(Name) " +
                "VALUES ('" + place.getName() +"')";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insertStatement);
        db.close();

        return true;

    }

    private boolean placeExists(Place place) {

        String getStatement = "SELECT * FROM " + dbTablePlaces;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);
        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext()) {
                if(cursor.getString(cursor.getColumnIndex("Name")).toLowerCase().equals(place.getName().toLowerCase())) {
                    db.close();
                    return true;
                }
            }
        }

        db.close();
        return false;

    }

    public boolean removePlace(Place place) {

        if(placeExists(place)) {

            String insertStatement = "DELETE FROM " + dbTablePlaces +
                    " WHERE Name = ('" + place.getName() +"')";

            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(insertStatement);
            db.close();

            return true;

        }

        return false;

    }
}
