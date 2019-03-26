package com.example.moviecatalogue.fragment.tv;

import com.example.moviecatalogue.fragment.movie.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvDataResponse {
    @SerializedName("results")
    @Expose
    private List<Tv> tv = null;
    public TvDataResponse(List<Tv> tv ){
        this.tv = tv;
    }
    public List<Tv> getTv() {
        return tv;
    }

    public void setTv(List<Tv> tv) {
        this.tv = tv;
    }
}
