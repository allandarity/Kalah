package uk.co.erlski.kalah.model.pit;

import org.junit.Before;
import org.junit.Test;

import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.board.Board;
import uk.co.erlski.kalah.model.board.Pit;
import uk.co.erlski.kalah.model.enums.PlayerPosition;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;

import static org.junit.Assert.*;

public class PitTest {

    private GameHandler gameHandler;
    private Board board;
    private Game game;
    private Pit startingPit, secondPit, topHomePit, bottomHomePit;

    @Before
    public void setUp() throws Exception {
        final long gameId = 1L;
        gameHandler = new GameHandler();
        gameHandler.addGame(gameId);
        game = gameHandler.getValidGame(gameId);
        board = game.getBoard();
        startingPit = board.getPit(2L);
        secondPit = board.getPit(9L);
        topHomePit = board.getPit(1L);
        bottomHomePit = board.getPit(14L);

    }

    @Test
    public void sixStartingStones() {
        assertEquals(6, startingPit.getStones());
        assertEquals(6, secondPit.getStones());
    }

    @Test
    public void hasPositionSet() {
        assertNotNull(startingPit.getPosition());
        assertNotNull(secondPit.getPosition());
        assertNotNull(topHomePit.getPosition());
        assertNotNull(bottomHomePit.getPosition());

    }

    @Test
    public void positionsSetCorrectly() {
        assertSame("Pit 2 not positioned correctly", 2L, startingPit.getPosition());
        assertSame("Pit 9 not positioned correctly", 9L, secondPit.getPosition());
        assertSame("Pit 1 not positioned correctly", 1L, topHomePit.getPosition());
        assertSame("Pit 14 not positioned correctly", 14L, bottomHomePit.getPosition());

    }

    @Test
    public void topHomePitHasNoStones() {
        assertEquals("Top home pit has stones", 0, topHomePit.getStones());
    }

    @Test
    public void bottomHomePitHasNoStones() {
        assertEquals("Bottom home pit has stones", 0, bottomHomePit.getStones());
    }

    @Test
    public void pitHasPlayerPosition() {
        assertNotNull("Pit 2 has no player position", startingPit.getPosition());
        assertNotNull("Pit 9 has no player position", secondPit.getPosition());
        assertNotNull("Pit 1 has no player position", topHomePit.getPosition());
        assertNotNull("Pit 14 has no player position", bottomHomePit.getPosition());

    }

    @Test
    public void pitHasCorrectPlayerPosition() {
        assertEquals("Pit 2  has incorrect player position", PlayerPosition.TOP, startingPit.getOwner());
        assertEquals("Pit 9  has incorrect player position", PlayerPosition.BOTTOM, secondPit.getOwner());
        assertEquals("Pit 1  has incorrect player position", PlayerPosition.TOP, topHomePit.getOwner());
        assertEquals("Pit 14 has incorrect player position", PlayerPosition.BOTTOM, bottomHomePit.getOwner());
    }

    @Test
    public void pitsHaveOppositeSet() {
        assertNotNull("Pit 2 doesn't have their opposite set", startingPit.getOpposite());
        assertNotNull("Pit 9 doesn't have their opposite set", secondPit.getOpposite());
    }

    @Test
    public void homePitsHaveNoOpposite() {
        assertNull("Top Home pit has opposite", topHomePit.getOpposite());
        assertNull("Bottom home put has opposite", bottomHomePit.getOpposite());
    }

    @Test
    public void opopsitesAreSetCorrectly() {
        assertSame("Opposites are not set correctly", 8L, startingPit.getOpposite());
        assertSame("Opposites are not set correctly", 3L, secondPit.getOpposite());
    }

    @Test
    public void addAStone() {
        assertEquals("Starting stones are not 6", 6, startingPit.getStones());
        startingPit.setStones(1);
        assertEquals("Number of stones hasn't gone up", 7, startingPit.getStones());
    }

    @Test(expected = KalahException.class)
    public void addNegativeStone() {
        startingPit.setStones(-34);
    }
}