package com.example.mvvmsample.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmsample.repositories.MostPopularTvShowRepository;
import com.example.mvvmsample.responses.TVShowResponse;

public class MostPopularTvShowViewModel extends ViewModel {
    private MostPopularTvShowRepository mostPopularTvShowRepository;

    public MostPopularTvShowViewModel(){
        mostPopularTvShowRepository = new MostPopularTvShowRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page){
        return mostPopularTvShowRepository.getMostPopularTVShows(page);
    }
}
