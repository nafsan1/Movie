package com.example.moviecatalogue.sqlite;

import android.database.Cursor;

import com.example.moviecatalogue.fragment.movie.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.FAVOURITE_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.ID_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_LANGUAGE_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_TITLE_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.OVERVIEW_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.POPULAR_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.POSTER_PATH_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.RELEASE_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.TITLE_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.TYPE_MOVIE;
import static com.example.moviecatalogue.sqlite.DatabaseContracts.MovieColumns.VOTE_AVERAGE_MOVIE;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor movieCursor){
        ArrayList<Movie> movieList = new ArrayList<>();
        while (movieCursor.moveToNext()){
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(_ID));
            double voteAverage = movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(VOTE_AVERAGE_MOVIE));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE_MOVIE));
            double popularity = movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(POPULAR_MOVIE));
            String posterPath = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTER_PATH_MOVIE));
            String originalLanguage = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE_MOVIE));
            String originalTitle = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ORIGINAL_TITLE_MOVIE));
            String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(OVERVIEW_MOVIE));
            String releaseDate = movieCursor.getString(movieCursor.getColumnIndexOrThrow(RELEASE_MOVIE));
            int id_movie = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(ID_MOVIE));
            movieList.add(new Movie(voteAverage,title,popularity,posterPath,originalLanguage,originalTitle,overview,releaseDate,id_movie));

        }
        return movieList;
    }
}
