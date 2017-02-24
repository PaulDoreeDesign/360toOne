package com.ostojan.x360.model;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void checkGetters() {
        try {
            tryCheckGetters();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCheckGetters() throws MalformedURLException {
        Game game = GameHelper.createSimpleGame();
        assertEquals("ID is not correct", GameHelper.ID, game.getId());
        assertEquals("Title is not correct", GameHelper.TITLE, game.getTitle());
        assertEquals("Cover's link is not correct", new URL(GameHelper.COVER_LINK), game.getCoverLink());
        assertEquals("Store's link is not correct", new URL(GameHelper.STORE_LINK), game.getStoreLink());
    }

    @Test
    public void compareTwoSameGames() {
        try {
            tryCompareTwoSameGames();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCompareTwoSameGames() throws MalformedURLException {
        Game game1 = GameHelper.createSimpleGame();
        Game game2 = GameHelper.createSimpleGame();
        assertEquals("Games are not equals", game1, game2);
        assertEquals("Games are not equals", game2, game1);
    }

    @Test
    public void compareGameToItself() {
        try {
            tryCompareGameToItself();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCompareGameToItself() throws MalformedURLException {
        Game game = GameHelper.createSimpleGame();
        assertEquals("Game is not equals to itself", game, game);
    }

    @Test
    public void compareTwoGamesWithDifferentIds() {
        try {
            tryCompareTwoGamesWithDifferentIds();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCompareTwoGamesWithDifferentIds() throws MalformedURLException {
        Integer id1 = 1;
        Integer id2 = 2;
        Game game1 = GameHelper.createGameFromData(id1, GameHelper.TITLE, GameHelper.COVER_LINK, GameHelper.STORE_LINK);
        Game game2 = GameHelper.createGameFromData(id2, GameHelper.TITLE, GameHelper.COVER_LINK, GameHelper.STORE_LINK);
        assertNotEquals("Games are equals", game1, game2);
        assertNotEquals("Games are equals", game2, game1);
    }

    @Test
    public void compareTwoGamesWithDifferentTitles() {
        try {
            tryCompareTwoGamesWithDifferentTitles();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCompareTwoGamesWithDifferentTitles() throws MalformedURLException {
        String title1 = "Game1";
        String title2 = "Game2";
        Game game1 = GameHelper.createGameFromData(GameHelper.ID, title1, GameHelper.COVER_LINK, GameHelper.STORE_LINK);
        Game game2 = GameHelper.createGameFromData(GameHelper.ID, title2, GameHelper.COVER_LINK, GameHelper.STORE_LINK);
        assertNotEquals("Games are equals", game1, game2);
        assertNotEquals("Games are equals", game2, game1);
    }

    @Test
    public void compareTwoGamesWithDifferentCoverLinks() {
        try {
            tryCompareTwoGamesWithDifferentCoverLinks();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCompareTwoGamesWithDifferentCoverLinks() throws MalformedURLException {
        String coverLink1 = "http://www.game.com/cover1.jpg";
        String coverLink2 = "http://www.game.com/cover2.jpg";
        Game game1 = GameHelper.createGameFromData(GameHelper.ID, GameHelper.TITLE, coverLink1, GameHelper.STORE_LINK);
        Game game2 = GameHelper.createGameFromData(GameHelper.ID, GameHelper.TITLE, coverLink2, GameHelper.STORE_LINK);
        assertNotEquals("Games are equals", game1, game2);
        assertNotEquals("Games are equals", game2, game1);
    }

    @Test
    public void compareTwoGamesWithDifferentStoreLinks() {
        try {
            tryCompareTwoGamesWithDifferentStoreLinks();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("Couldn't create URL for Game object");
        }
    }

    private void tryCompareTwoGamesWithDifferentStoreLinks() throws MalformedURLException {
        String storeLink1 = "http://www.game.com/store/game1/";
        String storeLink2 = "http://www.game.com/store/game2/";
        Game game1 = GameHelper.createGameFromData(GameHelper.ID, GameHelper.TITLE, GameHelper.COVER_LINK, storeLink1);
        Game game2 = GameHelper.createGameFromData(GameHelper.ID, GameHelper.TITLE, GameHelper.COVER_LINK, storeLink2);
        assertNotEquals("Games are equals", game1, game2);
        assertNotEquals("Games are equals", game2, game1);
    }
}