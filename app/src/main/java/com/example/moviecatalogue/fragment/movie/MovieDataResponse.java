package com.example.moviecatalogue.fragment.movie;

import android.icu.text.AlphabeticIndex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDataResponse {

    @SerializedName("results")
    @Expose
    private List<Movie> movies = null;

    public MovieDataResponse(List<Movie> movies) {
        this.movies = movies;
    }


    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
