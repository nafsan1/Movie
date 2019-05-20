package com.example.movieprovider.fragment.tv;

import com.example.movieprovider.fragment.tv.Tv;

import java.util.List;

public interface TvView {
    void showProgress();
    void hideProgress();
    void getDataMovie(List<Tv> listMovie);
    void onAddError(String message);
}
