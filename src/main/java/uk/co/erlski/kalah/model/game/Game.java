package uk.co.erlski.kalah.model.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Game {

    private static final Logger log = LogManager.getLogger(Game.class);

    private Board board;
    private PlayerPosition lastPlayed = PlayerPosition.BOTTOM;
    private final Long gameId;

    public Game(final Long gameId) {
        log.info("game starting at game id " + gameId);
        this.gameId = gameId;
        setBoard(new Board());
    }


    public void playMove(final Long startingPosition) {
        final PlayerPosition currentPlayer =
                lastPlayed == PlayerPosition.BOTTOM
                        ? PlayerPosition.TOP
                        : PlayerPosition.BOTTOM;

        final Pit startingPit = board.getPit(startingPosition);

        if(isLegalMove(startingPosition)) {
            Long finalPosition = startingPosition+1+startingPit.getStones();
            log.info(finalPosition);
            for(Long i = startingPosition+1; i < finalPosition; i++) {
                log.info("adding stone to " + board.getPit(i));
                board.getPit(i).setStones(1);
            }
            startingPit.clearPit();
            if (isLastEmpty(finalPosition)) {
                if(board.getPit(finalPosition).getOwner()
                        .equals(currentPlayer)) {
                    Pit pitToRemoveStones = board.getOppositePit(startingPosition, currentPlayer);
                    int spoils = pitToRemoveStones.getStones();
                    pitToRemoveStones.clearPit();
                    board.getHomePit(currentPlayer).setStones(spoils);
                }
            }
        }
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

}
