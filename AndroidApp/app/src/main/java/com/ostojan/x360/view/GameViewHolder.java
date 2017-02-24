package com.ostojan.x360.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameViewHolder extends RecyclerView.ViewHolder {

    @BindView(android.R.id.text1)
    TextView text1;

    public GameViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
