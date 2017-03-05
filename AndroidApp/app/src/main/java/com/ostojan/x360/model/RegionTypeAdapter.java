package com.ostojan.x360.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RegionTypeAdapter extends TypeAdapter<Region> {
    @Override
    public void write(JsonWriter out, final Region region) throws IOException {
        out.beginObject();
        out.name("id").value(region.getId());
        out.name("name").value(region.getName());
        out.name("code").value(region.getCode());
        out.endObject();
    }

    @Override
    public Region read(JsonReader in) throws IOException {
        Region.Builder regionBuilder = new Region.Builder();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "id":
                    regionBuilder.id(in.nextInt());
                    break;
                case "name":
                    regionBuilder.name(in.nextString());
                    break;
                case "code":
                    regionBuilder.code(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return regionBuilder.build();
    }
}
