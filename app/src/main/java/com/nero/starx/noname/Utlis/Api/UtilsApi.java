package com.nero.starx.noname.Utlis.Api;

public class UtilsApi {
    public static final String BASE_URL_API = "https://pure-reef-16205.herokuapp.com/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
