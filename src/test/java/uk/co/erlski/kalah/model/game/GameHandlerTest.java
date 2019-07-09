package uk.co.erlski.kalah.model.game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.erlski.kalah.exception.KalahException;

import static org.junit.Assert.*;

public class GameHandlerTest {

    private GameHandler gameHandler;
    @Before
    public void setUp() throws Exception {
        gameHandler = new GameHandler();
        gameHandler.addGame(1L);
    }

    @Test
    public void addGame() {
        gameHandler.addGame(2L);
        Assert.assertNotNull(gameHandler.getValidGame(1L));
        Assert.assertNotNull(gameHandler.getValidGame(2L));
    }

    @Test(expected = KalahException.class)
    public void addGameWithSameId() {
        Assert.assertNotNull(gameHandler.getValidGame(1L));
        gameHandler.addGame(1L);
    }

    @Test
    public void addGameByReplacingId() {
        Long gameId = 1L;
        Game oldGame = gameHandler.getValidGame(gameId);

        oldGame.setGameState(GameState.FINISHED);
        gameHandler.addGame(gameId);
        Game newGame = gameHandler.getValidGame(gameId);
        assertEquals(GameState.LIVE, newGame.getGameState());
    }

    @Test(expected = KalahException.class)
    public void failAddGameByAttemptedReplacementId() {
        Long gameId = 1L;
        Game oldGame = gameHandler.getValidGame(gameId);

        oldGame.setGameState(GameState.LIVE);
        gameHandler.addGame(gameId);
    }


    @Test(expected = KalahException.class)
    public void addGameWithNegativeID() {
        gameHandler.addGame(-5L);
    }

    @Test
    public void getValidGame() {
        assertNotNull(gameHandler.getValidGame(1L));
    }

    @Test(expected = KalahException.class)
    public void attemptToGetGameWithNullID() {
        gameHandler.getValidGame(null);
    }

    @Test(expected = KalahException.class)
    public void attemptToGetGameThatDoesntExist() {
        gameHandler.getValidGame(3L);
    }

    @Test
    public void removeFinishedGame() {
        Game game = gameHandler.getValidGame(1L);
        game.setGameState(GameState.FINISHED);
        assertTrue(gameHandler.removeFinishedGame(1L));
    }

    @Test
    public void checkMapRemovedFinishedGame() {
        Game game = gameHandler.getValidGame(1L);
        game.setGameState(GameState.FINISHED);
        gameHandler.removeFinishedGame(1L);
        assertFalse(gameHandler.getGamesMap().containsKey(1L));
    }

    @After
    public void tearDown() throws Exception {
    }
}