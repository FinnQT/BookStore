package com.example.do_an_2.api;

import com.example.do_an_2.Book;
import com.example.do_an_2.model.sach;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


//http://localhost:8080/DiDong/getsanpham.php
public interface ApiService {

    Gson gson= new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiservice=  new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("DiDong/getsanpham.php")
    Call<List<sach>> getlistuser();
}
