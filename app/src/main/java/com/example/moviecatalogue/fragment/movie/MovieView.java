package com.example.moviecatalogue.fragment.movie;

import java.util.List;

public interface MovieView {
    void showProgress();
    void hideProgress();
    void getDataMovie(List<Movie> listMovie);
    void onAddError(String message);

}
