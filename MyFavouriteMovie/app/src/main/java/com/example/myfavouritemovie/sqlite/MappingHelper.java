package com.example.myfavouritemovie.sqlite;

import android.database.Cursor;

import com.example.myfavouritemovie.model.Movie;


import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ID_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_LANGUAGE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_TITLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.OVERVIEW_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.POPULAR_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.POSTER_PATH_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.RELEASE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.TITLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.VOTE_AVERAGE_MOVIE;


public class MappingHelper {
    public static List<Movie> mapCursorToArrayList(Cursor notesCursor) {
        List<Movie> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(_ID));
            double voteAverage = notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(VOTE_AVERAGE_MOVIE));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE_MOVIE));
            double popularity = notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(POPULAR_MOVIE));
            String posterPath = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTER_PATH_MOVIE));
            String originalLanguage = notesCursor.getString(notesCursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE_MOVIE));
            String originalTitle = notesCursor.getString(notesCursor.getColumnIndexOrThrow(ORIGINAL_TITLE_MOVIE));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW_MOVIE));
            String releaseDate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_MOVIE));
            int id_movie = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID_MOVIE));
            notesList.add(new Movie(voteAverage,title,popularity,posterPath,originalLanguage,originalTitle,overview,releaseDate,id_movie));
        }
        return notesList;
    }
}
