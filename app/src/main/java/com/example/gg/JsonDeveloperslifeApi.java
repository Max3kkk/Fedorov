package com.example.gg;


import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonDeveloperslifeApi {
    @GET("https://developerslife.ru/random?json=true")
    Call<Post> getPost();
}
