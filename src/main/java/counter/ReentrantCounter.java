package counter;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCounter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    public void increase(){
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}
