package com.example.myfavouritemovie.api;


import com.example.myfavouritemovie.model.MovieDataResponse;
import com.example.myfavouritemovie.model.TvDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
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
