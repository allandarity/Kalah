package uk.co.erlski.kalah.model.game;

public class Pit {

    private final Long position;
    private PlayerPosition owner;
    private int stones = 6;

    public Pit(final Long position) {
        this.position = position;
        if(isHomePit()) {
            this.stones = 0;
        }
        if(position <= 7) {
            this.owner = PlayerPosition.TOP;
        } else {
            this.owner = PlayerPosition.BOTTOM;
        }
    }

    public void setStones(final int noOfStones) {
        this.stones += noOfStones;
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

    public int getHomePit() {
        return this.owner.equals(PlayerPosition.TOP) ? 1 : 14;
    }

    public PlayerPosition getOwner() {
        return this.owner;
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
