package uk.co.erlski.kalah.model.game;

import org.junit.Before;
import org.junit.Test;

import uk.co.erlski.kalah.model.board.Board;
import uk.co.erlski.kalah.model.board.Pit;
import uk.co.erlski.kalah.model.enums.PlayerPosition;

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
        assertEquals(startingPit.getStones(), 6);
        assertEquals(secondPit.getStones(), 6);
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
        assertSame(startingPit.getPosition(), 2L);
        assertSame(secondPit.getPosition(), 9L);
        assertSame(topHomePit.getPosition(), 1L);
        assertSame(bottomHomePit.getPosition(), 14L);

    }

    @Test
    public void topHomePitHasNoStones() {
        assertEquals(topHomePit.getStones(), 0);
    }

    @Test
    public void bottomHomePitHasNoStones() {
        assertEquals(bottomHomePit.getStones(), 0);
    }

    @Test
    public void pitHasPlayerPosition() {
        assertNotNull("The pit has a player position", startingPit.getPosition());
        assertNotNull("The pit has a player position", secondPit.getPosition());
        assertNotNull("The pit has a player position", topHomePit.getPosition());
        assertNotNull("The pit has a player position", bottomHomePit.getPosition());

    }

    @Test
    public void pitHasCorrectPlayerPosition() {
        assertEquals(startingPit.getOwner(), PlayerPosition.TOP);
        assertEquals(secondPit.getOwner(), PlayerPosition.BOTTOM);
        assertEquals(topHomePit.getOwner(), PlayerPosition.TOP);
        assertEquals(bottomHomePit.getOwner(), PlayerPosition.BOTTOM);
    }

    @Test
    public void pitsHaveOppositeSet() {
        assertNotNull(startingPit.getOpposite());
        assertNotNull(secondPit.getOpposite());
    }

    @Test
    public void homePitsHaveNoOpposite() {
        assertNull(topHomePit.getOpposite());
        assertNull(bottomHomePit.getOpposite());
    }

    @Test
    public void opopsitesAreSetCorrectly() {
        assertSame(startingPit.getOpposite(), 8L );
    }

    @Test
    public void addAStone() {

    }

    @Test
    public void addNegativeStone() {

    }
}