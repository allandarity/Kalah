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


    @Test
    public void checkBoardExists() {
        assertNotNull("Game board doesn't exist", game.getBoard());
    }

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

    @Test
    public void testPlayMoveRoundTheBoard() {
        final Pit startingPit = board.getPit(7L);
        assertEquals("Pit 7 didn't start with 6 stones", startingPit.getStones(), 6);
        game.playMove(startingPit.getPosition());
        assertEquals("Pit 8 didn't gain a stone", 7, board.getPit(8L).getStones());
        assertEquals("Pit 9 didn't gain a stone", 7, board.getPit(9L).getStones());
        assertEquals("Pit 10 didn't gain a stone",7, board.getPit(10L).getStones());
        assertEquals("Pit 11 didn't gain a stone",7, board.getPit(11L).getStones());
        assertEquals("Pit 12 didn't gain a stone",7, board.getPit(12L).getStones());
        assertEquals("Pit 13 didn't gain a stone",7, board.getPit(13L).getStones());
        assertEquals("Pit 7 didn't end with 0 stones", 0, startingPit.getStones());
    }

    @Test
    public void skipHomePitWhenMovingRound() {
        final Pit startingPit = board.getPit(13L);
        game.setLastPlayed(PlayerPosition.BOTTOM); //Bottom needs to be set to go first for 13
        startingPit.setStoneCount(3);
        assertEquals("Pit 13 didn't start with 3 stones",3, startingPit.getStones());
        game.playMove(startingPit.getPosition());
        assertEquals("Pit 2 didn't gain a stone", 7,  board.getPit(2L).getStones());
        assertEquals("Pit 3 didn't gain a stone", 7, board.getPit(3L).getStones());
        assertEquals("Pit 4 didn't gain a stone", 7, board.getPit(4L).getStones());
        assertEquals("Pit 1 gained a stone", 0, board.getPit(1L).getStones());

    }

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
        assertEquals("Pit 1 didn't end with 6 stones", 6, homePit.getStones());
        assertEquals("Pit 8 didn't end with 0 stones", 0, stolenPit.getStones());
    }

    @Test(expected = KalahException.class)
    public void testTopGoesFirst() {
        final Pit startingPit = board.getPit(12L);
        game.playMove(startingPit.getPosition());
    }

    @Test
    public void testTopMoveAfterBottom() {
        final Pit startingPit = board.getPit(2L);
        final Pit secondPit = board.getPit(12L);

        assertEquals("Starting move wasn't top", PlayerPosition.TOP, game.getLastPlayed());
        game.playMove(startingPit.getPosition());
        assertEquals("Second move wasn't bottoms", PlayerPosition.BOTTOM, game.getLastPlayed());

    }

    @Test(expected = KalahException.class)
    public void testCantMoveTwice() {
        final Pit startingPit = board.getPit(2L);
        final Pit secondPit = board.getPit(3L);

        assertEquals("Starting move wasn't top", PlayerPosition.TOP, game.getLastPlayed());
        game.playMove(startingPit.getPosition());
        game.playMove(secondPit.getPosition());
    }

    @Test
    public void testGameHasEnded() {
        final Pit topHome = board.getHomePit(PlayerPosition.TOP);
        final Pit bottomHome = board.getHomePit(PlayerPosition.BOTTOM);

        topHome.setStoneCount(36);
        bottomHome.setStoneCount(36);

        final Pit startingPit = board.getPit(2L);
        startingPit.setStoneCount(1);
        game.playMove(startingPit.getPosition());

        assertEquals(GameState.FINISHED, game.getGameState());

    }

}
