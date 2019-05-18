package com.example.movieprovider.fragment.favourite;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.movieprovider.fragment.favourite.movies.MovieFragmentFav;
import com.example.movieprovider.fragment.favourite.tv.TvFragmentFav;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int number_tabs;

    public PagerAdapter(FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            return new MovieFragmentFav();
        }else if (position == 1){
            return new TvFragmentFav();
        }else {
            return null;
        }
    }


    @Override
    public int getCount() {
        return number_tabs;
    }
}
