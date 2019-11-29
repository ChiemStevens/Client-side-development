package com.chiem.hueapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private ArrayList<Connection> connections = new ArrayList<>();
    private static final String dbName = "HueDatabase";
    private static final int dbVersion = 4;

    private static final String dbTableConnections = "Connections";
    private static final String dbTablePresets  = "Presets";

    private static final String CREATE_TABLE_C = "CREATE TABLE " + dbTableConnections + "(Name text, Ip text, Port text, IsEmulator Integer, DBKey text)";
    private static final String CREATE_TABLE_P = "CREATE TABLE " + dbTablePresets + "(Name text, Brightness Integer, Hue Integer, Sat Integer)";

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_C);
        db.execSQL(CREATE_TABLE_P);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddConnection(Connection connection) {

        int emulator = 0;
        if(connection.isEmulator()) {
            emulator = 1;
        }

        String insertStatement = "INSERT INTO " + dbTableConnections + " (Name, Ip, Port, IsEmulator, DBKey) " +
                "VALUES ('" + connection.getName() + "', '" + connection.getIp() + "', " +
                "'" + connection.getPort() + "', '" + emulator + "', '" + connection.getKey() + "')";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insertStatement);
    }

    public ArrayList<Connection> GetPrevConnections() {

        String getStatement = "SELECT * FROM " + dbTableConnections;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);
        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext()) {

                boolean isEmualtor = true;
                if(cursor.getInt(cursor.getColumnIndex("IsEmulator")) == 0) {
                    isEmualtor = false;
                }

                this.connections.add(new Connection(cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getString(cursor.getColumnIndex("Ip")),
                        cursor.getString(cursor.getColumnIndex("Port")),
                        isEmualtor,
                        cursor.getString(cursor.getColumnIndex("DBKey"))));
            }
        }


        return this.connections;
    }

    private boolean checkIfPresetExists(String name) {

        String getStatement = "SELECT * FROM " + dbTablePresets + " WHERE Name = '" + name + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(getStatement, null);

        if(cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }

    public boolean addPreset(String name, Light light) {

        boolean exsits = checkIfPresetExists(name);
        if(!exsits) {

            String statement = "INSERT INTO " + dbTablePresets + " (Name, Brightness, Hue, Sat) " +
                    "VALUES('" + name + "', '" + light.getLightState().getBri() + "', '" + light.getLightState().getHue() + "', " +
                    "'" + light.getLightState().getSat() + "')";

            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(statement);
        }

        return exsits;

    }

    public ArrayList<Light> getPresets() {

        String getStatement = "SELECT * FROM " + dbTablePresets;
        ArrayList<Light> lights = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(getStatement, null);
        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext()) {

                lights.add(new Light(cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getInt(cursor.getColumnIndex("Brightness")),
                        cursor.getInt(cursor.getColumnIndex("Hue")),
                        cursor.getInt(cursor.getColumnIndex("Sat"))));
            }
        }


        return lights;

    }
}
