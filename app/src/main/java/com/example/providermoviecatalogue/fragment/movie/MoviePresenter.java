package com.example.providermoviecatalogue.fragment.movie;



import com.example.providermoviecatalogue.api.BaseApiService;
import com.example.providermoviecatalogue.api.UtilsAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter {
    private MovieView view;
    private List<Movie> listMovie = new ArrayList<>();
    private List<MovieDataResponse> listMovieResponse = new ArrayList<>();


    public MoviePresenter(MovieView view,List<MovieDataResponse> listMovieResponse) {
        this.view = view;
        this.listMovieResponse = listMovieResponse;

    }
    void searchDataMovie(String name){
        view.showProgress();
        listMovie.clear();
        listMovieResponse.clear();
        BaseApiService mApiService = UtilsAPI.getApiService();
        mApiService.getSearchMovie(name).enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        int total = response.body().getMovies().size();
                        for (int i = 0; i < total; i++) {
                            Movie m = new Movie(response.body().getMovies().get(i).getVoteAverage(),
                                    response.body().getMovies().get(i).getTitle(),
                                    response.body().getMovies().get(i).getPopularity(),
                                    response.body().getMovies().get(i).getPosterPath(),
                                    response.body().getMovies().get(i).getOriginalLanguage(),
                                    response.body().getMovies().get(i).getOriginalTitle(),
                                    response.body().getMovies().get(i).getOverview(),
                                    response.body().getMovies().get(i).getReleaseDate(),
                                    response.body().getMovies().get(i).getId());
                            listMovie.add(m);

                        }
                        MovieDataResponse movie = new MovieDataResponse(listMovie);
                        listMovieResponse.add(movie);
                        view.getDataMovie(listMovie);
                        view.hideProgress();

                    } catch (Exception e) {
                        // view.onAddError("Server Error");
                        view.hideProgress();
                        e.printStackTrace();

                    }

                } else {
                    view.onAddError("Server Error");
                    view.hideProgress();

                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                view.onAddError(t.getMessage());
                view.hideProgress();
            }
        });
    }
    void showData() {
        listMovieResponse.clear();
        listMovie.clear();
        view.showProgress();
        BaseApiService mApiService = UtilsAPI.getApiService();
        mApiService.getMovie().enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        int total = response.body().getMovies().size();
                        for (int i = 0; i < total; i++) {
                            Movie m = new Movie(response.body().getMovies().get(i).getVoteAverage(),
                                    response.body().getMovies().get(i).getTitle(),
                                    response.body().getMovies().get(i).getPopularity(),
                                    response.body().getMovies().get(i).getPosterPath(),
                                    response.body().getMovies().get(i).getOriginalLanguage(),
                                    response.body().getMovies().get(i).getOriginalTitle(),
                                    response.body().getMovies().get(i).getOverview(),
                                    response.body().getMovies().get(i).getReleaseDate(),
                                    response.body().getMovies().get(i).getId());
                            listMovie.add(m);

                        }
                        MovieDataResponse movie = new MovieDataResponse(listMovie);
                        listMovieResponse.add(movie);
                        view.getDataMovie(listMovie);
                        view.hideProgress();

                    } catch (Exception e) {
                       // view.onAddError("Server Error");
                        view.hideProgress();
                        e.printStackTrace();

                    }

                } else {
                    view.onAddError("Server Error");
                    view.hideProgress();

                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
               view.onAddError(t.getMessage());
                view.hideProgress();

            }
        });
    }
    /*private void recyclerView(List<Movie> movies, Context context) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        AdapterMovie adapter = new AdapterMovie(context);
        adapter.setListMovie(movies);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }*/

}
