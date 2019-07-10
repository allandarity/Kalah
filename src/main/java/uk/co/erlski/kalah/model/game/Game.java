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

    private boolean redo = false;

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
            log.info("Current player set as: " + this.getLastPlayed());
            final Pit startingPit = board.getPit(startingPosition);

            Pit currentPit = startingPit;
            do {
                redo = false;
                checkGameOver();
                if (isLegalMove(startingPit)) {
                    currentPit = handleTurn(startingPit, currentPit);
                    System.out.println(startingPit.getMoves());
                } else {
                    redo = true;
                    break;
                }
            } while (startingPit.getStones() > 0 && getGameState().equals(GameState.LIVE));
            System.out.println(startingPit.getMoves());
            log.info("ended on " + currentPit);
            log.info("ended " + getLastPlayed() + " turn");
            if (!redo && getGameState().equals(GameState.LIVE)) {
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
        if(startingPit.getStones() == 0) {
            return next;
        }

        if(checkRedoForLastStone(startingPit, next)) {
            log.info("last stone landed in own house going again");
            redo = true;
        }

        if(checkForStoneSteal(startingPit, next)) {
            stealStones(getLastPlayed(), next);
            startingPit.removeStone();
        }

        if(checkAddStone(startingPit, next)) {
            log.info(next + " added 1 stone" + startingPit.getStones());
            next.setStones(1);
            startingPit.removeStone();
        } else {
            log.info(next + " passed over");
        }
        return next;
    }

    /**
     * Checks whether a stone needs to be added to the next pit if it's owned
     * by the same player.
     * @param startingPit The pit that either started the turn or is currently
     *                    being interacted with.
     * @param nextPit The pit that is next to the pit that is being interacted with
     * @return Whether a stone should be deposited or move on
     */
    private boolean checkAddStone(Pit startingPit, Pit nextPit) {
        PlayerPosition startingPosition = startingPit.getOwner();
        if(startingPosition.equals(PlayerPosition.TOP)) {
            return nextPit.getPosition() != 14;
        } else {
            return nextPit.getPosition() != 1;
        }
    }

    /**
     * Checks whether the final stone is being placed into a neighbouring
     * house that is empty giving another move to the same player.
     * @param startingPit The pit that either started the turn or is currently
     *                    being interacted with.
     * @param nextPit The pit that is next to the pit that is being interacted with
     * @return Whether there should be another turn for the player
     */
    private boolean checkRedoForLastStone(Pit startingPit, Pit nextPit) {
        boolean isNextHome = nextPit.getPosition().equals(startingPit.getHomePit());
        return startingPit.getStones() == 1  && isNextHome;
    }

    /**
     * Check whether a stone should be stolen from the opposite house
     * @param startingPit The pit that either started the turn or is currently
     *                    being interacted with.
     * @param nextPit The pit that is next to the pit that is being interacted with
     * @return Whether the stones should be stolen from the opposite side
     */
    private boolean checkForStoneSteal(Pit startingPit, Pit nextPit) {
        if(!nextPit.getPosition().equals(startingPit.getHomePit()))
            return startingPit.getOwner().equals(nextPit.getOwner())
                    && startingPit.getStones() == 1 && nextPit.getStones() == 0;
        return false;
    }

    /**
     * Check that the game has ended.
     */
    private void checkGameOver() {
        Pit topHome = board.getHomePit(PlayerPosition.TOP);
        Pit bottomHome = board.getHomePit(PlayerPosition.BOTTOM);
        if(board.getTotalNumberOfStonesRemaining(PlayerPosition.TOP) == 0
                || board.getTotalNumberOfStonesRemaining(PlayerPosition.BOTTOM) == 0 ) {
            PlayerPosition moved = topHome.getStones() == 36 ? topHome.getOwner() : bottomHome.getOwner();
            PlayerPosition remained = moved.equals(PlayerPosition.TOP) ? PlayerPosition.BOTTOM : PlayerPosition.TOP;
            setLastPlayed(determineWinner(moved, remained));
        }
    }

    /**
     * Determines the winner of the game based on how many stones are in the home pit + any left on the field if applicable
     * @param moved The player that had all their stones in the pit
     * @param remained The player that had to move stones to the pit afterwards
     * @return The {@link PlayerPosition} that has won the game
     */
    private PlayerPosition determineWinner(PlayerPosition moved, PlayerPosition remained) {
        PlayerPosition winner;
        int movedRemaining = board.getTotalNumberOfStonesRemaining(moved)
                + board.getHomePit(moved).getStones();
        int remainedRemaining = board.getTotalNumberOfStonesRemaining(remained)
                + board.getHomePit(remained).getStones();
        setGameState(GameState.FINISHED);
        if( movedRemaining > remainedRemaining ) {
            winner = moved;
        } else {
            winner = remained;
        }
        log.info("The game has ended " +getLastPlayed()+ " is the winner");
        return winner;
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
        int spoils = pitToRemoveStones.getStones();
        pitToRemoveStones.clearPit();
        board.getHomePit(playerPosition).setStones(spoils+1);
    }

    /**
     * Returns the next legal {@link Pit} to place the stone in.
     * If the pit number is 14 ->
     *  and the player position = top then place in pit 1
     *  else if the player position is bottom then skip pit 1 and go to pit 2
     * @param attemptedPit The {@link Pit} that is being attempted to be placed into
     * @return The next legal {@link Pit} that can be placed into
     */
    private Pit getNextPit(final Long attemptedPit) {
        return attemptedPit == 14L
                ? getBoard().getPit(1L)
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
