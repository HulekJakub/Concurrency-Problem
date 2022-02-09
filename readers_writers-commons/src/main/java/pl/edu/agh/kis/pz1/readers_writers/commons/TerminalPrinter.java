package pl.edu.agh.kis.pz1.readers_writers.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Custom logger
 */
public class TerminalPrinter {
    private final Logger logger = LogManager.getLogger(TerminalPrinter.class);

    /**
     * Prints given string
     * @param s string to print
     */
    public void print(String s){
        logger.info(s);
    }

    /**
     * Prints given error's message
     * @param e given error
     */
    public void printError(Exception e){
        logger.info(e.getMessage());
    }

    /**
     * Prints given string, then prints given error message
     * @param s given string
     * @param e given message
     */
    public void printError(String s,Exception e){
        this.print(s + e.getMessage());
    }
}
