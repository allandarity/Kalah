package uk.co.erlski.kalah.model.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Game {

    private static final Logger log = LogManager.getLogger(Game.class);

    private Board board;
    private PlayerPosition lastPlayed = PlayerPosition.BOTTOM;
    private final Long gameId;
    private boolean gameIsLive = true;

    public Game(final Long gameId) {
        log.info("game starting at game id " + gameId);
        this.gameId = gameId;
        setBoard(new Board());
    }


    /*
    TODO:
        Alternate turns between TOP/BOTTOM
        Have the game end - needs testing
     */
    public void playMove(final Long startingPosition) {
        final PlayerPosition currentPlayer =
                lastPlayed == PlayerPosition.BOTTOM
                        ? PlayerPosition.TOP
                        : PlayerPosition.BOTTOM;

        final Pit startingPit = board.getPit(startingPosition);

        int numberOfStones = startingPit.getStones();
        Pit currentPit = startingPit;
        do {
            if (isLegalMove(startingPosition)) {
                Pit next = getNextPit(currentPit.getPosition());
                if(startingPit.getStones() == 1 && next.getStones() == 0) {
                    stealStones(currentPlayer, startingPit);
                }
                next.setStones(1);
                log.info(next + " added 1 stone" + numberOfStones);
                currentPit = next;
            }
            startingPit.removeStone();
            checkGameOver();
        } while(startingPit.getStones() > 0 && gameIsLive);

        log.info("---------");
        log.info("ended on " + currentPit);
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

    private boolean isLastEmpty(final Long finalPosition) {
        return board.getPit(finalPosition).getStones() == 0;
    }

    private boolean isLegalMove(final Long startingPosition) {
        if(board.getPit(startingPosition).isHomePit()) {
            return false;
        }
        if(board.getPit(startingPosition).getStones() <= 0) {
            return false;
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
