package uk.co.erlski.kalah.util;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import uk.co.erlski.kalah.io.ErrorDTO;
import uk.co.erlski.kalah.io.StatusDTO;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;
import uk.co.erlski.kalah.model.game.PlayerPosition;

public class Converter {

    Gson gson = new Gson();

    public String convertToStatusDTO(final Long gameId) {
        StatusDTO dto = new StatusDTO();
        Game game = GameHandler.getInstance().getValidGame(gameId);
        dto.setTopRow(game.getBoard().getPlayerPitsValues(PlayerPosition.TOP));
        dto.setBottomRow(game.getBoard().getPlayerPitsValues(PlayerPosition.BOTTOM));
        dto.setGameId(gameId);
        return gson.toJson(dto);
    }

    public String convertToErrorDTO(String error, HttpStatus type) {
        ErrorDTO dto = new ErrorDTO();
        dto.setError(error);
        dto.setType(type);
        return gson.toJson(dto);
    }



}
