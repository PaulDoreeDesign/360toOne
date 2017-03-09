package com.ostojan.x360.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GameTypeAdapterTest {

    private Gson gson;

    @Before
    public void setUp() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GameTypeAdapter());
        gson = gsonBuilder.create();
    }

    @Test
    public void createJsonFromSimpleGame() {
        try {
            tryCreateJsonFromSimpleGame();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCreateJsonFromSimpleGame() throws MalformedURLException {
        Game game = GameHelper.createSimpleGame();
        compareJsonWithParserResult(game);
    }

    @Test
    public void createJsonFromGameWithoutId() {
        try {
            tryCreateJsonFromGameWithoutId();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCreateJsonFromGameWithoutId() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutId();
        compareJsonWithParserResult(game);
    }

    @Test
    public void createJsonFromGameWithoutTitle() {
        try {
            tryCreateJsonFromGameWithoutTitle();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCreateJsonFromGameWithoutTitle() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutTitle();
        compareJsonWithParserResult(game);
    }

    @Test
    public void createJsonFromGameWithoutCoverLink() {
        try {
            tryCreateJsonFromGameWithoutCoverLink();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCreateJsonFromGameWithoutCoverLink() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutCoverLink();
        compareJsonWithParserResult(game);
    }

    @Test
    public void createJsonFromGameWithoutStoreLink() {
        try {
            tryCreateJsonFromGameWithoutStoreLink();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCreateJsonFromGameWithoutStoreLink() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutStoreLink();
        compareJsonWithParserResult(game);
    }

    @Test
    public void createJsonFromGameWithoutAvailableRegions() {
        try {
            tryCreateJsonFromGameWithoutAvailableRegions();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCreateJsonFromGameWithoutAvailableRegions() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutAvailableRegions();
        compareJsonWithParserResult(game);
    }

    @Test
    public void createJsonFromGameWithoutExcludedRegions() {
        try {
            tryCreateJsonFromGameWithoutExcludedRegions();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCreateJsonFromGameWithoutExcludedRegions() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutExcludedRegions();
        compareJsonWithParserResult(game);
    }

    @Test
    public void readGameFromJson() {
        try {
            tryReadGameFromJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryReadGameFromJson() throws MalformedURLException {
        Game game = GameHelper.createSimpleGame();
        compareGameWithParserResult(game);
    }

    @Test
    public void readGameWithoutIdFromJson() {
        try {
            tryReadGameWithoutIdFromJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryReadGameWithoutIdFromJson() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutId();
        compareGameWithParserResult(game);
    }

    @Test
    public void readGameWithoutTitleFromJson() {
        try {
            tryReadGameWithoutTitleFromJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryReadGameWithoutTitleFromJson() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutTitle();
        compareGameWithParserResult(game);
    }

    @Test
    public void readGameWithoutCoverLinkFromJson() {
        try {
            tryReadGameWithoutCoverLinkFromJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryReadGameWithoutCoverLinkFromJson() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutCoverLink();
        compareGameWithParserResult(game);
    }

    @Test
    public void readGameWithoutStoreLinkFromJson() {
        try {
            tryReadGameWithoutStoreLinkFromJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryReadGameWithoutStoreLinkFromJson() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutStoreLink();
        compareGameWithParserResult(game);
    }

    @Test
    public void readGameWithoutAvailableRegionsFromJson() {
        try {
            tryReadGameWithoutAvailableRegionsFromJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryReadGameWithoutAvailableRegionsFromJson() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutAvailableRegions();
        compareGameWithParserResult(game);
    }

    @Test
    public void readGameWithoutExcludedRegionsFromJson() {
        try {
            tryReadGameWithoutExcludedRegionsFromJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryReadGameWithoutExcludedRegionsFromJson() throws MalformedURLException {
        Game game = GameHelper.createSimpleGameWithoutExcludedRegions();
        compareGameWithParserResult(game);
    }

    private void compareJsonWithParserResult(Game game) {
        String json = createJsonStringFromGame(game);
        assertEquals("Result json string is not correct", gson.toJson(game), json);
    }

    private void compareGameWithParserResult(Game game) {
        String gameJson = createJsonStringFromGame(game);
        Game resultGame = gson.fromJson(gameJson, Game.class);
        assertEquals("Game object is not correct", game, resultGame);
    }

    private String createJsonStringFromGame(Game game) {
        return createJsonStringFromData(game.getId(),
                game.getTitle(),
                game.getCoverLink() != null ? game.getCoverLink().toString() : null,
                game.getStoreLink() != null ? game.getStoreLink().toString() : null,
                game.getAvailableRegions(),
                game.getExcludedRegions());
    }

    private String createJsonStringFromData(Integer id, String title, String coverLink, String storeLink, List<Region> availableRegion, List<Region> excludedRegion) {
        String separator = "";
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (id != null) {
            json.append(separator);
            json.append("\"id\":");
            json.append(id);
            separator = ",";
        }
        if (title != null) {
            json.append(separator);
            json.append("\"title\":");
            json.append(String.format("\"%s\"", title));
            separator = ",";
        }
        if (coverLink != null) {
            json.append(separator);
            json.append("\"cover_link\":");
            json.append(String.format("\"%s\"", coverLink));
            separator = ",";
        }
        if (storeLink != null) {
            json.append(separator);
            json.append("\"store_link\":");
            json.append(String.format("\"%s\"", storeLink));
            separator = ",";
        }
        if (availableRegion != null) {
            json.append(separator);
            json.append("\"available_regions\":[");
            json.append(createJsonStringFromRegionList(availableRegion));
            json.append("]");
            separator = ",";
        }
        if (excludedRegion != null) {
            json.append(separator);
            json.append("\"excluded_regions\":[");
            json.append(createJsonStringFromRegionList(excludedRegion));
            json.append("]");
            separator = ",";
        }
        json.append("}");
        return json.toString();
    }

    private String createJsonStringFromRegionList(List<Region> regions) {
        String separator = "";
        StringBuilder json = new StringBuilder();
        for (Region region : regions) {
            if (!regionExists(region)) {
                continue;
            }
            String innerSeparator = "";
            json.append(separator);
            json.append("{");
            if (region.getId() != null) {
                json.append(innerSeparator);
                json.append("\"id\":");
                json.append(region.getId());
                innerSeparator = ",";
            }
            if (region.getName() != null) {
                json.append(innerSeparator);
                json.append("\"name\":");
                json.append(String.format("\"%s\"", region.getName()));
                innerSeparator = ",";
            }
            if (region.getCode() != null) {
                json.append(innerSeparator);
                json.append("\"code\":");
                json.append(String.format("\"%s\"", region.getCode()));
                innerSeparator = ",";
            }
            json.append("}");
            separator = ",";
        }
        return json.toString();
    }

    private boolean regionExists(Region region) {
        return region.getId() != null || region.getName() != null || region.getCode() != null;
    }
}