package com.example.movieprovider.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.movieprovider.MainActivity;

import static com.example.movieprovider.sqlite.DatabaseContracts.AUTHORITY;
import static com.example.movieprovider.sqlite.DatabaseContracts.MovieColumns.CONTENT_URI_MOVIE;
import static com.example.movieprovider.sqlite.DatabaseContracts.TABLE_MOVIE;

public class MovieProvider extends ContentProvider {
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private TvMovieHelper tvMovieHelper;
    static {
        // content://com.example.providermoviecatalogue/note
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, NOTE);
        // content://com.example.providermoviecatalogue/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", NOTE_ID);
    }
    @Override
    public boolean onCreate() {
        tvMovieHelper = TvMovieHelper.getInstance(getContext());
        return true;
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        tvMovieHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case NOTE:
                cursor = tvMovieHelper.queryProvider();
                break;
            case NOTE_ID:
                cursor = tvMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        tvMovieHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = tvMovieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, new MainActivity.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI_MOVIE + "/" + added);
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        tvMovieHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted = tvMovieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, new MainActivity.DataObserver(new Handler(), getContext()));
        Log.v("MovieDetail1", ""+uri);
        return deleted;
    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        tvMovieHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = tvMovieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, new MainActivity.DataObserver(new Handler(), getContext()));
        return updated;
    }
}
