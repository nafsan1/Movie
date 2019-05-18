package com.example.myfavouritemovie.sqlite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContracts {
    static String TABLE_TV = "tabeltv";
    public static String TABLE_MOVIE = "tabelmovie";
    public static final String AUTHORITY = "com.example.movieprovider";

    private DatabaseContracts() {
    }

    static final class TvColumns implements BaseColumns {
        static String ORIGINAL_NAME_TV = "originalName";
        static String ORIGINAL_LANGUANE_TV = "originalLanguage";
        static String NAME_TV = "name";
        static String FIRST_AIR_DATE_TV = "firstAirDate";
        static String ID_TV = "id";
        static String VOTE_AVERAGE_TV = "voteAverage";
        static String OVERVIEW_TV = "overview";
        static String POSTER_PATH_TV = "posterPath";
        static String FAVOURITE_TV = "favourite";
        static String TYPE_TV = "type";
    }

    public static final class MovieColumns implements BaseColumns {
        public static String VOTE_AVERAGE_MOVIE = "voteAverage";
        public static String TITLE_MOVIE = "title";
        public static String POPULAR_MOVIE = "popularity";
        public static String POSTER_PATH_MOVIE = "posterPath";
        public static String ORIGINAL_LANGUAGE_MOVIE = "originalLanguage";
        public static String ORIGINAL_TITLE_MOVIE = "originalTitle";
        public static String OVERVIEW_MOVIE = "overview";
        public static String RELEASE_MOVIE = "releaseDate";
        public static String ID_MOVIE = "id";
        public static String FAVOURITE_MOVIE = "favourite";
        public static String TYPE_MOVIE = "type";

        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
    public static double getColumnDouble(Cursor cursor, String columnName){
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }
}
