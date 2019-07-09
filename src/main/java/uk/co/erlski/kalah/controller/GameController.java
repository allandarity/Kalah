package uk.co.erlski.kalah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;
import uk.co.erlski.kalah.model.game.GameState;
import uk.co.erlski.kalah.util.Converter;
import uk.co.erlski.kalah.exception.KalahException;

/**
 * Controller class for REST entry into the application
 * @author Elliott Lewandowski
 */
@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private Converter converter;
    @Autowired
    private GameHandler gameHandler;

    /**
     * Creates a new {@link Game}
     * @return A created response entity if successful.
     * @throws KalahException exception
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createGame() throws KalahException {
        long gameId = (long) (Math.random() * (1000));
        gameHandler.addGame(gameId);
        Game game = gameHandler.getValidGame(gameId);
        return new ResponseEntity<>(converter.convertToStartGameDTO(game.getGameId()), HttpStatus.CREATED);
    }

    /**
     * Returns the current state of the {@link uk.co.erlski.kalah.model.board.Board} for a given
     * gameId
     * @param gameId Identifier of the board that the state is being requested
     * @return The current state of the board + a 200 code if applicable
     * @throws KalahException exception
     */
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> status(
            @PathVariable(value = "gameId") final Long gameId
    ) throws KalahException {
        Game game = gameHandler.getValidGame(gameId);
        return new ResponseEntity<>(converter.convertToStatusDTO(game.getGameId()), HttpStatus.OK);
    }

    /**
     * Makes a move on a board defined by its identifier and the starting position of the move
     * @param gameId Identifier for the {@link Game}
     * @param startingPosition The starting position used to find the {@link uk.co.erlski.kalah.model.board.Pit}
     * @return A HTTP response with the outcome of the request
     * @throws KalahException exception
     */
    @RequestMapping(value = "/{gameId}/{startingPosition}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String>  makeAMove(
            @PathVariable(value = "gameId") final Long gameId,
            @PathVariable(value = "startingPosition") final Long startingPosition
    ) throws KalahException {
        Game game = gameHandler.getValidGame(gameId);
        if(game.getGameState().equals(GameState.LIVE)) {
            game.playMove(startingPosition);
        } else {
            throw new KalahException("The game has ended, " + game.getLastPlayed() + " is the winner.");
        }
        return new ResponseEntity<>(converter.convertToStatusDTO(game.getGameId()), HttpStatus.OK);
    }


}
