package uk.co.erlski.kalah.model.game;

import org.junit.Before;
import org.junit.Test;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.board.Board;
import uk.co.erlski.kalah.model.board.Pit;
import uk.co.erlski.kalah.model.enums.GameState;
import uk.co.erlski.kalah.model.enums.PlayerPosition;

import static org.junit.Assert.*;

public class GameTest {

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
     * Check to make sure the board exists at the time the game is created
     */
    @Test
    public void checkBoardExists() {
        assertNotNull("Game board doesn't exist", game.getBoard());
    }

    /**
     * Make sure that the stones are removed from the inital stone store after a move
     */
    @Test
    public void testPlayMoveCountStonesAfterMove() {
        final Pit startingPit = board.getPit(2L);
        startingPit.setStoneCount(3);
        assertEquals("Pit 2 didn't start with 3 stones", 3, startingPit.getStones());
        game.playMove(startingPit.getPosition());
        assertEquals("Pit 3 didn't gain a stone", 7, board.getPit(3L).getStones());
        assertEquals("Pit 4 didn't gain a stone", 7, board.getPit(4L).getStones());
        assertEquals("Pit 5 didn't gain a stone", 7, board.getPit(5L).getStones());
        assertEquals("Pit 2 didn't end with 0 stones", 0, startingPit.getStones());
    }

    /**
     * Make sure that the stones can move around the board properly i.e. moving from bottom to top row
     */
    @Test
    public void testPlayMoveRoundTheBoard() {
        final Pit startingPit = board.getPit(7L);
        assertEquals("Pit 7 didn't start with 6 stones", startingPit.getStones(), 6);
        game.playMove(startingPit.getPosition());
        assertEquals("Pit 8 gained a stone", 7, board.getPit(8L).getStones());
        assertEquals("Pit 9 gained gain a stone", 7, board.getPit(9L).getStones());
        assertEquals("Pit 10 gained gain a stone",7, board.getPit(10L).getStones());
        assertEquals("Pit 11 gained gain a stone",7, board.getPit(11L).getStones());
        assertEquals("Pit 12 gained gain a stone",7, board.getPit(12L).getStones());
        assertEquals("Pit 13 gained gain a stone",7, board.getPit(13L).getStones());
        assertEquals("Pit 7 ended with 6 stones", 0, startingPit.getStones());
    }

    /**
     * Make sure that if the stone goes past its own store it drops its stone off
     */
    @Test
    public void testPlaceInOwnStore() {
        final Pit startingPit = board.getPit(13L);
        game.setLastPlayed(PlayerPosition.BOTTOM); //Bottom needs to be set to go first for 13
        startingPit.setStoneCount(3);
        assertEquals("Pit 13 didn't start with 3 stones",3, startingPit.getStones());
        game.playMove(startingPit.getPosition());
        assertEquals("Pit 14 didn't gain a stone", 1,  board.getPit(14L).getStones());
        assertEquals("Pit 1 gained a stone", 0,  board.getPit(1L).getStones());
        assertEquals("Pit 2 didn't gain a stone", 7,  board.getPit(2L).getStones());
        assertEquals("Pit 3 didn't gain a stone", 7, board.getPit(3L).getStones());
        assertEquals("Pit 4 didn't gain a stone", 6, board.getPit(4L).getStones());
        assertEquals("Pit 13 ended on incorrect stones", 0, startingPit.getStones());

    }

