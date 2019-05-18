package com.example.movieprovider.fragment.tv;

import com.example.movieprovider.api.BaseApiService;
import com.example.movieprovider.api.UtilsAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvPresenter {
    private TvView view;
    private List<TvDataResponse> listTvResponse = new ArrayList<>();
    private List<Tv> listTv = new ArrayList<>();

    public TvPresenter(TvView view, List<TvDataResponse> listTvResponse) {
        this.view = view;
        this.listTvResponse = listTvResponse;
    }


    void showData() {
        listTv.clear();
        listTvResponse.clear();
        view.showProgress();
        BaseApiService mApiService = UtilsAPI.getApiService();
        mApiService.getTv().enqueue(new Callback<TvDataResponse>() {
            @Override
            public void onResponse(Call<TvDataResponse> call, Response<TvDataResponse> response) {
                try {
                    int total = response.body().getTv().size();
                    for (int i = 0; i < total; i++) {
                        Tv tv = new Tv(
                                response.body().getTv().get(i).getOriginalName(),
                                response.body().getTv().get(i).getOriginalLanguage(),
                                response.body().getTv().get(i).getName(),
                                response.body().getTv().get(i).getFirstAirDate(),
                                response.body().getTv().get(i).getId(),
                                response.body().getTv().get(i).getVoteAverage(),
                                response.body().getTv().get(i).getOverview(),
                                response.body().getTv().get(i).getPosterPath()
                        );
                        listTv.add(tv);
                    }
                    TvDataResponse tvDataResponse = new TvDataResponse(listTv);
                    listTvResponse.add(tvDataResponse);
                    view.hideProgress();
                    view.getDataMovie(listTv);

                } catch (Exception e) {
                    view.hideProgress();
                    //view.onAddError("Server Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TvDataResponse> call, Throwable t) {
                view.hideProgress();
                view.onAddError("Server Error");
            }
        });
    }

}
