package uk.co.erlski.kalah.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.erlski.kalah.exception.KalahException;
import uk.co.erlski.kalah.io.StatusDTO;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;
import uk.co.erlski.kalah.model.enums.PlayerPosition;

@Component
public class Converter {

    @Autowired
    private GameHandler gameHandler;

    private Gson gson = new Gson();

    public String convertToStatusDTO(final Long gameId) throws KalahException {
        StatusDTO dto = new StatusDTO();
        Game game = gameHandler.getValidGame(gameId);
        dto.setTopRow(game.getBoard().getPlayerPitsValues(PlayerPosition.TOP));
        dto.setBottomRow(game.getBoard().getPlayerPitsValues(PlayerPosition.BOTTOM));
        dto.setGameId(gameId);
        return gson.toJson(dto);
    }


}
