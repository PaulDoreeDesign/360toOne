package com.ostojan.x360.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ostojan.x360.Properties;
import com.ostojan.x360.model.Game;
import com.ostojan.x360.model.GameTypeAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static volatile ApiClient instance;
    private ApiClientInterface apiClient;

    private ApiClient() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() to get ApiClient");
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Game.class, new GameTypeAdapter())
                .create();
        apiClient = new Retrofit.Builder()
                .baseUrl(Properties.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiClientInterface.class);
    }

    public ApiClientInterface getApiClientInterface() {
        return apiClient;
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) instance = new ApiClient();
            }
        }
        return instance;
    }
}
