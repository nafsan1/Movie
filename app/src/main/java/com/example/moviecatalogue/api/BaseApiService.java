package com.example.moviecatalogue.api;


import com.example.moviecatalogue.fragment.movie.MovieDataResponse;
import com.example.moviecatalogue.fragment.tv.TvDataResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("discover/movie?api_key=403a22d62769b2f5b28ce5321b91690f&language=en-US")
    Call<MovieDataResponse> getMovie();
    @GET("discover/tv?api_key=403a22d62769b2f5b28ce5321b91690f&language=en-US")
    Call<TvDataResponse> getTv();
    @GET("search/movie?api_key=403a22d62769b2f5b28ce5321b91690f&language=en-US")
    Call<MovieDataResponse> getSearchMovie(@Query("query") String name);
    @GET("search/movie?api_key=403a22d62769b2f5b28ce5321b91690f&language=en-US")
    Call<TvDataResponse> getSearchTv(@Query("query") String name);
    @GET("discover/movie?api_key=403a22d62769b2f5b28ce5321b91690f&upcoming")
    Call<MovieDataResponse> getMovieRelease();
}
