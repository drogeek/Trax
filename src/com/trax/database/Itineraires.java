package com.trax.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by toor on 14/12/14.
 */
public class Itineraires extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Itineraires";
    private static String ITINERAIRE_NAME = "Itineraires";
    private static String POINT_NAME = "Points";
    private static int DATABASE_VERSION = 2;

    //column
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_NAME = "name";

    private static final String COLUMN_LAT = "latitude";
    private static final String COLUMN_LNG = "longitude";
    private static final String COLUMN_ITI = "itineraire";

    private static String CREATE_ITINERAIRE = "create table " + ITINERAIRE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + "text, "
            + COLUMN_IMAGE + " blob);";
    private static String CREATE_POINTS = "create table " + POINT_NAME + "(" +
            COLUMN_ID + " sqltime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, " + COLUMN_LAT + "REAL, "
            + COLUMN_LNG + "REAL," + " PRIMARY KEY (" + COLUMN_ID + ", " + COLUMN_ITI + ")" + " FOREIGN KEY( "
            + COLUMN_ID + ") REFERENCES " + ITINERAIRE_NAME + "( " + COLUMN_ID + ");";

    //constructeur
    Itineraires(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //appelé à chaque fois que l'on recrée la BDD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITINERAIRE);
        db.execSQL(CREATE_POINTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
        return;
    }
}
