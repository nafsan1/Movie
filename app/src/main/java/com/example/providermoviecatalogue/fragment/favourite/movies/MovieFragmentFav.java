package com.example.providermoviecatalogue.fragment.favourite.movies;


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.providermoviecatalogue.R;
import com.example.providermoviecatalogue.fragment.movie.AdapterMovie;
import com.example.providermoviecatalogue.fragment.movie.Movie;
import com.example.providermoviecatalogue.sqlite.TvMovieHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.providermoviecatalogue.MainActivity.EXTRA_CATALOG;

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
    private AdapterMovie adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        txt_movie = view.findViewById(R.id.txt_movie);
        tvMovieHelper = TvMovieHelper.getInstance(getContext().getApplicationContext());
        tvMovieHelper.open();
        initComponent();
        if (savedInstanceState == null){
            adapter.setListMovie(tvMovieHelper.getListMovie());
        }else {
            List<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_CATALOG);
            if (list != null){
                adapter.setListMovie(list);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    private void initComponent() {
        list.clear();
        list.addAll(tvMovieHelper.getListMovie());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        adapter = new AdapterMovie(getActivity());

        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvMovieHelper.close();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_CATALOG,(ArrayList<? extends Parcelable>) adapter.getListMovie());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}