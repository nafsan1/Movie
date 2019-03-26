package com.example.moviecatalogue.fragment.movie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.api.BaseApiService;
import com.example.moviecatalogue.api.UtilsAPI;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ALL")
public class MovieFragment extends Fragment implements MovieView{
    private List<MovieDataResponse> listMovieResponse = new ArrayList<>();
    private List<Movie> listMovie = new ArrayList<>();
    private RecyclerView rv_tv;
    private BaseApiService mApiService;
    private ProgressBar progressBar;
    public MovieFragment() {
        // Required empty public constructor
    }
    MoviePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        rv_tv = view.findViewById(R.id.rv_tv);
        progressBar = view.findViewById(R.id.progress_circular);
        mApiService = UtilsAPI.getApiService();
        progressBar.bringToFront();
        presenter = new MoviePresenter(this,listMovieResponse);


        return view;
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
    public void onAddError(String message) {
        Toast.makeText(getContext(), message+"", Toast.LENGTH_SHORT).show();
    }
}
