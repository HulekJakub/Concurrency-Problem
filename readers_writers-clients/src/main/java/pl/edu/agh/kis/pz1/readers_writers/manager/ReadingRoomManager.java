package pl.edu.agh.kis.pz1.readers_writers.manager;

import pl.edu.agh.kis.pz1.readers_writers.clients.ReadingRoomClient;
import pl.edu.agh.kis.pz1.readers_writers.commons.TerminalPrinter;
import pl.edu.agh.kis.pz1.readers_writers.reading_room.ReadingRoom;


import java.util.List;

/**
 * Class that manages access to ReadingRoom using ReadingRoomQueue
 */
public class ReadingRoomManager {
    protected static final TerminalPrinter terminalPrinter = new TerminalPrinter();
    private final ReadingRoom readingRoom;
    private final ReadingRoomQueue readingRoomQueue = new ReadingRoomQueue();
    private boolean stop = false;

    public ReadingRoomManager(ReadingRoom readingRoom){
        this.readingRoom = readingRoom;
    }

    /**
     * Adds new clients to the queue
     * @param readingRoomClients client list
     */
    public void addClients(List<ReadingRoomClient> readingRoomClients){
        readingRoomQueue.enterQueue(readingRoomClients);
    }

    /**
     * Adds new client to the queue
     * @param readingRoomClient client
     */
    public void addClient(ReadingRoomClient readingRoomClient){
        readingRoomQueue.enterQueue(readingRoomClient);
    }

    /**
     * Starts threads(clients) that are waiting in queue.
     * Makes new thread that allows new clients to enter once old ones finish
     */
    public void openReadingRoom(){
       readingRoomQueue.getQueue().forEach(Thread::start);
       Thread managingThread = new Thread(this::manageReadingRoomQueue);
       managingThread.start();
    }

    void manageReadingRoomQueue(){
        while(true){
            if(readingRoomQueue.isReadingRoomEmpty()){
                List<ReadingRoomClient> allowedToEnter = readingRoomQueue.getNextClients();
                readingRoomQueue.setClientsCanEnter(allowedToEnter, true);
                synchronized (this){
                    this.notifyAll();
                }
                terminalPrinter.print("In reading room: " + readingRoomQueue.getInside().size());
                terminalPrinter.print("In queue: " + readingRoomQueue.getQueue().size());
            }

            while (!readingRoomQueue.isReadingRoomEmpty()){
                try{
                    synchronized (this){
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    stop = true;
                    synchronized (this){
                        this.notifyAll();
                    }
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public ReadingRoom getReadingRoom() {
        return readingRoom;
    }

    public ReadingRoomQueue getReadingRoomQueue() {
        return readingRoomQueue;
    }

    public boolean isStop() {
        return stop;
    }
}
