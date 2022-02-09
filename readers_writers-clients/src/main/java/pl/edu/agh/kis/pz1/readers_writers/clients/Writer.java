package pl.edu.agh.kis.pz1.readers_writers.clients;

import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;

/**
 * Class that extends ReadingRoomClient and tries to access ReadingRoom to write in it
 */
public class Writer extends ReadingRoomClient {
    protected static final int WRITING_TIME = 2500;

    public Writer(String clientName, ReadingRoomManager readingRoomManager){
        super("Writer " + clientName, readingRoomManager);
    }

    /**
     * Tries to get access to ReadingRoom and write in its resources in an infinite loop.
     * After it finishes writing it notifies ReadingRoomManager that it left the ReadingRoom.
     */
    @Override
    public void run(){
        ReadingRoom readingRoom = readingRoomManager.getReadingRoom();
        while(true){
            try {
                askAndWait();

                terminalPrinter.print(this + " writes now");
                sleep(WRITING_TIME);
                readingRoom.setBook(clientName + "'s book");
                terminalPrinter.print(clientName + " has written " + readingRoom.getBook());

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
