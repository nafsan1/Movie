package com.example.moviecatalogue;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.moviecatalogue.fragment.favourite.FavouriteFragment;
import com.example.moviecatalogue.fragment.favourite.movies.MovieFragmentFav;
import com.example.moviecatalogue.fragment.movie.MovieFragment;
import com.example.moviecatalogue.fragment.tv.TvFragment;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    public static String EXTRA_MOVIES = "MOVIESHERO";
    public static int TYPE_TV_INTENT = 101;
    public static int TYPE_MOVIE_INTENT = 102;
    private FrameLayout frameLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            String title = null;
            Fragment fragment = null;

            if (id == R.id.navigation_movie) {
                fragment = new MovieFragment();
                title = getResources().getString(R.string.movie);
                setActionBarTitle(title);
            } else if (id == R.id.navigation_tv) {
                title = getResources().getString(R.string.tv_show);
                fragment = new TvFragment();
                setActionBarTitle(title);
            }else if (id == R.id.favourite_tv){
                title = getResources().getString(R.string.favourite);
                fragment = new FavouriteFragment();
                setActionBarTitle(title);
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment)
                        .commit();
            }
            return true;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.eng){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(String title) {
        //getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            setActionBarTitle(getResources().getString(R.string.movie));
            MovieFragment movieFragment = new MovieFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, movieFragment);
            fragmentTransaction.commit();
        }
        //initToolbar();
        frameLayout = findViewById(R.id.content_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
