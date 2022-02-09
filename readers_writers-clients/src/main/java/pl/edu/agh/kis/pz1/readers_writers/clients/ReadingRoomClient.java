package pl.edu.agh.kis.pz1.readers_writers.clients;

import pl.edu.agh.kis.pz1.readers_writers.commons.TerminalPrinter;
import pl.edu.agh.kis.pz1.readers_writers.manager.ReadingRoomManager;

/**
 * Abstract class that extends Thread and simplifies ReadingRoom management
 */
public class ReadingRoomClient extends Thread{
    protected static final TerminalPrinter terminalPrinter = new TerminalPrinter();
    protected final String clientName;
    protected final ReadingRoomManager readingRoomManager;
    protected boolean canEnter = false;

    protected ReadingRoomClient(String clientName, ReadingRoomManager readingRoomManager){
        this.clientName = clientName;
        this.readingRoomManager = readingRoomManager;
    }

    @Override
    public String toString() {
        return clientName;
    }

    /**
     * Overridden run method to make spotting errors easier
     */
    @Override
    public void run() {
        terminalPrinter.print("Something went wrong. This method should not be called.");
        throw new ClassCastException("This method should not be called.");
    }

    public synchronized boolean isCanEnter() {
        return canEnter;
    }

    public synchronized void setCanEnter(boolean canEnter) {
        this.canEnter = canEnter;
    }

    void askAndWait() throws InterruptedException {
        terminalPrinter.print(clientName + " wants to enter");
        readingRoomManager.addClient(this);
        while(!canEnter){
            synchronized (readingRoomManager){
                readingRoomManager.wait();
            }
        }
        if(readingRoomManager.isStop()){
            throw new InterruptedException("Manager stopped.");
        }
    }

    void dealWithInterrupt(){
        readingRoomManager.getReadingRoomQueue().leaveInside(this);
        readingRoomManager.getReadingRoomQueue().leaveQueue(this);
        setCanEnter(false);
    }

    void leaveReadingRoom(){
        readingRoomManager.getReadingRoomQueue().leaveInside(this);
        setCanEnter(false);
        terminalPrinter.print(clientName + " leaves");

        synchronized (readingRoomManager){
            readingRoomManager.notifyAll();
        }
    }
}

