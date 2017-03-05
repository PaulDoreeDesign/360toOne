package com.ostojan.x360.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ostojan.x360.Properties;
import com.ostojan.x360.model.Game;
import com.ostojan.x360.model.GameTypeAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static volatile ApiClientInterface apiClient;

    private ApiClient() {
        throw new RuntimeException("Use getApiClientInterface() to get ApiClientInterface");
    }

    public static ApiClientInterface getApiClientInterface() {
        if (apiClient == null) {
            synchronized (ApiClient.class) {
                if (apiClient == null) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Game.class, new GameTypeAdapter())
                            .create();
                    apiClient = new Retrofit.Builder()
                            .baseUrl(Properties.SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
                            .create(ApiClientInterface.class);
                }
            }
        }
        return apiClient;
    }
}
