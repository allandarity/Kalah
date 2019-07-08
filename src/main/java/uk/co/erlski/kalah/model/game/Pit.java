package uk.co.erlski.kalah.model.game;

public class Pit {

    private final Long position;
    private Long opposite = null;
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
        setOpposite();
    }


    //TODO: this needs cleaning up/replacing
    private void setOpposite() {
        Long[] topPits = {2L, 3L, 4L, 5L, 6L, 7L};
        Long[] bottomPits = {8L, 9L, 10L, 11L, 12L, 13L};
        if(this.owner.equals(PlayerPosition.TOP)) {
            for(int i = 0; i < topPits.length; i++) {
                if(this.position == topPits[i]) {
                    this.opposite = bottomPits[i];
                }
            }
        } else {
            for(int i = 0; i < topPits.length; i++) {
                if(this.position == bottomPits[i]) {
                    this.opposite = topPits[i];
                }
            }
        }
    }

    public Long getOpposite() {
        return this.opposite;
    }

    public void setStones(final int noOfStones) {
        this.stones += noOfStones;
    }

    public void removeStone() {
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
