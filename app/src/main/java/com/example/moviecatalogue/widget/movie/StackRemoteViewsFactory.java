package com.example.moviecatalogue.widget.movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.activity.DetailActivity;
import com.example.moviecatalogue.api.BaseApiService;
import com.example.moviecatalogue.api.UtilsAPI;
import com.example.moviecatalogue.fragment.movie.Movie;
import com.example.moviecatalogue.fragment.movie.MovieDataResponse;
import com.example.moviecatalogue.sqlite.TvMovieHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<String> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private TvMovieHelper tvMovieHelper;


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        tvMovieHelper = TvMovieHelper.getInstance(mContext.getApplicationContext());
        tvMovieHelper.open();
        mWidgetItems = tvMovieHelper.getAllPosterMovie();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_item);

        rv.setImageViewBitmap(R.id.imageView, getBitmapFromURL("https://image.tmdb.org/t/p/w342" +
                mWidgetItems.get(position)));

        Bundle extras = new Bundle();
        extras.putInt(MovieAppWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    public static Bitmap getBitmapFromURL(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.e("Widget", e.getMessage());
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
