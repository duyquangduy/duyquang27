package com.example.mvvmsample.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmsample.network.ApiClient;
import com.example.mvvmsample.network.ApiService;
import com.example.mvvmsample.responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowRepository {
    private ApiService apiService;

    public MostPopularTvShowRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page) {
        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
