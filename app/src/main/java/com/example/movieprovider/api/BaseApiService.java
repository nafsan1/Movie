package com.example.movieprovider.api;


import com.example.movieprovider.fragment.movie.MovieDataResponse;
import com.example.movieprovider.fragment.tv.TvDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseApiService {

    /*@FormUrlEncoded
    @POST("AKfycbxB5RVPFfecAjzOwfcc7ZX2TGYwcKV8_A6qR4eD/exec")
    Call<ReadDataResponse> readData(@Field("action") String action,
                                    @Field("tabelName") String tabelName);
*/
    /*@GET("v0/topstories.json")
        Call <List<Integer>>getTopstories();*/

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
