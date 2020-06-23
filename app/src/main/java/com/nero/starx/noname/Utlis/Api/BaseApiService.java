package com.nero.starx.noname.Utlis.Api;

import com.nero.starx.noname.Utlis.model.ResponseDecisions;
import com.nero.starx.noname.Utlis.model.ResponseEtat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> loginRequest(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("password") String password);
    @FormUrlEncoded
    @POST("api/etatwilya")
    Call<ResponseEtat> getEtat(@Field("wilaya") String wilaya);


    @FormUrlEncoded
    @POST("api/decisions")
    Call<ResponseDecisions> getDecisions(@Field("wilaya") String wilaya);
    @FormUrlEncoded
    @POST("api/etatdecisions")
    Call<ResponseBody> getEtatetDecisions(@Field("wilaya") String wilaya);


}
