package com.ostojan.x360.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ostojan.x360.R;
import com.ostojan.x360.view.GameAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.recycler_games_list)
    RecyclerView gamesList;

    private GameAdapter gamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createGameListRecyclerView();
    }

    private void createGameListRecyclerView() {
        gamesList.setHasFixedSize(true);
        gamesList.setLayoutManager(new LinearLayoutManager(this));
        gamesList.setItemAnimator(new DefaultItemAnimator());
        gamesAdapter = new GameAdapter(this);
        gamesList.setAdapter(gamesAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
