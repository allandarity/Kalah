package uk.co.erlski.kalah.model.game;

import org.springframework.stereotype.Component;
import uk.co.erlski.kalah.exception.KalahException;

import java.util.HashMap;

/**
 * Holds all running instances of a {@link Game}.
 * @author Elliott Lewandowski
 */
@Component
public class GameHandler {

    public GameHandler() {
    }

    /*
    Todo:
        clear finished games out of the map so the id can be reused/less chance of collision
     */

    /**
     * Map  holding all running instances of games
     */
    private HashMap<Long, Game> games = new HashMap<>();

    /**
     * Adds a game, if it doesn't already exist, to the {@link this#games} map
     * @param gameId Unique identifier of the {@link Game} to be created
     * @throws KalahException exception
     */
    public void addGame(final Long gameId) throws KalahException {
        if(games.containsKey(gameId)) {
            throw new KalahException("Game already exists");
        }
        games.put(gameId, new Game(gameId));
        startGame(getValidGame(gameId));
    }

    /**
     * Starts a new {@link Game} by setting up the {@link uk.co.erlski.kalah.model.board.Board}
     * @param game the {@link Game} that is starting
     */
    private void startGame(final Game game) {
        game.getBoard().setupBoard();
    }

    public void endGame(final Game game) {
        game.getBoard().clearBoard();
    }

    /**
     * Takes an input of a gameId and checks it against the map to make sure
     * that a valid game exists.
     * @param gameId The id of the game that is being searched for
     * @return An instance of {@link Game} if it exists
     * @throws KalahException exception
     */
    public Game getValidGame(final Long gameId) throws KalahException {
        if(gameId == null) {
            throw new KalahException("Error. You have not specified a gameId");
        }
        if(!games.containsKey(gameId)) {
            throw new KalahException("Error. A game with the id " + gameId +" doesn't exist");
        }
        return games.get(gameId);
    }

}
