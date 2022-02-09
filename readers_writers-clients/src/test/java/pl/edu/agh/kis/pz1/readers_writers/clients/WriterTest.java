package pl.edu.agh.kis.pz1.readers_writers.clients;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomQueue;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;

import java.time.Duration;

import static org.awaitility.Awaitility.*;
import static org.junit.Assert.*;

/**
 * Test class
 */
public class WriterTest {
    ReadingRoom readingRoom;
    ReadingRoomManager readingRoomManager;
    ReadingRoomQueue readingRoomQueue;
    Writer writer;

    @Before
    public void init(){
        readingRoom = new ReadingRoom();
        readingRoomManager = new ReadingRoomManager(readingRoom);
        readingRoomQueue = readingRoomManager.getReadingRoomQueue();
        writer = new Writer("test", readingRoomManager);
        readingRoomManager.addClient(writer);
    }

    @Test
    public void runTest() {
        readingRoomQueue.getQueue().forEach(Thread::start);
        writer.setCanEnter(true);
        synchronized (this){
            this.notifyAll();
        }

        await().atMost(Duration.ofMillis(Writer.WRITING_TIME*2)).until(() -> !writer.canEnter);
        assertEquals(readingRoom.getBook(), writer + "'s book");
    }

    @Test
    public void runInterruptTest() {
        writer.start();
        writer.interrupt();
        assertTrue(writer.isInterrupted());
    }

}