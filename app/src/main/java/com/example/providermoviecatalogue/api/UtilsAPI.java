package com.example.providermoviecatalogue.api;

public class UtilsAPI {

    public static final String BASE_ROOT_URL = "https://api.themoviedb.org/3/";

    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_ROOT_URL).create(BaseApiService.class);
    }

}
