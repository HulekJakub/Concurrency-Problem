package pl.edu.agh.kis.pz1.readers_writers.clients;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomQueue;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

/**
 * Test class
 */
public class ReaderTest {

    ReadingRoom readingRoom;
    ReadingRoomManager readingRoomManager;
    ReadingRoomQueue readingRoomQueue;
    Reader reader;

    @Before
    public void init(){
        readingRoom = new ReadingRoom();
        readingRoomManager = new ReadingRoomManager(readingRoom);
        readingRoomQueue = readingRoomManager.getReadingRoomQueue();
        reader = new Reader("test", readingRoomManager);
        readingRoomManager.addClient(reader);
    }

    @Test
    public void runTest(){
        readingRoomQueue.getQueue().forEach(Thread::start);
        reader.setCanEnter(true);
        int before = readingRoom.getNumberOfReadAccesses();
        synchronized (this){
            this.notifyAll();
        }

        await().atMost(Duration.ofMillis(Reader.READING_TIME*2)).until(() -> !reader.canEnter);
        int after = readingRoom.getNumberOfReadAccesses();
        assertEquals(before, after - 1);
    }

    @Test
    public void runInterruptTest() {
        reader.start();
        reader.interrupt();
        assertTrue(reader.isInterrupted());
    }

}