import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class lab4_ai {
    public static void main(String[] args) throws Exception {
        int threads = 4;
        int batches = 8;
        int batchSize = 5;

        SharedResource shared = new SharedResource("SharedMemoryBuffer");

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            List<Callable<List<Integer>>> tasks = new ArrayList<>();
            for (int i = 0; i < batches; i++) {
                int start = i * batchSize;
                tasks.add(() -> {
                    List<Integer> input = SharedResource.generateInput(start, batchSize);
                    return shared.processBatch(input); // object calling important method
                });
            }

            List<Future<List<Integer>>> futures = pool.invokeAll(tasks);
            for (Future<List<Integer>> f : futures) {
                f.get();
            }

            System.out.println("Resource name: " + shared.getName());
            System.out.println("Batches processed: " + shared.getBatchesProcessed());
            System.out.println("Total processed items: " + shared.getProcessedCount());
        } finally {
            pool.shutdownNow();
        }
    }
}
