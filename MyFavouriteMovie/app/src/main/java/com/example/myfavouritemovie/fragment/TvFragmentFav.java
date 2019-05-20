package com.example.myfavouritemovie.fragment;


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


import com.example.myfavouritemovie.R;
import com.example.myfavouritemovie.adapter.AdapterTv;
import com.example.myfavouritemovie.model.Tv;
import com.example.myfavouritemovie.sqlite.TvMovieHelper;
import java.util.ArrayList;
import java.util.List;

import static com.example.myfavouritemovie.MainActivity.EXTRA_CATALOG;


public class TvFragmentFav extends Fragment {


    public TvFragmentFav() {
        // Required empty public constructor
    }

    private RecyclerView recycleView;
    private List<Tv> list = new ArrayList<>();
    private TvMovieHelper tvMovieHelper;
    private TextView txt_tv;
    private AdapterTv adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_fav, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        txt_tv = view.findViewById(R.id.txt_tv);
        tvMovieHelper = TvMovieHelper.getInstance(getContext().getApplicationContext());
        tvMovieHelper.open();
        adapter = new AdapterTv(getActivity());
        if (savedInstanceState == null) {
            adapter.setListMovie(tvMovieHelper.getListTv());
        } else {
            List<Tv> list = savedInstanceState.getParcelableArrayList(EXTRA_CATALOG);
            if (list != null) {
                adapter.setListMovie(list);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponent();
    }

    private void initComponent() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycleView.setLayoutManager(layoutManager);

        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (tvMovieHelper.getListTv().size() > 0){
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_CATALOG, (ArrayList<? extends Parcelable>) adapter.getListMovie());
    }
}