    /**
     * Make sure that when the last stone is placed into an empty store that the opposite
     * players stones are stolen
     */
    @Test
    public void testStealStone() {
        final Pit startingPit = board.getPit(2L);
        final Pit nextPit = board.getPit(3L);
        final Pit homePit = board.getPit(1L);
        final Pit stolenPit = board.getPit(9L);

        startingPit.setStoneCount(1);
        nextPit.setStoneCount(0);

        assertEquals("Pit 2 didn't start with 1 stone", 1, startingPit.getStones());
        assertEquals("Pit 3 didn't start with 0 stones", 0, nextPit.getStones());
        assertEquals("Pit 1 didn't start with 0 stones", 0, homePit.getStones());
        assertEquals("Pit 8 didn't start with 6 stones", 6, stolenPit.getStones());

        game.playMove(startingPit.getPosition());
        assertEquals("Pit 2 didn't end with 0 stone", 0, startingPit.getStones());
        assertEquals("Pit 3 didn't end with 1 stones", 1,nextPit.getStones());
        assertEquals("Pit 1 didn't end with 6 stones", 7, homePit.getStones());
        assertEquals("Pit 8 didn't end with 0 stones", 0, stolenPit.getStones());
    }

    /**
     * Make sure that TOP plays first
     */
    @Test(expected = KalahException.class)
    public void testTopGoesFirst() {
        final Pit startingPit = board.getPit(12L);
        game.playMove(startingPit.getPosition());
    }

    /**
     * Make sure that the turn priority changes
     */
    @Test
    public void testTopMoveAfterBottom() {
        final Pit startingPit = board.getPit(2L);

        assertEquals("Starting move wasn't top", PlayerPosition.TOP, game.getLastPlayed());
        game.playMove(startingPit.getPosition());
        assertEquals("Second move wasn't bottoms", PlayerPosition.BOTTOM, game.getLastPlayed());

    }

    /**
     * Make sure that a player can't play twice in a row
     */
    @Test(expected = KalahException.class)
    public void testCantMoveTwice() {
        final Pit startingPit = board.getPit(2L);
        final Pit secondPit = board.getPit(3L);

        assertEquals("Starting move wasn't top", PlayerPosition.TOP, game.getLastPlayed());
        game.playMove(startingPit.getPosition());
        game.playMove(secondPit.getPosition());
    }

    /**
     * Confirm that the TOP row player can't make a move
     * on a bottom rows stones and vice-versa
     */
    @Test(expected = KalahException.class)
    public void testCantPlayOtherPersonsStones() {
        final Pit startingPit = board.getPit(2L);
        game.setLastPlayed(PlayerPosition.BOTTOM);
        game.playMove(startingPit.getPosition());
    }

    /**
     * Check that the game state is moved to finished once a winner has been
     * decided
     */
    @Test
    public void testGameHasEnded() {
        final Pit topHome = board.getHomePit(PlayerPosition.TOP);
        final Pit bottomHome = board.getHomePit(PlayerPosition.BOTTOM);

        topHome.setStoneCount(36);
        bottomHome.setStoneCount(24);

        board.getPit(3L).setStoneCount(0);
        board.getPit(4L).setStoneCount(0);
        board.getPit(5L).setStoneCount(0);
        board.getPit(6L).setStoneCount(0);
        board.getPit(7L).setStoneCount(0);
        board.getPit(8L).setStoneCount(0);
        board.getPit(9L).setStoneCount(0);
        board.getPit(10L).setStoneCount(0);
        board.getPit(11L).setStoneCount(0);
        board.getPit(12L).setStoneCount(0);
        board.getPit(13L).setStoneCount(0);


        final Pit startingPit = board.getPit(2L);
        startingPit.setStoneCount(1);
        game.playMove(startingPit.getPosition());

        assertEquals(GameState.FINISHED, game.getGameState());

    }

    /**
     * Make sure that if you play a move that gives you another
     * go you are given that go
     */
    @Test
    public void testGetASecondGo() {
        final Pit startingPit = board.getPit(13L);
        final Pit secondGo = board.getPit(8L);
        game.setLastPlayed(PlayerPosition.BOTTOM);
        startingPit.setStoneCount(1);
        secondGo.setStoneCount(3);
        game.playMove(startingPit.getPosition());
        assertEquals(PlayerPosition.BOTTOM, game.getLastPlayed());
        game.playMove(secondGo.getPosition());
        assertEquals("Second go didn't happen", 0, secondGo.getStones());
        game.playMove(2L);

    }
}
