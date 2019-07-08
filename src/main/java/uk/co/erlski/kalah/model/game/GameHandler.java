package uk.co.erlski.kalah.model.game;

import java.util.HashMap;

public class GameHandler {

    private static GameHandler INSTANCE = null;

    private HashMap<Long, Game> games = new HashMap<>();

    public void addGame(final Long gameId) {
        games.put(gameId, new Game(gameId));
    }

    public void startGame(final Game game) {
        game.getBoard().setupBoard();
    }

    public void endGame(final Game game) {
        game.getBoard().clearBoard();
    }

    public Game getValidGame(final Long gameId) {
        return games.get(gameId);
    }

    public static GameHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GameHandler();
        }
        return INSTANCE;
    }
}
