package com.ostojan.x360.controller;

import com.ostojan.x360.model.Game;

import java.io.IOException;
import java.util.List;

import retrofit2.Callback;

public class ApiController {
    private ApiClientInterface apiClient;

    public ApiController(ApiClientInterface apiClient) {
        this.apiClient = apiClient;
    }

    public void getGames(Callback<List<Game>> callback) {
    }

    public void getGameWithId(int id, Callback<Game> callback) {
    }
}
