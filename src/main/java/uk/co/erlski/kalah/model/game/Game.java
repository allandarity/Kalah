package uk.co.erlski.kalah.model.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.board.Board;
import uk.co.erlski.kalah.model.board.Pit;
import uk.co.erlski.kalah.model.enums.GameState;
import uk.co.erlski.kalah.model.enums.PlayerPosition;

/**
 * A game of Kalah
 * @author Elliott Lewandowski
 */
public class Game {

    private static final Logger log = LogManager.getLogger(Game.class);

    private Board board;
    private PlayerPosition lastPlayed = PlayerPosition.TOP;
    private GameState gameState = GameState.LIVE;
    private final Long gameId;

    Game(final Long gameId) {
        log.info("game starting at game id " + gameId);
        this.gameId = gameId;
        setBoard(new Board());
    }

    /**
     * Carry out the overall players move. Handles the changing of players turn
     * and the looping through the available stones to the player.
     * @param startingPosition the id of the {@link Pit} that the turn is starting at
     */
    public void playMove(final Long startingPosition) {
        boolean redo = false;
        log.info("Current player set as: " + this.getLastPlayed());
        final Pit startingPit = board.getPit(startingPosition);

        Pit currentPit = startingPit;
        do {
            if (isLegalMove(startingPit)) {
                currentPit = handleTurn(startingPit, currentPit);
            } else {
                redo = true;
                break;
            }
        } while(startingPit.getStones() > 0 && getGameState().equals(GameState.LIVE));

        log.info("ended on " + currentPit);
        log.info("ended " + getLastPlayed() + " turn");

        if(!redo && getGameState().equals(GameState.LIVE)) {
            setLastPlayed(getLastPlayed()
                    == PlayerPosition.BOTTOM
                    ? PlayerPosition.TOP
                    : PlayerPosition.BOTTOM);
            log.info("changing player to: " + getLastPlayed());
        }
        log.info("---------");
    }

    /**
     * Manage the logic of a players turn
     * @param startingPit The {@link Pit} that the turn started on
     * @param currentPit The {@link Pit} that is currently being interacted with
     * @return The <code>currentPit</code> so that it may be increased for the next loop
     */
    private Pit handleTurn(Pit startingPit, Pit currentPit) {
        Pit next = getNextPit(currentPit.getPosition());
        if(startingPit.getStones() == 1 && next.getStones() == 0) {
            stealStones(getLastPlayed(), next);
        }
        next.setStones(1);
        log.info(next + " added 1 stone" + startingPit.getStones());
        startingPit.removeStone();
        checkGameOver();
        return next;
    }

    /**
     * Check that the game has ended.
     * Done by adding up the total of the the stones
     * in the two home pits (1/14).
     */
    private void checkGameOver() {
        Pit topHome = board.getHomePit(PlayerPosition.TOP);
        Pit bottomHome = board.getHomePit(PlayerPosition.BOTTOM);
        if(topHome.getStones() + bottomHome.getStones() == 72) {
            setGameState(GameState.FINISHED);
        }
    }

    /**
     * 'Steals' the stones from the opposite players {@link Pit}
     * and places them in the players home pit.
     * This can only occur if the <code>landed</code> {@link Pit}
     * was empty as the final stone was placed.
     * @param playerPosition Current player whose turn it is.
     * @param landed The {@link Pit} that was landed in as the final stone
     */
    private void stealStones(final PlayerPosition playerPosition, Pit landed) {
        Pit pitToRemoveStones = board.getPit(landed.getOpposite());
        System.out.println(pitToRemoveStones);
        int spoils = pitToRemoveStones.getStones();
        pitToRemoveStones.clearPit();
        board.getHomePit(playerPosition).setStones(spoils);
    }

    /**
     * Returns the next legal {@link Pit} to place the stone in.
     * Home pits (1/14) are not allowed to be directly placed in.
     * @param attemptedPit The {@link Pit} that is being attempted to be placed into
     * @return The next legal {@link Pit} that can be placed into
     */
    private Pit getNextPit(final Long attemptedPit) {
        return attemptedPit == 13
                ? getBoard().getPit(2L)
                : getBoard().getPit(attemptedPit + 1L);
    }

    /**
     * Runs validation on the move that is about to take place to make sure that
     * it is legal within the game rules.
     * @param pit The {@link Pit} that is being played from.
     * @return Whether the move is legal or not as specified in the contract
     * of the method
     */
    private boolean isLegalMove(final Pit pit) {
        if(!pit.getOwner().equals(getLastPlayed())) {
            throw new KalahException("Invalid move. It is " + getLastPlayed().name() + " turn.");
        }
        if(pit.isHomePit()) {
            throw new KalahException("Invalid move. Can't select home pit (1/14)");
        }
        if(pit.getStones() <= 0) {
            throw new KalahException("Invalid move. Can't select a pit with 0 stones");
        }
        return true;
    }


    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(final GameState gameState) {
        this.gameState = gameState;
    }

    public PlayerPosition getLastPlayed() {
        return this.lastPlayed;
    }

    public void setLastPlayed(final PlayerPosition playerPosition) {
        this.lastPlayed = playerPosition;
    }

    public Board getBoard() {
        return this.board;
    }

    private void setBoard(final Board board) {
        this.board = board;
    }

    public Long getGameId() {
        return this.gameId;
    }

}
