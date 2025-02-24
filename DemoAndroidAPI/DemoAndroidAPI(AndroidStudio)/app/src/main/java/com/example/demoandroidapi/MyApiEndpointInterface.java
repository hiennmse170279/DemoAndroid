package com.example.demoandroidapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {
    String BASE_URL = "http://10.0.2.2:5077/api/";
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    MyApiEndpointInterface myRetrofitApi = retrofit.create(MyApiEndpointInterface.class);

    @GET("Book")
    Call<List<Book>> getBooks();
    @POST("Book")
    Call<Integer> addBook(@Body BookRequestDTO book);
    @PUT("Book")
    Call<Integer> updateBook(@Query("id") int id, @Body BookRequestDTO book);
    @DELETE("Book/{id}")
    Call<Boolean> deleteBook(@Path("id") int id);



}
