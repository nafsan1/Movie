package com.example.moviecatalogue.activity;


import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.fragment.movie.Movie;
import com.example.moviecatalogue.fragment.tv.Tv;
import com.example.moviecatalogue.sqlite.TvMovieHelper;


import static com.example.moviecatalogue.MainActivity.EXTRA_MOVIES;
import static com.example.moviecatalogue.MainActivity.TYPE_MOVIE_INTENT;
import static com.example.moviecatalogue.MainActivity.TYPE_TV_INTENT;

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
                    long result = tvMovieHelper.deleteMovie(m.getId());
                    if (result > 0) {
                        imageWhite();
                        Toast.makeText(this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    long result = tvMovieHelper.insertMovie(m);
                    if (result > 0) {
                        imageRed();
                        Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }
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
