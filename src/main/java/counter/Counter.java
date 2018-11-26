package counter;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Counter {
    private AtomicCounter atomicCounter;
    private ReentrantCounter reentrantCounter;
    private SynchronizedCounter synchronizedCounter;
    private VolitileCounter volitileCounter;

    @Setup
    public void setUp() {
        atomicCounter = new AtomicCounter();
        reentrantCounter = new ReentrantCounter();
        synchronizedCounter = new SynchronizedCounter();
        volitileCounter = new VolitileCounter();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public void atomicCounter() {
        atomicCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public void reentrantCounter() {
        reentrantCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public void synchronizedCounter() {
        synchronizedCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public void volitaleCounter() {
        volitileCounter.increase();
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(2)
    public void atomicCounter2() {
        atomicCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(2)
    public void reentrantCounter2() {
        reentrantCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(2)
    public void synchronizedCounter2() {
        synchronizedCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(2)
    public void volitaleCounter2() {
        volitileCounter.increase();
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(4)
    public void atomicCounter4() {
        atomicCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(4)
    public void reentrantCounter4() {
        reentrantCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(4)
    public void synchronizedCounter4() {
        synchronizedCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(4)
    public void volitaleCounter4() {
        volitileCounter.increase();
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(8)
    public void atomicCounter8() {
        atomicCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(8)
    public void reentrantCounter8() {
        reentrantCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(8)
    public void synchronizedCounter8() {
        synchronizedCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(8)
    public void volitaleCounter8() {
        volitileCounter.increase();
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(16)
    public void atomicCounter16() {
        atomicCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(16)
    public void reentrantCounter16() {
        reentrantCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(16)
    public void synchronizedCounter16() {
        synchronizedCounter.increase();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(16)
    public void volitaleCounter16() {
        volitileCounter.increase();
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Counter.class.getSimpleName())
                .forks(2)
                .warmupIterations(5)
                .measurementIterations(10)
                .resultFormat(ResultFormatType.CSV)
                .result("SmaginResult.csv")
                .mode(Mode.Throughput)
                .jvmArgs("-ea")
                .build();

        new Runner(opt).run();
        System.out.println("Hello World!");
    }
}
