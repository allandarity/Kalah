package uk.co.erlski.kalah.model.board;

import org.junit.Before;
import org.junit.Test;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.enums.PlayerPosition;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;

import static org.junit.Assert.*;

public class BoardTest {

    private GameHandler gameHandler;
    private Board board;
    private Game game;

    @Before
    public void setUp() throws Exception {
        final long gameId = 1L;
        gameHandler = new GameHandler();
        gameHandler.addGame(gameId);
        game = gameHandler.getValidGame(gameId);
        board = game.getBoard();
        board.setupBoard();
    }

    /**
     * Make sure that the board was correctly set up
     * on the {@link Game} setup
     */
    @Test
    public void testSetupBoard() {
        assertNotNull(game.getBoard());
        assertNotNull(board.getPit(3L));
        assertNotNull(board.getPit(12L));
        assertNotNull(board.getPit(6L));
    }

    /**
     * Make sure that the board is empties when cleared
     */
    @Test(expected = KalahException.class)
    public void testClearBoard() {
        assertNotNull(board.getPit(3L));
        assertNotNull(board.getPit(12L));
        assertNotNull(board.getPit(6L));
        board.clearBoard();
        assertNull(board.getPit(3L));
        assertNull(board.getPit(12L));
        assertNull(board.getPit(6L));
    }

    /**
     * Return a valid pit by its id
     */
    @Test
    public void testGetPit() {
        assertNotNull(board.getPit(3L));
        assertNotNull(board.getPit(12L));
        assertNotNull(board.getPit(6L));
    }

    /**
     * Fail if trying to get a pit outside the range
     */
    @Test(expected = KalahException.class)
    public void testGetNonPit() {
        assertNull(board.getPit(35L));
    }

    /**
     * Return the players home pit based on their {@link PlayerPosition}
     */
    @Test
    public void testGetHomePit() {
        assertNotNull(board.getHomePit(PlayerPosition.TOP));
        assertNotNull(board.getHomePit(PlayerPosition.BOTTOM));
    }

    /**
     * Make sure that the home pit that is returned
     * is correct for the player
     */
    @Test
    public void testHomePitIsCorrect() {
        final Pit topHome = board.getHomePit(PlayerPosition.TOP);
        final Pit bottomHome = board.getHomePit(PlayerPosition.BOTTOM);

        assertEquals((long) topHome.getPosition(), 1L);
        assertEquals((long) bottomHome.getPosition(), 14L);
    }
}