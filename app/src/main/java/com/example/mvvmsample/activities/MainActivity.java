package com.example.mvvmsample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mvvmsample.R;
import com.example.mvvmsample.adapters.TVShowAdapter;
import com.example.mvvmsample.databinding.ActivityMainBinding;
import com.example.mvvmsample.models.TVShow;
import com.example.mvvmsample.viewmodels.MostPopularTvShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTvShowViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization() {
        activityMainBinding.recyclerviewtvshow.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowViewModel.class);
        tvShowAdapter = new TVShowAdapter(tvShows);
        activityMainBinding.recyclerviewtvshow.setAdapter(tvShowAdapter);
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTVShows(0).observe(this, mostPopularTVShowsReponse -> {
            activityMainBinding.setIsLoading(false);
            if (mostPopularTVShowsReponse != null) {
                if (mostPopularTVShowsReponse.getTvShows() != null) {
                    tvShows.addAll(mostPopularTVShowsReponse.getTvShows());
                    tvShowAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
