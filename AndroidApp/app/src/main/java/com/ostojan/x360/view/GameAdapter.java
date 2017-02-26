package com.ostojan.x360.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ostojan.x360.R;
import com.ostojan.x360.model.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameViewHolder> {

    private List<Game> games;
    private View.OnClickListener onClickListener;

    public GameAdapter(View.OnClickListener onClickListener, List<Game> games) {
        this.onClickListener = onClickListener;
        this.games = games;
    }

    public GameAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.games = new ArrayList<>();
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        view.setOnClickListener(onClickListener);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game game = games.get(position);
        holder.title.setText(game.getTitle());
        Picasso.with(holder.itemView.getContext())
                .load(game.getCoverLink().toString())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(holder.coverImage);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void add(Game game) {
        this.games.add(game);
        notifyDataSetChanged();
    }

    public void addAll(Collection<Game> games) {
        this.games.addAll(games);
        notifyDataSetChanged();
    }

    public Game get(int index) {
        return this.games.get(index);
    }
}
