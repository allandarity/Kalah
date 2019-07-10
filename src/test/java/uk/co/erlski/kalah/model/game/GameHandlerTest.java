package uk.co.erlski.kalah.model.game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.enums.GameState;

import static org.junit.Assert.*;

public class GameHandlerTest {

    private GameHandler gameHandler;
    @Before
    public void setUp() throws Exception {
        gameHandler = new GameHandler();
        gameHandler.addGame(1L);
    }

    /**
     * Make sure that a game is added correctly
     */
    @Test
    public void testAddGame() {
        gameHandler.addGame(2L);
        Assert.assertNotNull("Game 1 not created", gameHandler.getValidGame(1L));
        Assert.assertNotNull("Game 2 not created", gameHandler.getValidGame(2L));
    }

    /**
     * Check that 2 games with the same id can't be created
     */
    @Test(expected = KalahException.class)
    public void testAddGameWithSameId() {
        Assert.assertNotNull("Game 1 doesn't exist", gameHandler.getValidGame(1L));
        gameHandler.addGame(1L);
    }

    /**
     * Test that a finished game can be replaced if the id is reused
     */
    @Test
    public void testAddGameByReplacingId() {
        Long gameId = 1L;
        Game oldGame = gameHandler.getValidGame(gameId);

        oldGame.setGameState(GameState.FINISHED);
        gameHandler.addGame(gameId);
        Game newGame = gameHandler.getValidGame(gameId);
        assertEquals("Game state not set to live", GameState.LIVE, newGame.getGameState());
    }

    /**
     * Confirm that a failure occurs if attempting to replace
     * a game that is live with the same id
     */
    @Test(expected = KalahException.class)
    public void testFailAddGameByAttemptedReplacementId() {
        Long gameId = 1L;
        Game oldGame = gameHandler.getValidGame(gameId);

        oldGame.setGameState(GameState.LIVE);
        gameHandler.addGame(gameId);
    }


    /**
     * Make sure that a negative value game can't be made
     */
    @Test(expected = KalahException.class)
    public void testAddGameWithNegativeID() {
        gameHandler.addGame(-5L);
    }

    /**
     * Test that you can return a game with a valid id
     */
    @Test
    public void testGetValidGame() {
        assertNotNull("Game 1 doesn't exist", gameHandler.getValidGame(1L));
    }

    /**
     * Test that you will fail trying to get a game with a null id
     */
    @Test(expected = KalahException.class)
    public void testAttemptToGetGameWithNullID() {
        gameHandler.getValidGame(null);
    }

    /**
     * Trying to get a game that doesn't exist will fail
     */
    @Test(expected = KalahException.class)
    public void testAttemptToGetGameThatDoesntExist() {
        gameHandler.getValidGame(3L);
    }

    /**
     * Once a game is finished it is removed from the map
     */
    @Test
    public void testRemoveFinishedGame() {
        Game game = gameHandler.getValidGame(1L);
        game.setGameState(GameState.FINISHED);
        assertTrue("Finished game not removed", gameHandler.removeFinishedGame(1L));
    }

    /**
     * Make sure the map no longer contains the removed game
     */
    @Test
    public void testCheckMapRemovedFinishedGame() {
        Game game = gameHandler.getValidGame(1L);
        game.setGameState(GameState.FINISHED);
        gameHandler.removeFinishedGame(1L);
        assertFalse("Map hasn't cleared finished game", gameHandler.getGamesMap().containsKey(1L));
    }

    @After
    public void tearDown() throws Exception {
    }
}