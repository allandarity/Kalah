package uk.co.erlski.kalah.model.board;

import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.enums.PlayerPosition;

import java.util.*;

/**
 * The board in which the game is played on
 * @author Elliott Lewandowski
 */
public class Board {

    private HashMap<Long, Pit> pits = new HashMap<>();

    public Board() {}

    /**
     * Sets the board for the start of the game.
     * Populates a map holding 1-14 pits
     */
    public void setupBoard() {
        for(Long i = 1L; i < 15; i++) {
            pits.put(i, new Pit(i));
        }
    }

    /**
     * Clears the board for the end of the game
     * if required
     */
    public void clearBoard() {
        pits.clear();
    }

    /**
     * Returns a specific {@link Pit} that is identified
     * by <code>position</code>
     * @param position The unique identifer of the {@link Pit} that is
     *                 being searched for
     * @return {@link Pit}
     */
    public Pit getPit(final Long position) {
        if (!pits.containsKey(position)) {
            throw new KalahException("Error. The pit ["+position+"] does not exist");
        }
        return pits.get(position);
    }

    /**
     * Returns a players 'home pit' (1/14)
     * @param playerPosition The player that is requesting the home pit
     * @return The home {@link Pit} for the given {@link PlayerPosition}
     */
    public Pit getHomePit(final PlayerPosition playerPosition) {
        return playerPosition.equals(PlayerPosition.TOP) ? getPit(1L) : getPit(14L);
    }

    /**
     * Calculates the number of stones remaining on the field by subtracting the number on the field
     * by the number in the players home pit.
     * @param playerPosition The {@link PlayerPosition} to count for
     * @return The number of stones on the players field
     */
    public int getTotalNumberOfStonesRemaining(PlayerPosition playerPosition) {
        int totalStones = pits.values().stream()
                .filter(v -> v.getOwner()
                .equals(playerPosition))
                .mapToInt(Pit::getStones).sum();
        int toRemove = getHomePit(playerPosition).getStones();
        return totalStones - toRemove;
    }

    /**
     * Creates a <code>Map<Long, Integer></code> that is used to hold
     * the current state of the board in a format that is convertable
     * for {@link uk.co.erlski.kalah.util.Converter#convertToStatusDTO(Long)}
     * @return A map format of the current state of the game board
     */
    public Map<Long, Integer> getPrintable() {
        final Map<Long, Integer> status = new HashMap<>();
        pits.forEach((k,v) -> {
            status.put(k, v.getStones());
        });
        return status;
    }



}
