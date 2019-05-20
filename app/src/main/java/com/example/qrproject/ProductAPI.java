package com.example.qrproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductAPI {
    @GET("product/find/{id}")
    Call<Product> find(@Path("id") String id);
}
