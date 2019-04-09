package com.example.moviecatalogue.fragment.favourite.movies;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.fragment.movie.AdapterMovie;
import com.example.moviecatalogue.fragment.movie.Movie;
import com.example.moviecatalogue.sqlite.TvMovieHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragmentFav extends Fragment {



    public MovieFragmentFav() {
        // Required empty public constructor
    }
    private RecyclerView recycleView;
    private List<Movie> list = new ArrayList<>();
    private TvMovieHelper tvMovieHelper;
    private TextView txt_movie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        txt_movie = view.findViewById(R.id.txt_movie);
        tvMovieHelper = TvMovieHelper.getInstance(getContext().getApplicationContext());
        tvMovieHelper.open();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponent();

    }

    private void initComponent() {
        list.clear();
        list.addAll(tvMovieHelper.getListMovie());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        AdapterMovie adapter = new AdapterMovie(getActivity());
        adapter.setListMovie(list);
        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list.size() > 0){
            txt_movie.setVisibility(View.GONE);
        }else {
            txt_movie.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvMovieHelper.close();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
