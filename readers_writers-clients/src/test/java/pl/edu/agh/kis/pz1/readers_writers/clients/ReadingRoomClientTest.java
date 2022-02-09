package pl.edu.agh.kis.pz1.readers_writers.clients;

import org.junit.Test;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;

/**
 * Test class
 */
public class ReadingRoomClientTest {

    @Test(expected = ClassCastException.class)
    public void runTest() {
        ReadingRoomClient readingRoomClient = new ReadingRoomClient("test", new ReadingRoomManager(new ReadingRoom()));
        readingRoomClient.run();
    }
}