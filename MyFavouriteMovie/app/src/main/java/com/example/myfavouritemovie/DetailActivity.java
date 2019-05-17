package com.example.myfavouritemovie;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.myfavouritemovie.model.Movie;
import com.example.myfavouritemovie.model.Tv;
import com.example.myfavouritemovie.sqlite.TvMovieHelper;

import static com.example.myfavouritemovie.MainActivity.EXTRA_MOVIES;
import static com.example.myfavouritemovie.MainActivity.TYPE_MOVIE_INTENT;
import static com.example.myfavouritemovie.MainActivity.TYPE_TV_INTENT;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.CONTENT_URI_MOVIE;
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


@SuppressWarnings("ALL")
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Movie m;
    private Tv tv;
    private int code;
    private Menu menu;
    private TvMovieHelper tvMovieHelper;
    private ImageButton image_fav;
    private ImageView img_movie_poster;
    private TextView txt_movie_name, txt_movie_date, txt_movie_overview, txt_vote;
    private Uri uri;
    private ContentResolver resolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ProgressBar progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setVisibility(View.VISIBLE);
        txt_movie_name = findViewById(R.id.txt_movie_name);
        txt_movie_date = findViewById(R.id.txt_movie_date);
        txt_movie_overview = findViewById(R.id.txt_movie_overview);
        txt_vote = findViewById(R.id.txt_vote);
        img_movie_poster = findViewById(R.id.img_movie_poster);
        image_fav = findViewById(R.id.image_fav);
        image_fav.bringToFront();
        tvMovieHelper = TvMovieHelper.getInstance(getApplicationContext());
        tvMovieHelper.open();
        resolver = getContentResolver();
        code = getIntent().getIntExtra("code", 0);
        initToolbar();

        if (code == TYPE_MOVIE_INTENT) {
            setDataMovie();
        } else if (code == TYPE_TV_INTENT) {
            setDataTv();
        } else {
            finish();
            Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
        }

        image_fav.setOnClickListener(this);
        progress_circular.setVisibility(View.GONE);

    }

    private void setDataMovie() {
        m = getIntent().getParcelableExtra(EXTRA_MOVIES);
        uri = getIntent().getData();
        if (uri != null){
            Toast.makeText(this, "uri tidak null", Toast.LENGTH_SHORT).show();
        }
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()) m =new Movie(cursor);
            cursor.close();
        }else {
            Toast.makeText(this, "Gagal update content provider", Toast.LENGTH_SHORT).show();
        }
        txt_movie_name.setText(m.getOriginalTitle());
        txt_movie_date.setText(m.getReleaseDate());
        txt_movie_overview.setText(m.getOverview());
        txt_vote.setText("Vote: " + m.getVoteAverage() + "%");
        Glide.with(DetailActivity.this)
                .load("https://image.tmdb.org/t/p/w342" + m.getPosterPath())
                .into(img_movie_poster);
        if (tvMovieHelper.getDataMovie(m.getId()).getCount() > 0) {
            imageRed();
        } else {
            imageWhite();
        }
    }

    private void setDataTv() {
        tv = getIntent().getParcelableExtra(EXTRA_MOVIES);
        txt_movie_name.setText(tv.getOriginalName());
        txt_movie_date.setText(tv.getFirstAirDate());
        txt_movie_overview.setText(tv.getOverview());
        txt_vote.setText("Vote: " + tv.getVoteAverage() + "%");
        Glide.with(DetailActivity.this)
                .load("https://image.tmdb.org/t/p/w342" + tv.getPosterPath())
                .into(img_movie_poster);
        if (tvMovieHelper.getDataTv(tv.getId()).getCount() > 0) {
            imageRed();
        } else {
            imageWhite();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvMovieHelper.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.image_fav) {
            if (code == TYPE_TV_INTENT) {
                if (tvMovieHelper.getDataTv(tv.getId()).getCount() > 0) {
                    long result = tvMovieHelper.deleteTv(tv.getId());
                    if (result > 0) {
                        imageWhite();
                        Toast.makeText(this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    long result = tvMovieHelper.insertTv(tv);
                    if (result > 0) {
                        imageRed();
                        Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (code == TYPE_MOVIE_INTENT) {
                if (tvMovieHelper.getDataMovie(m.getId()).getCount() > 0) {

                    imageWhite();
                    resolver.delete(getIntent().getData(),null,null);
                    resolver.notifyChange(CONTENT_URI_MOVIE, new MainActivity.DataObserver(new Handler(),this));
                    Toast.makeText(this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();

                } else {

                    ContentValues values = new ContentValues();
                    values.put(VOTE_AVERAGE_MOVIE, m.getVoteAverage());
                    values.put(TITLE_MOVIE, m.getTitle());
                    values.put(POPULAR_MOVIE, m.getPopularity());
                    values.put(POSTER_PATH_MOVIE, m.getPosterPath());
                    values.put(ORIGINAL_LANGUAGE_MOVIE, m.getOriginalLanguage());
                    values.put(ORIGINAL_TITLE_MOVIE, m.getOriginalTitle());
                    values.put(OVERVIEW_MOVIE, m.getOverview());
                    values.put(RELEASE_MOVIE, m.getReleaseDate());
                    values.put(ID_MOVIE, m.getId());
                    values.put(FAVOURITE_MOVIE, "yes");
                    values.put(TYPE_MOVIE, TYPE_TV_INTENT);
                    resolver.insert(CONTENT_URI_MOVIE, values);
                    resolver.notifyChange(CONTENT_URI_MOVIE, new MainActivity.DataObserver(new Handler(),this));
                    imageRed();
                    Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    private void imageRed() {
        image_fav.setImageResource(R.drawable.ic_favorite_red);
    }

    private void imageWhite() {
        image_fav.setImageResource(R.drawable.ic_favorite_border_white);
    }
}
