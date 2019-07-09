package uk.co.erlski.kalah;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point into the KalahApplication
 * @author Elliott Lewandowski
 */
@SpringBootApplication
public class KalahApplication {

    private static final Logger log = LogManager.getLogger(KalahApplication.class);

    /**
     * Application entry point
     * @param args
     */
    public static void main(String[] args) {
        log.info("Kalah starting");
        SpringApplication.run(KalahApplication.class, args);
    }

}
