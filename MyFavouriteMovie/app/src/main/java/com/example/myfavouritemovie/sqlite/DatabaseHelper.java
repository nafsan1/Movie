package com.example.myfavouritemovie.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmoviestv";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s INTEGER NOT NULL)",
            DatabaseContracts.TABLE_TV,
            DatabaseContracts.TvColumns._ID,
            DatabaseContracts.TvColumns.ORIGINAL_NAME_TV,
            DatabaseContracts.TvColumns.ORIGINAL_LANGUANE_TV,
            DatabaseContracts.TvColumns.NAME_TV,
            DatabaseContracts.TvColumns.FIRST_AIR_DATE_TV,
            DatabaseContracts.TvColumns.ID_TV,
            DatabaseContracts.TvColumns.VOTE_AVERAGE_TV,
            DatabaseContracts.TvColumns.OVERVIEW_TV,
            DatabaseContracts.TvColumns.POSTER_PATH_TV,
            DatabaseContracts.TvColumns.FAVOURITE_TV,
            DatabaseContracts.TvColumns.TYPE_TV

    );
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s INTEGER NOT NULL)",
            DatabaseContracts.TABLE_MOVIE,
            DatabaseContracts.MovieColumns._ID,
            DatabaseContracts.MovieColumns.VOTE_AVERAGE_MOVIE,
            DatabaseContracts.MovieColumns.TITLE_MOVIE,
            DatabaseContracts.MovieColumns.POPULAR_MOVIE,
            DatabaseContracts.MovieColumns.POSTER_PATH_MOVIE,
            DatabaseContracts.MovieColumns.ORIGINAL_LANGUAGE_MOVIE,
            DatabaseContracts.MovieColumns.ORIGINAL_TITLE_MOVIE,
            DatabaseContracts.MovieColumns.OVERVIEW_MOVIE,
            DatabaseContracts.MovieColumns.RELEASE_MOVIE,
            DatabaseContracts.MovieColumns.ID_MOVIE,
            DatabaseContracts.MovieColumns.FAVOURITE_MOVIE,
            DatabaseContracts.MovieColumns.TYPE_MOVIE

    );
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TV);
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContracts.TABLE_TV);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContracts.TABLE_MOVIE);
    }
}
