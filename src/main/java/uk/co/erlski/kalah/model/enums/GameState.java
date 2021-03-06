package uk.co.erlski.kalah.model.enums;

import uk.co.erlski.kalah.model.game.Game;

/**
 * State of the {@link Game} that is being played
 * @author Elliott Lewandowski
 */
public enum GameState {
    /**
     * {@link Game} is currently alive and playable
     */
    LIVE,

    /**
     * {@link Game} is over and unplayable
     */
    FINISHED

}
