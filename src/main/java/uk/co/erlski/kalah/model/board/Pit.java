package uk.co.erlski.kalah.model.board;

import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.enums.PlayerPosition;

/**
 * One of the 14 pits that make up a game of Kalah
 * @author Elliott Lewandowski
 */
public class Pit {

    private final Long position;
    private Long opposite = null;
    private PlayerPosition owner;
    private int stones = 6;
    private int moves = 0;

    Pit(final Long position) {
        this.position = position;
        if(isHomePit()) {
            this.stones = 0;
        }
        if(position <= 7) {
            this.owner = PlayerPosition.TOP;
        } else {
            this.owner = PlayerPosition.BOTTOM;
        }
        setOpposite();
    }

    /**
     * Determines and sets the {@link Pit} that lives
     * opposite of the current instance of this Pit.
     */
    private void setOpposite() {
        Long[] topPits = {2L, 3L, 4L, 5L, 6L, 7L};
        Long[] bottomPits = {8L, 9L, 10L, 11L, 12L, 13L};
        if(this.owner.equals(PlayerPosition.TOP)) {
            for(int i = 0; i < topPits.length; i++) {
                if(this.position.equals(topPits[i])) {
                    this.opposite = bottomPits[i];
                }
            }
        } else {
            for(int i = 0; i < topPits.length; i++) {
                if(this.position.equals(bottomPits[i])) {
                    this.opposite = topPits[i];
                }
            }
        }
    }

    public Long getOpposite() {
        return this.opposite;
    }

    public void setStones(final int noOfStones) {
        if(noOfStones < 0) {
            throw new KalahException("Unable to set negative stones");
        }
        this.stones += noOfStones;
    }

    public void removeStone() {
        if(this.stones != 0)
            this.stones -= 1;
    }

    public void clearPit() {
        this.stones = 0;
    }

    public int getStones() {
        return this.stones;
    }

    public boolean isHomePit() {
        return this.position == 1 || this.position == 14;
    }

    public Long getPosition() {
        return this.position;
    }

    public Long getHomePit() {
        return this.owner.equals(PlayerPosition.TOP) ? 1L : 14L;
    }

    public PlayerPosition getOwner() {
        return this.owner;
    }

    public void setStoneCount(final int stones) {
        this.stones = stones;
    }

    public void setMoves(final int moves) {
        this.moves = moves;
    }

    public int getMoves() {
        return this.moves;
    }

    public void removeMove(final int move) {
        this.moves -= move;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isHomePit()) {
            sb.append("[H]");
        }
        sb.append(position).append(":").append(stones);
        return sb.toString();
    }

}
