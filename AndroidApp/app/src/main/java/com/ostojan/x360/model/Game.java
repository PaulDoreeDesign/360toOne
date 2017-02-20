package com.ostojan.x360.model;

import java.net.MalformedURLException;
import java.net.URL;

public class Game {
    private Integer id;
    private String title;
    private URL coverLink;
    private URL storeLink;

    public Game() {
        id = null;
        title = null;
        coverLink = null;
        storeLink = null;
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

        public Game build() {
            return game;
        }
    }
}
