package com.ostojan.x360.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ostojan.x360.Properties;
import com.ostojan.x360.model.Game;
import com.ostojan.x360.model.GameTypeAdapter;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class ApiControllerTest {
    private ApiController apiController;
    private NetworkBehavior behavior;
    private MockApiClientInterface apiClientInterface;

    private List<Game> obtainedGames;
    private Game obtainedGame;
    private boolean failure;
    private Throwable throwable;

    @Before
    public void setUp() {
        Gson gson = createGson();
        Retrofit retrofit = createRetrofit(gson);
        createNetworkBehavior();
        MockRetrofit mockRetrofit = createMockRetrofit(retrofit);
        BehaviorDelegate<ApiClientInterface> delegate = mockRetrofit.create(ApiClientInterface.class);
        apiClientInterface = new MockApiClientInterface(delegate);
        apiController = new ApiController(apiClientInterface);
        clearCommonValues();
    }

    private void createNetworkBehavior() {
        behavior = NetworkBehavior.create();
        behavior.setDelay(100, TimeUnit.MILLISECONDS);
    }

    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Game.class, new GameTypeAdapter())
                .create();
    }

    private Retrofit createRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Properties.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private MockRetrofit createMockRetrofit(Retrofit retrofit) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .backgroundExecutor(executorService)
                .build();
    }

    private void clearCommonValues() {
        obtainedGames = null;
        obtainedGame = null;
        failure = false;
        throwable = null;
    }

    @Test
    public void successOnGetGames() {
        try {
            behavior.setFailurePercent(0);
            tryGetGames(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Exception during obtaining games");
        }
    }

    @Test
    public void failureOnGetGames() {
        try {
            behavior.setFailurePercent(100);
            tryGetGames(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Exception during obtaining games");
        }
    }

    private void tryGetGames(boolean withSuccess) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        apiController.getGames(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                obtainedGames = response.body();
                latch.countDown();
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                failure = true;
                throwable = t;
                latch.countDown();
            }
        });
        failOnExecutionTimeout(latch);
        if (withSuccess) {
            failOnFailureOnCallbackExecution();
            assertNotNull("Obtained empty response", obtainedGames);
            List<Game> originalGames = apiClientInterface.getOriginalGames();
            assertEquals("Obtained different number of games than expected", originalGames.size(), obtainedGames.size());
            for (int i = 0; i < obtainedGames.size(); ++i) {
                assertEquals("Obtained game doesn't match original", originalGames.get(i), obtainedGames.get(i));
            }
        } else {
            assertNull("Obtained response on failure", obtainedGames);
            testFailureHandle();
        }
    }

    @Test
    public void successOnGetGameWithId() {
        try {
            behavior.setFailurePercent(0);
            tryGetGameWithId(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Exception during obtaining game");
        }
    }

    @Test
    public void failureOnGetGameWithId() {
        try {
            behavior.setFailurePercent(100);
            tryGetGameWithId(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Exception during obtaining game");
        }
    }

    private void tryGetGameWithId(boolean withSuccess) throws InterruptedException {
        List<Game> originalGames = apiClientInterface.getOriginalGames();
        for (Game game : originalGames) {
            final CountDownLatch latch = new CountDownLatch(1);
            apiController.getGameWithId(game.getId(), new Callback<Game>() {
                @Override
                public void onResponse(Call<Game> call, Response<Game> response) {
                    obtainedGame = response.body();
                    latch.countDown();
                }

                @Override
                public void onFailure(Call<Game> call, Throwable t) {
                    failure = true;
                    throwable = t;
                    latch.countDown();
                }
            });
            failOnExecutionTimeout(latch);
            if (withSuccess) {
                failOnFailureOnCallbackExecution();
                assertEquals("Obtained game doesn't match original", game, obtainedGame);
            } else {
                assertNull("Obtained response on failure", obtainedGame);
                testFailureHandle();
            }
        }
    }

    private void failOnExecutionTimeout(CountDownLatch latch) throws InterruptedException {
        boolean timeOut = latch.await(10, TimeUnit.SECONDS);
        if (!timeOut) {
            fail("Callback execution timeout reached");
        }
    }

    private void failOnFailureOnCallbackExecution() {
        if (failure) {
            throwable.printStackTrace();
            fail("Exception during obtaining process");
        }
    }

    private void testFailureHandle() {
        assertTrue("Failure didn't handled correctly", failure);
        assertNotNull("No exception on failure", throwable);
    }
}
