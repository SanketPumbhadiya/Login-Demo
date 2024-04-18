package com.example.demo_login;



import com.example.demo_login.model.ModelApiRequestLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApis {


    String apiBaseUrl = "https://afcwebapiuat.wtpcenter.com/api/";
    @POST("Account")
    Call<String> PostRetrofitApi(@Body ModelApiRequestLogin modelApiRequestLogin);

}
