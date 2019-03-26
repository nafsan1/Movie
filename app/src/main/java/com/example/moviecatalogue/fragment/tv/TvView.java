package com.example.moviecatalogue.fragment.tv;

import java.util.List;

public interface TvView {
    void showProgress();
    void hideProgress();
    void getDataMovie(List<Tv> listMovie);
    void onAddError(String message);
}
