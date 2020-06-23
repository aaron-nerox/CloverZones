package com.nero.starx.noname.Utlis.Api;

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.1.3/cloverback/public/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
