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

    /**
     * Ensure all pits (outside home pits) start with 6 stones
     */
    @Test
    public void sixStartingStones() {
        assertEquals(6, startingPit.getStones());
        assertEquals(6, secondPit.getStones());
    }

    /**
     * Make sure that a selection of pits have their position ids set
     */
    @Test
    public void hasPositionSet() {
        assertNotNull(startingPit.getPosition());
        assertNotNull(secondPit.getPosition());
        assertNotNull(topHomePit.getPosition());
        assertNotNull(bottomHomePit.getPosition());

    }

    /**
     * make sure that the position ids that are set are correct
     */
    @Test
    public void positionsSetCorrectly() {
        assertSame("Pit 2 not positioned correctly", 2L, startingPit.getPosition());
        assertSame("Pit 9 not positioned correctly", 9L, secondPit.getPosition());
        assertSame("Pit 1 not positioned correctly", 1L, topHomePit.getPosition());
        assertSame("Pit 14 not positioned correctly", 14L, bottomHomePit.getPosition());

    }

    /**
     * Make sure that the home pit has started without stones
     */
    @Test
    public void topHomePitHasNoStones() {
        assertEquals("Top home pit has stones", 0, topHomePit.getStones());
    }

    /**
     * Make sure that the home pit has started without stones
     */
    @Test
    public void bottomHomePitHasNoStones() {
        assertEquals("Bottom home pit has stones", 0, bottomHomePit.getStones());
    }

    /**
     * Make sure that the pits have a position
     * so that they can be referenced correctly
     */
    @Test
    public void pitHasPlayerPosition() {
        assertNotNull("Pit 2 has no player position", startingPit.getPosition());
        assertNotNull("Pit 9 has no player position", secondPit.getPosition());
        assertNotNull("Pit 1 has no player position", topHomePit.getPosition());
        assertNotNull("Pit 14 has no player position", bottomHomePit.getPosition());

    }

    /**
     * Ensure that the pits have been given the correct owne
     */
    @Test
    public void pitHasCorrectPlayerPosition() {
        assertEquals("Pit 2  has incorrect player position", PlayerPosition.TOP, startingPit.getOwner());
        assertEquals("Pit 9  has incorrect player position", PlayerPosition.BOTTOM, secondPit.getOwner());
        assertEquals("Pit 1  has incorrect player position", PlayerPosition.TOP, topHomePit.getOwner());
        assertEquals("Pit 14 has incorrect player position", PlayerPosition.BOTTOM, bottomHomePit.getOwner());
    }

    /**
     * Make sure the pits have been given opposites so that
     * they can steal from them if required.
     */
    @Test
    public void pitsHaveOppositeSet() {
        assertNotNull("Pit 2 doesn't have their opposite set", startingPit.getOpposite());
        assertNotNull("Pit 9 doesn't have their opposite set", secondPit.getOpposite());
    }

    /**
     * Confirms that the home pits aren't given opposites
     */
    @Test
    public void homePitsHaveNoOpposite() {
        assertNull("Top Home pit has opposite", topHomePit.getOpposite());
        assertNull("Bottom home put has opposite", bottomHomePit.getOpposite());
    }

    /**
     * Confirm the opposites are set correctly
     */
    @Test
    public void opopsitesAreSetCorrectly() {
        assertSame("Opposites are not set correctly", 8L, startingPit.getOpposite());
        assertSame("Opposites are not set correctly", 3L, secondPit.getOpposite());
    }

    /**
     * Test the adding of a stone to the Pit
     */
    @Test
    public void testAddAStone() {
        assertEquals("Starting stones are not 6", 6, startingPit.getStones());
        startingPit.setStones(1);
        assertEquals("Number of stones hasn't gone up", 7, startingPit.getStones());
    }

    /**
     * Test that negative stones can't be added to the pit
     */
    @Test(expected = KalahException.class)
    public void testAddNegativeStone() {
        startingPit.setStones(-34);
    }

    /**
     * Test that a stone is correctly removed from the pit
     */
    @Test
    public void testRemoveStone() {
        assertEquals("Starting stones are not 6", 6, startingPit.getStones());
        startingPit.removeStone();
        assertEquals("Number of stones hasn't gone down", 5, startingPit.getStones());
    }

    /**
     * Test that a pit that has 0 stones can't be removed from
     */
    @Test
    public void testFailRemoveStone() {
        startingPit.setStoneCount(0);
        assertEquals("Starting stones are not 0", 0, startingPit.getStones());
        startingPit.removeStone();
        assertEquals("Number of stones has gone down", 0, startingPit.getStones());
    }
}