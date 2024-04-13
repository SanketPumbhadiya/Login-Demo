package com.example.demo_login;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApis {


    String loginUrl = "https://afcwebapiuat.wtpcenter.com/api/";
    @POST("Account")
    Call<String> PostRetrofitApi(@Body LoginRequestModel loginRequestModel);

}
