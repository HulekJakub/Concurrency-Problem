package pl.edu.agh.kis.pz1.readers_writers.reading_room;

/**
 * Resource class for concurrency problem
 */
public class ReadingRoom {
    private String book = "StartingString";
    private int numberOfReadAccesses = 0;

    /**
     * Returns class' book and increments readAccesses counter
     * @return String book
     */
    public synchronized String getBook() {
        numberOfReadAccesses++;
        return book;
    }

    public int getNumberOfReadAccesses() {
        return numberOfReadAccesses;
    }

    public synchronized void setBook(String book) {
        this.book = book;
    }
}

