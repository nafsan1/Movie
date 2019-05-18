package com.example.myfavouritemovie.fragment;


import android.content.ContentResolver;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.myfavouritemovie.R;
import com.example.myfavouritemovie.adapter.AdapterMovie;
import com.example.myfavouritemovie.model.Movie;
import com.example.myfavouritemovie.sqlite.TvMovieHelper;

import java.util.ArrayList;
import java.util.List;


import static com.example.myfavouritemovie.MainActivity.EXTRA_CATALOG;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.AUTHORITY;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.CONTENT_URI_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.TABLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.MappingHelper.mapCursorToArrayList;

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
        adapter = new AdapterMovie(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);

        //initComponent();

        if (savedInstanceState == null){
            Cursor cursor = getActivity().getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
            list = mapCursorToArrayList(cursor);
            if (list.size()> 0) {
                adapter.setListMovie(list);
            }else {
                adapter.setListMovie(new ArrayList<Movie>());
            }
        }else {
            List<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_CATALOG);
            if (list != null){
                adapter.setListMovie(list);
            }
        }
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private void listEntries(ContentResolver cr) {
        Uri uri = Uri.parse("content://" + AUTHORITY + "/" +TABLE_MOVIE);
        Cursor c = cr.query(uri, null, null, null, null);

        if (c == null) {
            Log.d("FRAGMEN", "Cursor c == null.");
            return;
        }
        while (c.moveToNext()) {
            String column1 = c.getString(0);
            String column2 = c.getString(1);
            String column3 = c.getString(2);

            Log.d("FRAGMEN", "column1=" + column1 + " column2=" + column2 + " column3=" + column3);
        }
        c.close();
    }
    private void initComponent() {
        //list.addAll(tvMovieHelper.getListMovie());


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
