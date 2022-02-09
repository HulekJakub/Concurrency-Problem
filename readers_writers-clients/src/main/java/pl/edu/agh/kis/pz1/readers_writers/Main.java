package pl.edu.agh.kis.pz1.readers_writers;

import pl.edu.agh.kis.pz1.readers_writers.clients.Reader;
import pl.edu.agh.kis.pz1.readers_writers.clients.ReadingRoomClient;
import pl.edu.agh.kis.pz1.readers_writers.clients.Writer;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Main function class
 */
public class Main {
    private static final int NUMBER_OF_READERS = 10;
    private static final int NUMBER_OF_WRITERS = 3;
    /**
     * Creates 10 Readers, 3 Writers and shuffles their positions in queue.
     * Then opens ReadingRoom and starts thread managing algorithm.
     * @param args for convention purpose
     */
    public static void main(String[] args){

        ReadingRoom readingRoom = new ReadingRoom();
        ReadingRoomManager readingRoomManager = new ReadingRoomManager(readingRoom);

        List<ReadingRoomClient> readingRoomClients = inviteClients(NUMBER_OF_READERS, NUMBER_OF_WRITERS, readingRoomManager);

        readingRoomClients.forEach(Thread::start);
        readingRoomManager.openReadingRoom();
    }

    static List<ReadingRoomClient> inviteClients(int numberOfReaders, int numberOfWriters, ReadingRoomManager readingRoomManager){
        List<ReadingRoomClient> readingRoomClients = new LinkedList<>();
        for (int i = 0; i < numberOfReaders; i++) {
            readingRoomClients.add(new Reader("" + i, readingRoomManager));
        }
        for (int i = 0; i < numberOfWriters; i++) {
            readingRoomClients.add(new Writer("" + i, readingRoomManager));
        }
        Collections.shuffle(readingRoomClients);
        return readingRoomClients;
    }
}
