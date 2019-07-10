package uk.co.erlski.kalah.model.game;

import org.springframework.stereotype.Component;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.model.enums.GameState;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds all running instances of a {@link Game}.
 * @author Elliott Lewandowski
 */
@Component
public class GameHandler {

    public GameHandler() {
    }

    /**
     * Map  holding all running instances of games
     */
    private Map<Long, Game> games = new HashMap<>();

    /**
     * Adds a game, if it doesn't already exist, to the {@link this#games} map.
     * If the game already exists it checks to see whether the {@link GameState}
     * is set to finished - if yes, it replaces it.
     * @param gameId Unique identifier of the {@link Game} to be created
     * @throws KalahException exception
     */
    public void addGame(final Long gameId) throws KalahException {
        if(gameId == null) {
            throw new KalahException("Error. You have not specified a gameId");
        }
        if(gameId < 0) {
            throw new KalahException("ID is too low");
        }
        if(games.containsKey(gameId)) {
            if(getValidGame(gameId).getGameState().equals(GameState.LIVE)) {
                throw new KalahException("Game already exists and is live");
            } else {
                removeFinishedGame(gameId);
            }
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

    /**
     * Check whether a {@link Game} has finished yet by checking the {@link Game#getGameState()}
     * If the game state returns finished the instance is removed from the map and replaced with
     * a new one.
     * @param gameId
     * @return
     * @throws KalahException
     */
    public boolean removeFinishedGame(final Long gameId) throws KalahException {
        if(gameId == null) {
            throw new KalahException("Error. You have not specified a gameId");
        }
        if(!games.containsKey(gameId)) {
            throw new KalahException("Error. A game with the id " + gameId +" doesn't exist");
        }
        Game game = getValidGame(gameId);
        if(!game.getGameState().equals(GameState.FINISHED)) {
            throw new KalahException("Error. A game with the id " + gameId +" hasn't finished yet");
        }
        games.remove(gameId);
        return true;
    }


    public Map<Long, Game> getGamesMap() {
        return games;
    }

}
