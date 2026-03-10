import java.util.ArrayList;
import java.util.List;

public class SharedResource {
    public String name;
    public List<Integer> processed = new ArrayList<>();
    public int batchesProcessed = 0;

    public SharedResource(String name) {
        this.name = name;
    }

    public synchronized void processBatch(List<Integer> input){
        System.out.println("Processing batch in: "+ name);
        
        for (Integer num : input){
            int square = num*num;
            processed.add(square);
        }
        batchesProcessed++;
    }

    public int getBatchesProcessed(){
        return batchesProcessed;
    }
    
    public List<Integer> getList(){
        return processed;
    }

    public static void main(String[] args){
        SharedResource myRes = new SharedResource("Data-Buffer");    
        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(4);
        myRes.processBatch(data);
        System.out.println("Total processed: " + myRes.processed.size());
        List<Integer> list = myRes.getList();
        System.out.println(list.toString());
    }
}
