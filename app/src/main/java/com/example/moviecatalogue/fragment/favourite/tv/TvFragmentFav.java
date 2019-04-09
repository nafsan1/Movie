package com.example.moviecatalogue.fragment.favourite.tv;


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
import com.example.moviecatalogue.fragment.tv.AdapterTv;
import com.example.moviecatalogue.fragment.tv.Tv;
import com.example.moviecatalogue.sqlite.TvMovieHelper;

import java.util.ArrayList;
import java.util.List;


public class TvFragmentFav extends Fragment {


    public TvFragmentFav() {
        // Required empty public constructor
    }

    private RecyclerView recycleView;
    private List<Tv> list = new ArrayList<>();
    private TvMovieHelper tvMovieHelper;
    private TextView txt_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_fav, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        txt_tv = view.findViewById(R.id.txt_tv);
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
        list.addAll(tvMovieHelper.getListTv());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        AdapterTv adapter = new AdapterTv(getActivity());
        adapter.setListMovie(list);
        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list.size() > 0){
            txt_tv.setVisibility(View.GONE);
        }else {
            txt_tv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvMovieHelper.close();
    }


}
