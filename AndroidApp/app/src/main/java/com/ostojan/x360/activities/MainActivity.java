package com.ostojan.x360.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ostojan.x360.R;
import com.ostojan.x360.controller.ApiClient;
import com.ostojan.x360.controller.ApiController;
import com.ostojan.x360.model.Game;
import com.ostojan.x360.view.GameAdapter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<List<Game>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    @BindView(R.id.recycler_games_list)
    RecyclerView gamesList;

    private GameAdapter gamesAdapter;
    private ApiController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createGameListRecyclerView();
        apiController = new ApiController(ApiClient.getApiClientInterface());
    }

    private void createGameListRecyclerView() {
        gamesList.setHasFixedSize(true);
        gamesList.setLayoutManager(new LinearLayoutManager(this));
        gamesList.setItemAnimator(new DefaultItemAnimator());
        gamesAdapter = new GameAdapter(this);
        gamesList.setAdapter(gamesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        apiController.getGames(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GameDetailActivity.class);
        int layoutPosition = gamesList.getChildLayoutPosition(v);
        intent.putExtra(GameDetailActivity.EXTRA_GAME_ID, gamesAdapter.get(layoutPosition).getId());
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
        List<Game> games = response.body();
        if (games == null) {
            Toast.makeText(this, R.string.error_problem_with_data, Toast.LENGTH_SHORT).show();
            return;
        }
        gamesAdapter.addAll(games);
    }

    @Override
    public void onFailure(Call<List<Game>> call, Throwable error) {
        Log.e(LOG_TAG, error.getMessage());
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(this, R.string.error_problem_with_server, Toast.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.error_unknown, Toast.LENGTH_SHORT).show();
        }
    }
}
