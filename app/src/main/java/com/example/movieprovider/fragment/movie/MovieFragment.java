package com.example.movieprovider.fragment.movie;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movieprovider.R;


import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class MovieFragment extends Fragment implements MovieView {
    private List<MovieDataResponse> listMovieResponse = new ArrayList<>();
    private List<Movie> listMovieAc = new ArrayList<>();
    private RecyclerView rv_tv;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    private View view;

    public MovieFragment() {
        // Required empty public constructor
    }

    MoviePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frameLayout = new FrameLayout(getActivity());
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_movie, null);

        rv_tv = view.findViewById(R.id.rv_tv);
        progressBar = view.findViewById(R.id.progress_circular);
        progressBar.bringToFront();
        presenter = new MoviePresenter(this, listMovieResponse);
        initToolbar(view);
        frameLayout.addView(view);
        setHasOptionsMenu(true);
        return frameLayout;
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.movie));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.eng) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.showData();
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        rv_tv.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        rv_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void getDataMovie(List<Movie> listMovie) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_tv.setLayoutManager(layoutManager);
        AdapterMovie adapter = new AdapterMovie(getActivity());
        adapter.setListMovie(listMovie);
        rv_tv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAddError(String message) {

        Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
    }
}
