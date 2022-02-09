package pl.edu.agh.kis.pz1.readers_writers.manager;

import pl.edu.agh.kis.pz1.readers_writers.clients.ReadingRoomClient;
import pl.edu.agh.kis.pz1.readers_writers.clients.Reader;
import pl.edu.agh.kis.pz1.readers_writers.clients.Writer;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that manages queue to the ReadingRoom
 */
public class ReadingRoomQueue {
    private static final int MAX_READERS_AT_TIME = 5;
    private List<ReadingRoomClient> queue = new LinkedList<>();
    private List<ReadingRoomClient> inside = new LinkedList<>();


    /**
     * Adds new clients to the end of the queue
     * @param readingRoomClients clients list
     */
    public synchronized void enterQueue(List<ReadingRoomClient> readingRoomClients) {
        queue.addAll(readingRoomClients);
    }

    /**
     * Deletes clients from the queue
     * @param readingRoomClients clients list
     */
    public synchronized void leaveQueue(List<ReadingRoomClient> readingRoomClients) {
        readingRoomClients.forEach(readingRoomClient -> queue.remove(readingRoomClient));
    }

    /**
     * Adds new client to the end of the queue
     * @param readingRoomClient client
     */
    public synchronized void enterQueue(ReadingRoomClient readingRoomClient) {
        queue.add(readingRoomClient);
    }

    /**
     * Deletes client from the queue
     * @param readingRoomClient client
     */
    public synchronized void leaveQueue(ReadingRoomClient readingRoomClient) {
        queue.remove(readingRoomClient);
    }

    /**
     * Deletes client from the inside list
     * @param readingRoomClient client
     */
    public synchronized void leaveInside(ReadingRoomClient readingRoomClient){
        inside.remove(readingRoomClient);
    }

    /**
     * Return list of clients that have permission to enter
     * Returns 1 Writer if it stands at the beginning of the queue
     * Returns up to 5 Readers otherwise
     * @return list of clients
     */
    public synchronized List<ReadingRoomClient> getNextClients() {
        List<ReadingRoomClient> clientsToRun = new LinkedList<>();
        for (ReadingRoomClient readingRoomClient : queue) {
            if (clientsToRun.isEmpty() && readingRoomClient instanceof Writer) {
                clientsToRun.add(readingRoomClient);
            }
            if (readingRoomClient instanceof Reader) {
                clientsToRun.add(readingRoomClient);
            }

            if (clientsToRun.size() >= MAX_READERS_AT_TIME || clientsToRun.size() == 1 && clientsToRun.get(0) instanceof Writer) {
                break;
            }
        }
        inside = clientsToRun;
        leaveQueue(clientsToRun);

        return clientsToRun;
    }

    /**
     * Sets every given client's canEnter parameter to given canEnter method argument
     * @param clientList client list
     * @param canEnter true if they are to be allowed to enter, false otherwise
     */
    public synchronized void setClientsCanEnter(List<ReadingRoomClient> clientList, boolean canEnter) {
        clientList.forEach(readingRoomClient -> readingRoomClient.setCanEnter(canEnter));
    }

    /**
     * Returns true if there is no clients using ReadingRoom's resources at the moment
     * @return boolean
     */
    public synchronized boolean isReadingRoomEmpty(){
        return inside.isEmpty();
    }

    public synchronized List<ReadingRoomClient> getQueue() {
        return queue;
    }

    public synchronized List<ReadingRoomClient> getInside() {
        return inside;
    }
}
