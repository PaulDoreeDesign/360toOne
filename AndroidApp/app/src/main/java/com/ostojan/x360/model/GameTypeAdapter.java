package com.ostojan.x360.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GameTypeAdapter extends TypeAdapter<Game> {
    @Override
    public void write(final JsonWriter out, final Game game) throws IOException {
    }

    @Override
    public Game read(final JsonReader in) throws IOException {
        return null;
    }
}
