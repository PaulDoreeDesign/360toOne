package com.ostojan.x360.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GameTypeAdapter extends TypeAdapter<Game> {
    @Override
    public void write(final JsonWriter out, final Game game) throws IOException {
        out.beginObject();
        out.name("id").value(game.getId());
        out.name("title").value(game.getTitle());
        if (game.getCoverLink() != null) {
            out.name("cover_link").value(game.getCoverLink().toString());
        }
        if (game.getStoreLink() != null) {
            out.name("store_link").value(game.getStoreLink().toString());
        }
        out.endObject();
    }

    @Override
    public Game read(final JsonReader in) throws IOException {
        Game.Builder gameBuilder = new Game.Builder();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "id":
                    gameBuilder.id(in.nextInt());
                    break;
                case "title":
                    gameBuilder.title(in.nextString());
                    break;
                case "cover_link":
                    gameBuilder.coverLink(in.nextString());
                    break;
                case "store_link":
                    gameBuilder.storeLink(in.nextString());
                    break;
            }
        }
        in.endObject();
        return gameBuilder.build();
    }
}
