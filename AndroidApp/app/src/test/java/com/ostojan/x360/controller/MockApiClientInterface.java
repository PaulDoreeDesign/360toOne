package com.ostojan.x360.controller;

import com.ostojan.x360.model.Game;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;

public class MockApiClientInterface implements ApiClientInterface {

    private final BehaviorDelegate<ApiClientInterface> delegate;
    private final List<Game> games;

    public MockApiClientInterface(BehaviorDelegate<ApiClientInterface> delegate) {
        this.delegate = delegate;
        this.games = new ArrayList<>();
        createGames();
    }

    private void createGames() {
        for (int i = 0; i < 10; ++i) {
            String title = String.format(Locale.getDefault(), "Game%d", i);
            String coveLink = String.format(Locale.getDefault(), "http://www.game.com/cover%d.jpg", i);
            String storeLink = String.format(Locale.getDefault(), "http://www.game.com/store/game%d/", i);
            try {
                Game game = new Game.Builder()
                        .id(i)
                        .title(title)
                        .coverLink(coveLink)
                        .storeLink(storeLink)
                        .build();
                games.add(game);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Call<List<Game>> getGames() {
        return delegate.returningResponse(games).getGames();
    }

    @Override
    public Call<Game> getGameWithId(@Path("id") int id) {
        Game response = null;
        try {
            response = games.get(id);
        } catch (IndexOutOfBoundsException e) {
        }
        return delegate.returningResponse(response).getGameWithId(id);
    }

    public List<Game> getOriginalGames() {
        return games;
    }

    public Game getOriginalGameWithId(int id) {
        return games.get(id);
    }
}
