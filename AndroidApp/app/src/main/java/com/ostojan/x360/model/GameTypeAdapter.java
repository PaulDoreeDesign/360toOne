package com.ostojan.x360.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (game.getAvailableRegions() != null) {
            out.name("available_regions").beginArray();
            writeRegionsList(out, game.getAvailableRegions());
            out.endArray();
        }
        if (game.getExcludedRegions() != null) {
            out.name("excluded_regions").beginArray();
            writeRegionsList(out, game.getExcludedRegions());
            out.endArray();
        }
        out.endObject();
    }

    private void writeRegionsList(final JsonWriter out, final List<Region> regions) throws IOException {
        for (Region region : regions) {
            out.beginObject();
            if (region.getId() != null) {
                out.name("id").value(region.getId());
            }
            if (region.getName() != null) {
                out.name("name").value(region.getName());
            }
            if (region.getCode() != null) {
                out.name("code").value(region.getCode());
            }
            out.endObject();
        }
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
                case "available_regions":
                    gameBuilder.availableRegions(readRegionsList(in));
                    break;
                case "excluded_regions":
                    gameBuilder.excludedRegions(readRegionsList(in));
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return gameBuilder.build();
    }

    private List<Region> readRegionsList(final JsonReader in) throws IOException {
        List<Region> regions = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            in.beginObject();
            Region.Builder regionBuilder = new Region.Builder();
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
            regions.add(regionBuilder.build());
            in.endObject();
        }
        in.endArray();
        return regions.size() > 0 ? regions : null;
    }
}
