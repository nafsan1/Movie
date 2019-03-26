package com.example.moviecatalogue.fragment.tv;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.moviecatalogue.fragment.movie.AdapterMovie;
import com.example.moviecatalogue.fragment.movie.MovieDataResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class TvFragment extends Fragment implements TvView {


    private AdapterMovie adapter;
    private RecyclerView rv_tv;
    private List<Tv> listTv = new ArrayList<>();
    private List<TvDataResponse> listTvResponse = new ArrayList<>();
    private TvPresenter presenter;

    public TvFragment() {
        // Required empty public constructor
    }

    private ProgressBar progress_circular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        rv_tv = view.findViewById(R.id.rv_tv);
        progress_circular = view.findViewById(R.id.progress_circular);
        presenter = new TvPresenter(this, listTvResponse);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.showData();
    }


    @Override
    public void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
        rv_tv.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress_circular.setVisibility(View.GONE);
        rv_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void getDataMovie(List<Tv> listMovie) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_tv.setLayoutManager(layoutManager);
        AdapterTv adapter = new AdapterTv(getActivity());
        adapter.setListMovie(listMovie);
        rv_tv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onAddError(String message) {
        Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
    }
}
