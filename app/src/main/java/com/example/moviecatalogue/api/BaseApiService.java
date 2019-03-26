package com.example.moviecatalogue.api;


import com.example.moviecatalogue.fragment.movie.MovieDataResponse;
import com.example.moviecatalogue.fragment.tv.TvDataResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    /*@FormUrlEncoded
    @POST("AKfycbxB5RVPFfecAjzOwfcc7ZX2TGYwcKV8_A6qR4eD/exec")
    Call<ReadDataResponse> readData(@Field("action") String action,
                                    @Field("tabelName") String tabelName);
*/
    /*@GET("v0/topstories.json")
        Call <List<Integer>>getTopstories();*/

    @GET("movie?api_key=403a22d62769b2f5b28ce5321b91690f&language=en-US")
    Call<MovieDataResponse> getMovie();
    @GET("tv?api_key=403a22d62769b2f5b28ce5321b91690f&language=en-US")
    Call<TvDataResponse> getTv();
}
