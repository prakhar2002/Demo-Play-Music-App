package com.example.demomusicplay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context,Record.TAG, null, Record.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  String.format
                ("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT,%s TEXT);",
                        Record.RecordTask.Table,Record.RecordTask.COL2,Record.RecordTask.COL3,Record.RecordTask.COL4,Record.RecordTask.COL0);

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query =String.format("DROP TABLE IF EXISTS %s",Record.RecordTask.Table ) ;
        db.execSQL(query);
        onCreate(db);

    }
}
