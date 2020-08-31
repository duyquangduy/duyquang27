package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRetrofitClient {  //tuong tac client voi Server
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String base_url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(100000, TimeUnit.MILLISECONDS)
                .writeTimeout(100000, TimeUnit.MILLISECONDS)
                .connectTimeout(100000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        //Convert json sang bien cua Java dang Gson
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}
