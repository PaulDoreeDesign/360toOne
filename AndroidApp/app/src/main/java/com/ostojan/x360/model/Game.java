package com.ostojan.x360.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Game {
    private Integer id;
    private String title;
    private URL coverLink;
    private URL storeLink;
    private List<Region> availableRegions;
    private List<Region> excludedRegions;

    public Game() {
        id = null;
        title = null;
        coverLink = null;
        storeLink = null;
        availableRegions = null;
        excludedRegions = null;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public URL getCoverLink() {
        return coverLink;
    }

    public URL getStoreLink() {
        return storeLink;
    }

    public List<Region> getAvailableRegions() {
        return availableRegions;
    }

    public List<Region> getExcludedRegions() {
        return excludedRegions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Game game = (Game) o;

        if (id != null ? !id.equals(game.id) : game.id != null) {
            return false;
        }
        if (title != null ? !title.equals(game.title) : game.title != null) {
            return false;
        }
        if (coverLink != null ? !coverLink.equals(game.coverLink) : game.coverLink != null) {
            return false;
        }
        if (storeLink != null ? !storeLink.equals(game.storeLink) : game.storeLink != null) {
            return false;
        }
        if (availableRegions != null ? !availableRegions.equals(game.availableRegions) : game.availableRegions != null) {
            return false;
        }
        return excludedRegions != null ? excludedRegions.equals(game.excludedRegions) : game.excludedRegions == null;
    }

    public static class Builder {
        private Game game;

        public Builder() {
            this.game = new Game();
        }

        public Builder id(int id) {
            game.id = id;
            return this;
        }

        public Builder title(String title) {
            game.title = title;
            return this;
        }

        public Builder coverLink(String coverLink) throws MalformedURLException {
            game.coverLink = new URL(coverLink);
            return this;
        }

        public Builder coverLink(URL coverLink) {
            game.coverLink = coverLink;
            return this;
        }

        public Builder storeLink(String storeLink) throws MalformedURLException {
            game.storeLink = new URL(storeLink);
            return this;
        }

        public Builder storeLink(URL storeLink) {
            game.storeLink = storeLink;
            return this;
        }

        public Builder availableRegions(List<Region> regions) {
            game.availableRegions = regions;
            return this;
        }

        public Builder excludedRegions(List<Region> regions) {
            game.excludedRegions = regions;
            return this;
        }

        public Game build() {
            return game;
        }
    }
}
