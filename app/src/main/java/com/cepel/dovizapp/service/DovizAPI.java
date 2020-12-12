package com.cepel.dovizapp.service;

import com.cepel.dovizapp.model.DovizModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DovizAPI {

    @GET("latest?access_key=fb18d00e26014b596b63e6b2ae4bfe91&symbols=USD,GBP,CAD,CHF,INR,TRY")
    Call<DovizModel> getData();
    //Observable<DovizModel> getData();

}
