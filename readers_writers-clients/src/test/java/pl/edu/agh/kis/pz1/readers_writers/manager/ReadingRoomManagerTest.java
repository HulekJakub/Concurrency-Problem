package pl.edu.agh.kis.pz1.readers_writers.manager;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.kis.pz1.readers_writers.clients.Reader;
import pl.edu.agh.kis.pz1.readers_writers.clients.ReadingRoomClient;
import pl.edu.agh.kis.pz1.readers_writers.clients.Writer;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;


import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

/**
 * Test class
 */
public class ReadingRoomManagerTest {

    ReadingRoom readingRoom;
    ReadingRoomManager readingRoomManager;
    List<ReadingRoomClient> threadsList;

    @Before
    public void init() {
        readingRoom = new ReadingRoom();
        readingRoomManager = new ReadingRoomManager(readingRoom);

        List<ReadingRoomClient> readingRoomClientList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            readingRoomClientList.add(new Reader("test" + i, readingRoomManager));
        }
        readingRoomClientList.add((new Writer("testW", readingRoomManager)));

        readingRoomManager.addClients(readingRoomClientList);
        threadsList = new LinkedList<>(readingRoomClientList);
    }

    @After
    public void tearDown() {
        threadsList.forEach(Thread::interrupt);
        readingRoom = null;
        readingRoomManager = null;
    }

    @Test
    public void addClientsTest() {
        List<ReadingRoomClient> readingRoomClientList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
                readingRoomClientList.add(new Reader("123", readingRoomManager));
        }
        readingRoomClientList.add((new Writer("213", readingRoomManager)));
        readingRoomManager.addClients(readingRoomClientList);
        threadsList.addAll(readingRoomClientList);
        assertTrue(readingRoomManager.getReadingRoomQueue().getQueue().containsAll(readingRoomClientList));
    }

    @Test
    public void addClientTest() {
        Writer writer = new Writer("213", readingRoomManager);
        readingRoomManager.addClient(writer);
        threadsList.add(writer);
        assertTrue(readingRoomManager.getReadingRoomQueue().getQueue().contains(writer));
    }

    @Test
    public void openReadingRoomTest() {
        readingRoomManager.openReadingRoom();

        await().until(()->threadsList.stream()
            .filter(Thread::isAlive)
            .findAny()
            .orElse(null) != null);
        assertTrue(true);
    }

    @Test
    public void manageReadingRoomQueueTest() {
        threadsList.forEach(Thread::start);
        System.out.println("Wystartowane-----------------------");
        await().until(() -> threadsList.stream()
                .mapToInt(value -> value.isAlive()?0:1)
                .reduce(Integer::sum)
                .orElse(0)==0);

        Thread managingThread = new Thread(readingRoomManager::manageReadingRoomQueue);
        System.out.println("Managing wystartowany-----------------------");
        managingThread.start();
        await().until(someoneCanEnter());
        managingThread.interrupt();
        await().until(managingThread::isInterrupted);


        await().until(allCanNotEnter());

        assertTrue(true);
    }


    private Callable<Boolean> someoneCanEnter() {
        return () -> threadsList.stream()
                .filter(ReadingRoomClient::isCanEnter)
                .findAny()
                .orElse(null) != null;
    }

    private Callable<Boolean> allCanNotEnter() {
        return () -> threadsList.stream()
                .filter(ReadingRoomClient::isCanEnter)
                .mapToInt(value -> 1)
                .reduce(Integer::sum)
                .orElse(0) == 0;
    }
}