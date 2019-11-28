package com.chiem.hueapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chiem.hueapplication.Models.Connection;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private ArrayList<Connection> connections = new ArrayList<>();
    private static final String dbName = "HueDatabase";
    private static final int dbVersion = 1;
    private static final String dbTableName = "Connections";
    private static final String CREATE_TABLE_Q = "CREATE TABLE " + dbTableName + "(Name text, Ip text, Port text, IsEmulator Integer, DBKey text)";

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddConnection(Connection connection) {

        int emulator = 0;
        if(connection.isEmulator()) {
            emulator = 1;
        }

        String insertStatement = "INSERT INTO " + dbTableName + " (Name, Ip, Port, IsEmulator, DBKey) " +
                "VALUES ('" + connection.getName() + "', '" + connection.getIp() + "', " +
                "'" + connection.getPort() + "', '" + emulator + "', '" + connection.getKey() + "')";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insertStatement);
    }

    public ArrayList<Connection> GetPrevConnections() {

        String getStatement = "SELECT * FROM " + dbTableName;

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
}
