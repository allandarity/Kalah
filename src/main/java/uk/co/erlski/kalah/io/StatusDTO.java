package uk.co.erlski.kalah.io;

import java.util.List;

public class StatusDTO {

    private List<String> topRow;
    private List<String> bottomRow;
    private Long gameId;

    public List<String> getTopRow() {
        return topRow;
    }

    public void setTopRow(List<String> topRow) {
        this.topRow = topRow;
    }

    public List<String> getBottomRow() {
        return bottomRow;
    }

    public void setBottomRow(List<String> bottomRow) {
        this.bottomRow = bottomRow;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
