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

    @Test
    public void testSetupBoard() {
        assertNotNull(game.getBoard());
        assertNotNull(board.getPit(3L));
        assertNotNull(board.getPit(12L));
        assertNotNull(board.getPit(6L));
    }

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

    @Test
    public void testGetPit() {
        assertNotNull(board.getPit(3L));
        assertNotNull(board.getPit(12L));
        assertNotNull(board.getPit(6L));
    }

    @Test(expected = KalahException.class)
    public void testGetNonPit() {
        assertNull(board.getPit(35L));
    }

    @Test
    public void testGetHomePit() {
        assertNotNull(board.getHomePit(PlayerPosition.TOP));
        assertNotNull(board.getHomePit(PlayerPosition.BOTTOM));
    }

    @Test
    public void testHomePitIsCorrect() {
        final Pit topHome = board.getHomePit(PlayerPosition.TOP);
        final Pit bottomHome = board.getHomePit(PlayerPosition.BOTTOM);

        assertEquals((long) topHome.getPosition(), 1L);
        assertEquals((long) bottomHome.getPosition(), 14L);
    }
}