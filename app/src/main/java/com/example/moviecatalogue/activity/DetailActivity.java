package com.example.moviecatalogue.activity;



import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.fragment.movie.Movie;
import com.example.moviecatalogue.fragment.tv.Tv;


import static com.example.moviecatalogue.MainActivity.EXTRA_MOVIES;

@SuppressWarnings("ALL")
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ProgressBar progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setVisibility(View.VISIBLE);
        TextView txt_movie_name = findViewById(R.id.txt_movie_name);
        TextView txt_movie_date = findViewById(R.id.txt_movie_date);
        TextView txt_movie_overview = findViewById(R.id.txt_movie_overview);
        TextView txt_vote = findViewById(R.id.txt_vote);
        ImageView img_movie_poster = findViewById(R.id.img_movie_poster);
        int code = getIntent().getIntExtra("code",0);

        if (code == 101){
            Movie m = getIntent().getParcelableExtra(EXTRA_MOVIES);
            txt_movie_name.setText(m.getOriginalTitle());
            txt_movie_date.setText(m.getReleaseDate());
            txt_movie_overview.setText(m.getOverview());
            txt_vote.setText("Vote: "+m.getVoteAverage() +"%");
            Glide.with(DetailActivity.this)
                    .load("https://image.tmdb.org/t/p/w342"+m.getPosterPath())
                    .into(img_movie_poster);
        }else if (code == 102){
            Tv tv = getIntent().getParcelableExtra(EXTRA_MOVIES);
            txt_movie_name.setText(tv.getOriginalName());
            txt_movie_date.setText(tv.getFirstAirDate());
            txt_movie_overview.setText(tv.getOverview());
            txt_vote.setText("Vote: "+tv.getVoteAverage() +"%");
            Glide.with(DetailActivity.this)
                    .load("https://image.tmdb.org/t/p/w342"+tv.getPosterPath())
                    .into(img_movie_poster);

        }else {
            finish();
            Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress_circular.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
