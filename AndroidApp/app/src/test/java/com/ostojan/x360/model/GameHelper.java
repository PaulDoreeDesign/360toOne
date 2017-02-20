package com.ostojan.x360.model;

import java.net.MalformedURLException;

public class GameHelper {

    public static final Integer ID = 1;
    public static final String TITLE = "Game";
    public static final String COVER_LINK = "http://www.game.com/cover.jpg";
    public static final String STORE_LINK = "http://www.game.com/store/game/";

    public static Game createSimpleGame() throws MalformedURLException {
        return createGameFromData(ID, TITLE, COVER_LINK, STORE_LINK);
    }

    public static Game createSimpleGameWithoutId() throws MalformedURLException {
        return createGameFromData(null, TITLE, COVER_LINK, STORE_LINK);
    }

    public static Game createSimpleGameWithoutTitle() throws MalformedURLException {
        return createGameFromData(ID, null, COVER_LINK, STORE_LINK);
    }

    public static Game createSimpleGameWithoutCoverLink() throws MalformedURLException {
        return createGameFromData(ID, TITLE, null, STORE_LINK);
    }

    public static Game createSimpleGameWithoutStoreLink() throws MalformedURLException {
        return createGameFromData(ID, TITLE, COVER_LINK, null);
    }

    public static Game createGameFromData(Integer id, String title, String coverLink, String storeLink) throws MalformedURLException {
        Game.Builder gameBuilder = new Game.Builder();
        if (id != null) {
            gameBuilder.id(id);
        }
        if (title != null) {
            gameBuilder.title(title);
        }
        if (coverLink != null) {
            gameBuilder.coverLink(coverLink);
        }
        if (storeLink != null) {
            gameBuilder.storeLink(storeLink);
        }
        return gameBuilder.build();
    }
}
