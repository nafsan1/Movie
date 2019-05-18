package com.example.myfavouritemovie.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.myfavouritemovie.model.Movie;
import com.example.myfavouritemovie.model.Tv;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.myfavouritemovie.MainActivity.TYPE_TV_INTENT;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.FAVOURITE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ID_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_LANGUAGE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_TITLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.OVERVIEW_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.POPULAR_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.POSTER_PATH_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.RELEASE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.TITLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.TYPE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.VOTE_AVERAGE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TABLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TABLE_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.FAVOURITE_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.FIRST_AIR_DATE_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.ID_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.NAME_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.ORIGINAL_LANGUANE_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.ORIGINAL_NAME_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.OVERVIEW_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.POSTER_PATH_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.TYPE_TV;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TvColumns.VOTE_AVERAGE_TV;

public class TvMovieHelper {

    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper databaseHelper;
    private static TvMovieHelper INSTANCE;
    private static SQLiteDatabase database;

    public TvMovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor getDataTv(int id) {
        String sql = "SELECT * FROM " + TABLE_TV + " WHERE " + ID_TV + " ='" + id + "'";
        return database.rawQuery(sql, null);

    }

    public Cursor getDataMovie(int id) {
        String sql = "SELECT * FROM " + TABLE_MOVIE + " WHERE " + ID_MOVIE + " ='" + id + "'";
        return database.rawQuery(sql, null);

    }

    public List<String> getAllPosterMovie() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT "+POSTER_PATH_MOVIE+" FROM " + TABLE_MOVIE+" Union ALL SELECT "+POSTER_PATH_TV +" FROM "+TABLE_TV;
        Cursor c = database.rawQuery(sql, null);

        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                String poster_path = c.getString(c.getColumnIndex(POSTER_PATH_MOVIE));
                list.add(poster_path);
                c.moveToNext();
            }
            while (!c.isAfterLast());
        }
        c.close();
        return list;
    }

    public List<Movie> getListMovie() {
        List<Movie> list = new ArrayList<>();
        Cursor cursor = database.query(TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie m;
        if (cursor.getCount() > 0) {
            do {
                m = new Movie();
                m.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE_MOVIE)));
                m.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_MOVIE)));
                m.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(POPULAR_MOVIE)));
                m.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH_MOVIE)));
                m.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE_MOVIE)));
                m.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_TITLE_MOVIE)));
                m.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_MOVIE)));
                m.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_MOVIE)));
                m.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                list.add(m);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(VOTE_AVERAGE_MOVIE, movie.getVoteAverage());
        args.put(TITLE_MOVIE, movie.getTitle());
        args.put(POPULAR_MOVIE, movie.getPopularity());
        args.put(POSTER_PATH_MOVIE, movie.getPosterPath());
        args.put(ORIGINAL_LANGUAGE_MOVIE, movie.getOriginalLanguage());
        args.put(ORIGINAL_TITLE_MOVIE, movie.getOriginalTitle());
        args.put(OVERVIEW_MOVIE, movie.getOverview());
        args.put(RELEASE_MOVIE, movie.getReleaseDate());
        args.put(ID_MOVIE, movie.getId());
        args.put(FAVOURITE_MOVIE, "yes");
        args.put(TYPE_MOVIE, TYPE_TV_INTENT);
        return database.insert(TABLE_MOVIE, null, args);
    }


    public List<Tv> getListTv() {
        List<Tv> list = new ArrayList<>();
        Cursor cursor = database.query(TABLE_TV, null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
                , null);
        cursor.moveToFirst();
        Tv tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new Tv();
                tv.setOriginalName(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_NAME_TV)));
                tv.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUANE_TV)));
                tv.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME_TV)));
                tv.setFirstAirDate(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE_TV)));
                tv.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_TV)));
                tv.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE_TV)));
                tv.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_TV)));
                tv.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH_TV)));
                list.add(tv);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public long insertTv(Tv tv) {
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_NAME_TV, tv.getOriginalName());
        args.put(ORIGINAL_LANGUANE_TV, tv.getOriginalLanguage());
        args.put(NAME_TV, tv.getName());
        args.put(FIRST_AIR_DATE_TV, tv.getFirstAirDate());
        args.put(ID_TV, tv.getId());
        args.put(VOTE_AVERAGE_TV, tv.getVoteAverage());
        args.put(OVERVIEW_TV, tv.getOverview());
        args.put(POSTER_PATH_TV, tv.getPosterPath());
        args.put(FAVOURITE_TV, "yes");
        args.put(TYPE_TV, TYPE_TV_INTENT);
        return database.insert(TABLE_TV, null, args);
    }

    public int deleteTv(int id) {
        return database.delete(TABLE_TV, ID_TV + " ='"
                + id
                + "'", null);
    }

    public int deleteMovie(int id) {
        return database.delete(TABLE_MOVIE, ID_MOVIE + " ='"
                + id
                + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(TABLE_MOVIE, null
                , ID_MOVIE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , ID_MOVIE + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_MOVIE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_MOVIE, values, ID_MOVIE + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_MOVIE, ID_MOVIE + " = ?", new String[]{id});
    }
}
