package counter;

public class SynchronizedCounter {
    private int count = 0;

    public synchronized int increase(){
        return count++;
    }
}
