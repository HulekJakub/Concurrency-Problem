package pl.edu.agh.kis.pz1.readers_writers;

import org.junit.Test;
import pl.edu.agh.kis.pz1.readers_writers.clients.Reader;
import pl.edu.agh.kis.pz1.readers_writers.clients.ReadingRoomClient;
import pl.edu.agh.kis.pz1.readers_writers.clients.Writer;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Test class
 */
public class MainTest {

    @Test
    public void inviteClientsTest() {
        ReadingRoom readingRoom = new ReadingRoom();
        ReadingRoomManager readingRoomManager = new ReadingRoomManager(readingRoom);

        List<ReadingRoomClient> readingRoomClients = Main.inviteClients(10, 3, readingRoomManager);
        assertEquals(10, readingRoomClients.stream()
                .filter(readingRoomClient -> readingRoomClient instanceof Reader)
                .mapToInt(value -> 1)
                .sum());
        assertEquals(3, readingRoomClients.stream()
                .filter(readingRoomClient -> readingRoomClient instanceof Writer)
                .mapToInt(value -> 1)
                .sum());
    }
}