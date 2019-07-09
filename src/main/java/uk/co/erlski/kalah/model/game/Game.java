package uk.co.erlski.kalah.model.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.board.Board;
import uk.co.erlski.kalah.model.board.Pit;
import uk.co.erlski.kalah.model.enums.PlayerPosition;

public class Game {

    private static final Logger log = LogManager.getLogger(Game.class);

    private Board board;
    private PlayerPosition lastPlayed = PlayerPosition.TOP;
    private final Long gameId;
    private boolean gameIsLive = true;
    private boolean redo = false;

    Game(final Long gameId) {
        log.info("game starting at game id " + gameId);
        this.gameId = gameId;
        setBoard(new Board());
    }

    /*
    TODO:
        Alternate turns between TOP/BOTTOM
        Have the game end - needs testing
        Game state enum
     */
    public void playMove(final Long startingPosition) {
        redo = false;
        log.info("Current player set as: " + this.getLastPlayed());
        final Pit startingPit = board.getPit(startingPosition);

        int numberOfStones = startingPit.getStones();
        Pit currentPit = startingPit;
        do {
            if (isLegalMove(startingPit)) {
                Pit next = getNextPit(currentPit.getPosition());
                if(startingPit.getStones() == 1 && next.getStones() == 0) {
                    stealStones(getLastPlayed(), next);
                }
                next.setStones(1);
                log.info(next + " added 1 stone" + numberOfStones);
                currentPit = next;
                startingPit.removeStone();
                checkGameOver();
            } else {
                redo = true;
                break;
            }
        } while(startingPit.getStones() > 0 && gameIsLive);

        log.info("ended on " + currentPit);
        log.info("ended " + getLastPlayed() + " turn");

        if(!redo) {
            setLastPlayed(getLastPlayed()
                    == PlayerPosition.BOTTOM
                    ? PlayerPosition.TOP
                    : PlayerPosition.BOTTOM);
            log.info("changing player to: " + getLastPlayed());
        }
        log.info("---------");
    }

    private void checkGameOver() {
        Pit topHome = board.getHomePit(PlayerPosition.TOP);
        Pit bottomHome = board.getHomePit(PlayerPosition.BOTTOM);
        if(topHome.getStones() + bottomHome.getStones() == 75) {
            gameIsLive = false;
        }
    }

    private void stealStones(final PlayerPosition playerPosition, Pit landed) {
        Pit pitToRemoveStones = board.getPit(landed.getOpposite());
        System.out.println(pitToRemoveStones);
        int spoils = pitToRemoveStones.getStones();
        pitToRemoveStones.clearPit();
        board.getHomePit(playerPosition).setStones(spoils);
    }

    private Pit getNextPit(final Long startingPit) {
        return startingPit == 13
                ? getBoard().getPit(2L)
                : getBoard().getPit(startingPit + 1L);
    }

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

    public boolean getIsGameLive() {
        return this.gameIsLive;
    }

}
