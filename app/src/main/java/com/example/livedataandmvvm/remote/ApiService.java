package com.example.livedataandmvvm.remote;

import com.example.livedataandmvvm.model.Ticket;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("livedata.json")
    Call<Ticket> getTicketJson();
}
