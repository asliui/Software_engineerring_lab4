import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class SharedResource {
    private final String name;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final List<Integer> processed = new ArrayList<>();
    private final AtomicInteger batchesProcessed = new AtomicInteger(0);

    public SharedResource(String name) {
        this.name = name;
    }

    public List<Integer> processBatch(Collection<Integer> input) {
        lock.lock();
        try {
            List<Integer> output = input.stream()
                    .map(x -> x * x)
                    .collect(Collectors.toList());
            processed.addAll(output);
            batchesProcessed.incrementAndGet();
            return output;
        } finally {
            lock.unlock();
        }
    }

    public int getBatchesProcessed() {
        return batchesProcessed.get();
    }

    public int getProcessedCount() {
        lock.lock();
        try {
            return processed.size();
        } finally {
            lock.unlock();
        }
    }

    public String getName() {
        return name;
    }

    public static List<Integer> generateInput(int startInclusive, int count) {
        return IntStream.range(startInclusive, startInclusive + count).boxed().toList();
    }
}
