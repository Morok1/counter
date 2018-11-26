package counter;

public class VolitileCounter {
    private volatile int  count = 0;
    public void increase(){
        count++;
    }
}
