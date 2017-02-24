package com.ostojan.x360.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ostojan.x360.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_title)
    TextView title;
    @BindView(R.id.image_cover)
    ImageView coverImage;

    public GameViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
