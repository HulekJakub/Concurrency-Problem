package pl.edu.agh.kis.pz1.readers_writers.manager;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.kis.pz1.readers_writers.clients.Reader;
import pl.edu.agh.kis.pz1.readers_writers.clients.ReadingRoomClient;
import pl.edu.agh.kis.pz1.readers_writers.clients.Writer;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class
 */
public class ReadingRoomQueueTest {
    ReadingRoomQueue readingRoomQueue;

    @Before
    public void init() {
        readingRoomQueue = new ReadingRoomQueue();
    }

    @Test
    public void enterQueueTest() {
        Reader reader = new Reader("test", null);
        readingRoomQueue.enterQueue(reader);
        assertTrue(readingRoomQueue.getQueue().contains(reader));
    }

    @Test
    public void leaveQueueMultipleTest() {
        List<ReadingRoomClient> readingRoomClients = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            readingRoomClients.add(new Writer("test" + i, null));
        }
        readingRoomClients.add(new Reader("test", null));
        readingRoomQueue.enterQueue(readingRoomClients);
        readingRoomQueue.leaveQueue(readingRoomClients);
        assertEquals(0, readingRoomQueue.getQueue().size());
    }

    @Test
    public void enterQueueMultipleTest() {
        List<ReadingRoomClient> readingRoomClients = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            readingRoomClients.add(new Writer("test" + i, null));
        }
        readingRoomClients.add(new Reader("test", null));
        readingRoomQueue.enterQueue(readingRoomClients);
        assertTrue(readingRoomQueue.getQueue().containsAll(readingRoomClients));
    }

    @Test
    public void leaveInsideTest() {
        Reader reader = new Reader("test", null);
        readingRoomQueue.enterQueue(reader);
        assertTrue(readingRoomQueue.getQueue().contains(reader));
    }

    @Test
    public void leaveQueueTest() {
        Reader reader = new Reader("test", null);
        readingRoomQueue.enterQueue(reader);
        readingRoomQueue.leaveQueue(reader);
        assertFalse(readingRoomQueue.getQueue().contains(reader));
    }

    @Test
    public void getNextClientsWriterTest() {
        List<ReadingRoomClient> readingRoomClients = new LinkedList<>();
        Writer writer = new Writer("test", null);
        readingRoomClients.add(writer);
        for (int i = 0; i < 5; i++) {
            readingRoomClients.add(new Reader("test" + i, null));
        }
        readingRoomQueue.enterQueue(readingRoomClients);
        List<ReadingRoomClient> nextClients = readingRoomQueue.getNextClients();
        assertTrue(nextClients.size() == 1 && nextClients.contains(writer));
    }

    @Test
    public void getNextClientsReadersTest() {
        List<ReadingRoomClient> readingRoomClients = new LinkedList<>();
        Reader reader = new Reader("test", null);
        readingRoomClients.add(reader);
        Writer writer = new Writer("test", null);
        readingRoomClients.add(writer);
        for (int i = 0; i < 5; i++) {
            readingRoomClients.add(new Reader("test" + i, null));
        }
        readingRoomQueue.enterQueue(readingRoomClients);
        List<ReadingRoomClient> nextClients = readingRoomQueue.getNextClients();
        assertEquals(5, nextClients.stream()
                .filter(readingRoomClient -> readingRoomClient instanceof Reader)
                .mapToInt(value -> 1)
                .reduce(Integer::sum)
                .orElse(0));
    }

    @Test
    public void setClientsCanEnterTest() {
        List<ReadingRoomClient> readingRoomClients = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            readingRoomClients.add(new Writer("test" + i, null));
        }
        readingRoomClients.add(new Reader("test", null));
        readingRoomQueue.enterQueue(readingRoomClients);
        readingRoomQueue.setClientsCanEnter(readingRoomClients, true);
        assertEquals(readingRoomClients.size(), readingRoomQueue.getQueue().stream()
                .mapToInt(client -> client.isCanEnter()? 1: 0)
                .reduce(Integer::sum)
                .orElse(0));
    }
}