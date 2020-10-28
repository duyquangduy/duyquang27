package com.example.livedataandmvvm.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.livedataandmvvm.model.Ticket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass {

    private static final String BASE_URL = "http://192.168.0.103:8888/demojson/";

    private static Retrofit getRetroInstance(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService(){
        return getRetroInstance().create(ApiService.class);
    }

    public Ticket getTicket(){
        final Ticket ticket = new Ticket();
        ApiService apiService = RetroClass.getApiService();

        apiService.getTicketJson().enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                Ticket mTicket = response.body();
                ticket.setTicketId(mTicket.ticketId);
                ticket.setTicketName(mTicket.ticketName);
                ticket.setTicketDesc(mTicket.ticketDesc);
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {

            }
        });
        return ticket;
    }

    public LiveData<Ticket> getTicketLivedata(){
        final MutableLiveData<Ticket> mutableLiveData = new MutableLiveData<>();

        ApiService apiService = RetroClass.getApiService();
        apiService.getTicketJson().enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                Ticket tic = response.body();
                mutableLiveData.setValue(tic);
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
