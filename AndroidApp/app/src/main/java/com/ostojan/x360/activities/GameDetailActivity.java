package com.ostojan.x360.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ostojan.x360.R;
import com.ostojan.x360.controller.ApiClient;
import com.ostojan.x360.controller.ApiController;
import com.ostojan.x360.model.Game;
import com.squareup.picasso.Picasso;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailActivity extends AppCompatActivity implements Callback<Game> {

    private static final String LOG_TAG = GameDetailActivity.class.getName();

    public static final String EXTRA_GAME_ID = "EXTRA_GAME_ID";

    @BindView(R.id.image_detail_cover)
    ImageView coverImage;
    @BindView(R.id.text_game_title)
    TextView title;
    @BindView(R.id.text_store_link)
    TextView storeLink;

    private int gameId;
    private Game game;
    private ApiController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);
        getGameId();
        apiController = new ApiController(ApiClient.getApiClientInterface());
    }

    public void getGameId() {
        Intent intent = getIntent();
        gameId = intent.getIntExtra(EXTRA_GAME_ID, -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameId < 0) {
            Toast.makeText(this, R.string.error_no_such_game, Toast.LENGTH_SHORT).show();
            finish();
        }
        apiController.getGameWithId(gameId, this);
    }

    @OnClick(R.id.text_store_link)
    public void storeLinkClicked() {
        if (game != null) {
            Uri uri = Uri.parse(game.getStoreLink().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(Call<Game> call, Response<Game> response) {
        game = response.body();
        if (game == null) {
            Toast.makeText(this, R.string.error_problem_with_data, Toast.LENGTH_SHORT).show();
            return;
        }
        title.setText(game.getTitle());
        storeLink.setClickable(true);
        Picasso.with(this)
                .load(game.getCoverLink().toString())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(coverImage);
    }

    @Override
    public void onFailure(Call<Game> call, Throwable error) {
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
