package uk.co.erlski.kalah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;
import uk.co.erlski.kalah.util.Converter;
import uk.co.erlski.kalah.exception.KalahException;

@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private Converter converter;
    @Autowired
    private GameHandler gameHandler;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object createGame() throws KalahException {
        long gameId = (long) (Math.random() * (1000));
        gameHandler.addGame(gameId);
        Game game = gameHandler.getValidGame(gameId);
        return converter.convertToStatusDTO(game.getGameId());
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    @ResponseBody
    public Object status(
            @PathVariable(value = "gameId") final Long gameId
    ) throws KalahException {
        Game game = gameHandler.getValidGame(gameId);
        return converter.convertToStatusDTO(game.getGameId());
    }

    @RequestMapping(value = "/{gameId}/{startingPosition}", method = RequestMethod.PUT)
    @ResponseBody
    public Object makeAMove(
            @PathVariable(value = "gameId") final Long gameId,
            @PathVariable(value = "startingPosition") final Long startingPosition
    ) throws KalahException {
        Game game = gameHandler.getValidGame(gameId);
        game.playMove(startingPosition);
        return converter.convertToStatusDTO(game.getGameId());
    }


}
