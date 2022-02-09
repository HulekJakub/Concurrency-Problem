package pl.edu.agh.kis.pz1.readers_writers.clients;

import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;

/**
 * Class that extends ReadingRoomClient and tries to access ReadingRoom to read from it
 */
public class Reader extends ReadingRoomClient {
    protected static final int READING_TIME = 1500;

    public Reader(String clientName, ReadingRoomManager readingRoomManager ){
        super("Reader " + clientName, readingRoomManager);
    }

    /**
     * Tries to get access to ReadingRoom and read in its resources in an infinite loop.
     * After it finishes reading it notifies ReadingRoomManager that it left the ReadingRoom.
     */
    @Override
    public void run(){
        while(true){
            try {
                askAndWait();

                terminalPrinter.print(this + " reads now");
                sleep(READING_TIME);
                terminalPrinter.print(clientName + " has read " + readingRoomManager.getReadingRoom().getBook());

                leaveReadingRoom();
            } catch (InterruptedException e) {
                terminalPrinter.printError("Error: ",e);
                dealWithInterrupt();
                Thread.currentThread().interrupt();
                return;
            }
        }
    }


}
