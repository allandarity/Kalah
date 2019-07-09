package uk.co.erlski.kalah.model.game;

import org.springframework.stereotype.Component;
import uk.co.erlski.kalah.exception.KalahException;

import java.util.HashMap;

@Component
public class GameHandler {

    public GameHandler() {
    }

    private HashMap<Long, Game> games = new HashMap<>();

    public void addGame(final Long gameId) throws KalahException {
        if(games.containsKey(gameId)) {
            throw new KalahException("Game already exists");
        }
        games.put(gameId, new Game(gameId));
        startGame(getValidGame(gameId));
    }

    private void startGame(final Game game) {
        game.getBoard().setupBoard();
    }

    public void endGame(final Game game) {
        game.getBoard().clearBoard();
    }

    public Game getValidGame(final Long gameId) throws KalahException {
        if(gameId == null) {
            throw new KalahException("Exception");
        }
        if(!games.containsKey(gameId)) {
            throw new KalahException("doesnt exist");
        }
        return games.get(gameId);
    }

}
