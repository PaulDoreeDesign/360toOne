package com.ostojan.x360.model;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class GameHelper {

    public static final Integer ID = 1;
    public static final String TITLE = "Game";
    public static final String COVER_LINK = "http://www.game.com/cover.jpg";
    public static final String STORE_LINK = "http://www.game.com/store/game/";
    public static final List<Region> AVAILABLE_REGIONS = new ArrayList<Region>() {{
        add(RegionHelper.createSimpleRegion());
    }};
    public static final List<Region> EXCLUDED_REGIONS = new ArrayList<Region>() {{
        add(RegionHelper.createSimpleRegion());
    }};


    public static Game createSimpleGame() throws MalformedURLException {
        return createGameFromData(ID, TITLE, COVER_LINK, STORE_LINK, AVAILABLE_REGIONS, EXCLUDED_REGIONS);
    }

    public static Game createSimpleGameWithoutId() throws MalformedURLException {
        return createGameFromData(null, TITLE, COVER_LINK, STORE_LINK, AVAILABLE_REGIONS, EXCLUDED_REGIONS);
    }

    public static Game createSimpleGameWithoutTitle() throws MalformedURLException {
        return createGameFromData(ID, null, COVER_LINK, STORE_LINK, AVAILABLE_REGIONS, EXCLUDED_REGIONS);
    }

    public static Game createSimpleGameWithoutCoverLink() throws MalformedURLException {
        return createGameFromData(ID, TITLE, null, STORE_LINK, AVAILABLE_REGIONS, EXCLUDED_REGIONS);
    }

    public static Game createSimpleGameWithoutStoreLink() throws MalformedURLException {
        return createGameFromData(ID, TITLE, COVER_LINK, null, AVAILABLE_REGIONS, EXCLUDED_REGIONS);
    }

    public static Game createSimpleGameWithoutAvailableRegions() throws MalformedURLException {
        return createGameFromData(ID, TITLE, COVER_LINK, STORE_LINK, null, EXCLUDED_REGIONS);
    }

    public static Game createSimpleGameWithoutExcludedRegions() throws MalformedURLException {
        return createGameFromData(ID, TITLE, COVER_LINK, STORE_LINK, AVAILABLE_REGIONS, null);
    }

    public static Game createGameFromData(Integer id, String title, String coverLink, String storeLink, List<Region> availableRegions, List<Region> excludedRegions) throws MalformedURLException {
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
        if (availableRegions != null) {
            gameBuilder.availableRegions(availableRegions);
        }
        if (excludedRegions != null) {
            gameBuilder.excludedRegions(excludedRegions);
        }
        return gameBuilder.build();
    }
}
