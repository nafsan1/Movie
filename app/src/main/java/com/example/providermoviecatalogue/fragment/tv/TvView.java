package com.example.providermoviecatalogue.fragment.tv;

import java.util.List;

public interface TvView {
    void showProgress();
    void hideProgress();
    void getDataMovie(List<Tv> listMovie);
    void onAddError(String message);
}
