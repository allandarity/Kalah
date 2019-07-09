package uk.co.erlski.kalah;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.erlski.kalah.model.game.Game;
import uk.co.erlski.kalah.model.game.GameHandler;
import uk.co.erlski.kalah.exception.KalahException;

@SpringBootApplication
public class KalahApplication {

    private static final Logger log = LogManager.getLogger(KalahApplication.class);

    public static void main(String[] args) throws KalahException {
        log.info("Kalah starting");
        SpringApplication.run(KalahApplication.class, args);
    }

}
