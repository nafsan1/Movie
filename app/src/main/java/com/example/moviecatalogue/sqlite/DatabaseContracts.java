package com.example.moviecatalogue.sqlite;

import android.provider.BaseColumns;

public class DatabaseContracts {
    static String TABLE_TV = "tabeltv";
    static String TABLE_MOVIE = "tabelmovie";
    static final class TvColumns implements BaseColumns{
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

    static final class MovieColumns implements BaseColumns{
        static String VOTE_AVERAGE_MOVIE = "voteAverage";
        static String TITLE_MOVIE = "title";
        static String POPULAR_MOVIE = "popularity";
        static String POSTER_PATH_MOVIE = "posterPath";
        static String ORIGINAL_LANGUAGE_MOVIE = "originalLanguage";
        static String ORIGINAL_TITLE_MOVIE = "originalTitle";
        static String OVERVIEW_MOVIE = "overview";
        static String RELEASE_MOVIE = "releaseDate";
        static String ID_MOVIE = "id";
        static String FAVOURITE_MOVIE = "favourite";
        static String TYPE_MOVIE = "type";
    }
}
