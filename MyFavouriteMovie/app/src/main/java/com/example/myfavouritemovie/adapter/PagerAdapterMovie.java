package com.example.myfavouritemovie.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.myfavouritemovie.fragment.MovieFragmentFav;
import com.example.myfavouritemovie.fragment.TvFragmentFav;

public class PagerAdapterMovie extends FragmentStatePagerAdapter {

    private int number_tabs;

    public PagerAdapterMovie(FragmentManager fm, int number_tabs) {
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
