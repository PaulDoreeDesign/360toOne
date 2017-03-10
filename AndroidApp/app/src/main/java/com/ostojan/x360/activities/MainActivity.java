package com.ostojan.x360.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<List<Game>>, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = MainActivity.class.getName();

    @BindView(R.id.layout_activity_main)
    SwipeRefreshLayout mainLayout;
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
        mainLayout.setOnRefreshListener(this);
        apiController = new ApiController(ApiClient.getApiClientInterface());
    }

    private void createGameListRecyclerView() {
        gamesList.setHasFixedSize(true);
        gamesList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        gamesList.setItemAnimator(new DefaultItemAnimator());
        gamesAdapter = new GameAdapter(this);
        gamesList.setAdapter(gamesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadGames();
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
        mainLayout.setRefreshing(false);
        List<Game> games = response.body();
        if (games == null) {
            Toast.makeText(this, R.string.error_problem_with_data, Toast.LENGTH_SHORT).show();
            return;
        }
        gamesAdapter.addAll(games);
    }

    @Override
    public void onFailure(Call<List<Game>> call, Throwable error) {
        mainLayout.setRefreshing(false);
        Log.e(LOG_TAG, error.getMessage());
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(this, R.string.error_problem_with_server, Toast.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.error_unknown, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        reloadGames();
    }

    private void reloadGames() {
        mainLayout.setRefreshing(true);
        gamesAdapter.clear();
        apiController.getGames(this);
    }
}
