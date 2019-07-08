package uk.co.erlski.kalah.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;
import uk.co.erlski.kalah.util.Converter;

@RestController
@RequestMapping("games")
public class GameController {

    GameHandler gameHandler = GameHandler.getInstance();
    Converter converter = new Converter();


    @RequestMapping(value = "/{gameId}/")
    @ResponseBody
    public Object createGame(
            @PathVariable(value = "gameId") final Long gameId
    ) {
        Game game = gameHandler.getValidGame(gameId);
        if(game != null) {
            return converter.convertToErrorDTO("Game already exists. Check progress with /status/",
                    HttpStatus.CONFLICT);
        }
        return "yay";
    }

    @RequestMapping(value = "/{gameId}/status", method = RequestMethod.GET)
    @ResponseBody
    public Object status(
            @PathVariable(value = "gameId") final Long gameId
    ) {
        Game game = gameHandler.getValidGame(gameId);
        return converter.convertToStatusDTO(game.getGameId());
    }

    @RequestMapping(value = "/{gameId}/{startingPosition}", method = RequestMethod.PUT)
    @ResponseBody
    public Object makeAMove(
            @PathVariable(value = "gameId") final Long gameId,
            @PathVariable(value = "startingPosition") final Long startingPosition
    ) {
        Game game = gameHandler.getValidGame(gameId);
        game.playMove(startingPosition);
        return null;
    }


}
