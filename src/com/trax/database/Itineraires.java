package com.trax.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by toor on 14/12/14.
 */
public class Itineraires extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Itineraires";
    private static int DATABASE_VERSION = 2;

    //column
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_NAME = "name";

    private static String DATABASE_CREATE = "create table " + DATABASE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + "text, "
            + COLUMN_IMAGE + " blob);";

    //constructeur
    Itineraires(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //appelé à chaque fois que l'on recrée la BDD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
        return;
    }
}
