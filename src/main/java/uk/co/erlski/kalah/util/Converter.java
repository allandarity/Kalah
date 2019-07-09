package uk.co.erlski.kalah.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.io.GameStartDTO;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;

/**
 * Converter for turning game related entities into JSON formats to be
 * sent to the client
 * @author Elliott Lewandowski
 */
@Component
public class Converter {

    @Autowired
    private GameHandler gameHandler;
    @Autowired
    private Gson gson;

    /**
     * Converts a {@link Game} instance given by the <code>gameId</code>
     * into a JSON object to be sent when a game is started
     * @param gameId The unique identifier of a given {@link Game}
     * @return A JSON converted string to return to the client
     */
    public String convertToStartGameDTO(final long gameId) {
        GameStartDTO dto = new GameStartDTO();
        Game game = gameHandler.getValidGame(gameId);
        dto.setId(game.getGameId());
        dto.setUrl("http://localhost:8080/games/"+game.getGameId());
        return gson.toJson(dto);
    }

    /**
     * Returns the current status of the game board
     * @param gameId The unique identifier of a given {@link Game}
     * @return A JSON converted string to return to the client
     * @throws KalahException
     */
    public String convertToStatusDTO(final Long gameId) {
        return gson.toJson(gameHandler.getValidGame(gameId).getBoard().getPrintable());
    }


}
