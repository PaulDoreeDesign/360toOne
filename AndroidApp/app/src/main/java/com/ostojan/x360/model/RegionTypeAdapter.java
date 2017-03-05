package com.ostojan.x360.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RegionTypeAdapter extends TypeAdapter<Region> {
    @Override
    public void write(JsonWriter out, final Region region) throws IOException {
    }

    @Override
    public Region read(JsonReader in) throws IOException {
        return null;
    }
}
