package com.ostojan.x360.controller;

import com.ostojan.x360.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClientInterface {
    @GET("x360/api/games/")
    Call<List<Game>> getGames();

    @GET("x360/api/games/{id}/")
    Call<Game> getGameWithId(@Path("id") int id);
}
